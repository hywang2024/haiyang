package com.haiyang.spring.framework.context;

import com.haiyang.spring.framework.annection.HYAutowired;
import com.haiyang.spring.framework.annection.HYController;
import com.haiyang.spring.framework.annection.HYService;
import com.haiyang.spring.framework.aop.HYAopProxy;
import com.haiyang.spring.framework.aop.HYJdkDynamicAopProxy;
import com.haiyang.spring.framework.aop.config.HYAopConfig;
import com.haiyang.spring.framework.aop.support.HYAdvisedSupport;
import com.haiyang.spring.framework.beans.HYBeanWrapper;
import com.haiyang.spring.framework.beans.config.HYBeanDefinition;
import com.haiyang.spring.framework.beans.config.HYBeanPostProcessor;
import com.haiyang.spring.framework.beans.support.HYBeanDefinitionReader;
import com.haiyang.spring.framework.beans.support.HYDefaultListableBeanFactory;
import com.haiyang.spring.framework.core.HYBeanFactory;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: HYApplicationContext
 * @Description: ApplicationContext
 * @Author: hywang
 * @CreateDate: 2019-04-26 14:07
 * @Version: 1.0
 */
public class HYApplicationContext extends HYDefaultListableBeanFactory implements HYBeanFactory {

    private String[] configLocations;
    private HYBeanDefinitionReader beanDefinitionReader;


    // 单例的IOC容器缓存
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    // 通用的ioc容器
    private Map<String, HYBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    public HYApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() throws Exception {
        //1.定位配置文件
        beanDefinitionReader = new HYBeanDefinitionReader(configLocations);
        //2.加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<HYBeanDefinition> beanDefinitions = beanDefinitionReader.loadBeanDefinition();
        //3.注册 把配置信息放到容器中
        doRegisterBeanDefinition(beanDefinitions);
        //4.加载不是延迟加载的类，提前初始化
    }

    private void doRegisterBeanDefinition(List<HYBeanDefinition> beanDefinitions) {
        if (CollectionUtils.isEmpty(beanDefinitions)) {
            throw new RuntimeException("beanDefinitions is empty");
        }
        beanDefinitions.forEach(beanDefinition -> {
            if (beanDefinitionMap.containsKey(beanDefinition.getBeanClassName())) {
                throw new RuntimeException("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            beanDefinitionMap.put(beanDefinition.getBeanClassName(), beanDefinition);
        });
    }

    /**
     * 依赖注入，从这里开始，通过读取BeanDefinition中的信息
     * 然后，通过反射机制创建一个实例并返回
     * Spring做法是，不会把最原始的对象放出去，会用一个BeanWrapper来进行一次包装
     * 装饰器模式：
     * 1、保留原来的OOP关系
     * 2、我需要对它进行扩展，增强（为了以后AOP打基础）
     *
     * @param beanName
     * @return
     * @throws Exception
     */
    @Override
    public Object getBean(String beanName) throws Exception {
        HYBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        HYBeanPostProcessor beanPostProcessor = new HYBeanPostProcessor();
        beanPostProcessor.postProcessBeforeInitialization(null, beanName);
        //工厂模式 + 策略模式
        Object instance = instantiateBean(beanName, beanDefinition);
        // 把这个对象封装到beanWrapper中
        HYBeanWrapper beanWrapper = new HYBeanWrapper(instance);
        factoryBeanInstanceCache.put(beanName, beanWrapper);
        beanPostProcessor.postProcessAfterInitialization(instance, beanName);
        // 注入
        populateBean(beanName, new HYBeanDefinition(), beanWrapper);
        return factoryBeanInstanceCache.get(beanName).getWrapperInstance();
    }

    private void populateBean(String beanName, HYBeanDefinition hyBeanDefinition, HYBeanWrapper beanWrapper) {
        Object instance = beanWrapper.getWrapperInstance();


        Class<?> clazz = beanWrapper.getWrapperClass();
        //判断 只有加了注解 才能执行依赖注入
        if (!(clazz.isAnnotationPresent(HYController.class) || clazz.isAnnotationPresent(HYService.class))) {
            return;
        }
        // 获取所有的 fields
        Field[] fields = clazz.getDeclaredFields();
        for (Field filed : fields) {
            if (!filed.isAnnotationPresent(HYAutowired.class)) {
                continue;
            }
            HYAutowired annotation = filed.getAnnotation(HYAutowired.class);
            String autoeiredName = annotation.value().trim();
            if ("".equals(autoeiredName)) {
                autoeiredName = filed.getType().getName();
            }
            filed.setAccessible(true);
            try {
                if (factoryBeanInstanceCache.get(autoeiredName) == null) {
                    continue;
                }
                filed.set(instance, factoryBeanInstanceCache.get(autoeiredName).getWrapperInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private Object instantiateBean(String beanName, HYBeanDefinition beanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = beanDefinition.getBeanClassName();
        //2、反射 实例化对象
        Object instance = null;
        try {
            //假设默认就是单例,细节暂且不考虑，先把主线拉通
            if (singletonObjects.containsKey(className)) {
                instance = singletonObjects.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();

                //aop
                HYAopConfig config = instantiateAopConfig(beanDefinition);
                HYAdvisedSupport support = new HYAdvisedSupport(config);
                support.setTargetClass(clazz);
                support.setTarget(instance);
                //符合PointCut的规则的话，创建代理对象
                if (support.pointCutMatch()) {
                    instance = createProxy(support).getProxy();
                }

                singletonObjects.put(className, instance);
                singletonObjects.put(beanDefinition.getFactoryBeanName(), instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private HYAopProxy createProxy(HYAdvisedSupport support) {
        Class<?> targetClass = support.getTargetClass();
        if (targetClass.getInterfaces().length > 0) {
            new HYJdkDynamicAopProxy(support);
        }
        return new HYJdkDynamicAopProxy(support);
    }


    @Override
    public Object getBean(Class<?> beanClass) throws Exception {
        return getBean(beanClass.getName());
    }


    private HYAopConfig instantiateAopConfig(HYBeanDefinition beanDefinition) {
        HYAopConfig config = new HYAopConfig();
        config.setPointCut(this.beanDefinitionReader.getProperties().getProperty("pointCut"));
        config.setAspectClass(this.beanDefinitionReader.getProperties().getProperty("aspectClass"));
        config.setAspectBefore(this.beanDefinitionReader.getProperties().getProperty("aspectBefore"));
        config.setAspectAfter(this.beanDefinitionReader.getProperties().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.beanDefinitionReader.getProperties().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.beanDefinitionReader.getProperties().getProperty("aspectAfterThrowingName"));
        return config;
    }



    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new  String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig(){
        return this.beanDefinitionReader.getProperties();
    }
}
