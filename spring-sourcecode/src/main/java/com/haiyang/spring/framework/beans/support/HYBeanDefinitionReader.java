package com.haiyang.spring.framework.beans.support;

import com.haiyang.spring.framework.beans.config.HYBeanDefinition;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: HYBeanDefinitionReader
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-26 14:11
 * @Version: 1.0
 */
public class HYBeanDefinitionReader {
    //存放所有className
    private List<String> registyBeanClasses = new ArrayList<>();
    private Properties properties = new Properties();
    //固定配置文件中的key，相对于xml的规范
    private final String SCAN_PACKAGE = "scanPackage";

    public HYBeanDefinitionReader(String... locations) {
        //通过URL 定位找到对应文件，并转换成输入流
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:", ""));
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        doScanner(properties.getProperty(SCAN_PACKAGE));
    }

    private void doScanner(String scannerPackage) {
        // 转换文件路径  将.替换成/
        URL url = this.getClass().getClassLoader().getResource("/" + scannerPackage.replace(".", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScanner(scannerPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = scannerPackage + "." + file.getName().replace(".class", "");
                registyBeanClasses.add(className);
            }
        }
    }

    /**
     * 把配置文件中扫描到的所有的配置信息转换为BeanDefinition对象，以便于之后IOC操作方便
     *
     * @return
     */
    public List<HYBeanDefinition> loadBeanDefinition() {
        List<HYBeanDefinition> result = new ArrayList<>();
        try {
            if (CollectionUtils.isNotEmpty(registyBeanClasses)) {
                for (String beanName : registyBeanClasses) {
                    Class<?> clazz = Class.forName(beanName);

                    //如果类是接口的话 使用他的实现类
                    if (clazz.isInterface()) {
                        continue;
                    }
                    String lowerFirstCaseName = toLowerFirstCase(clazz.getSimpleName());
                    HYBeanDefinition beanDefinition = doCreateBeanDefinition(lowerFirstCaseName, clazz.getName());
                    result.add(beanDefinition);

                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class cla : interfaces) {
                        //如果是多个实现类，只能覆盖  //这个时候，可以自定义名字
                        result.add(doCreateBeanDefinition(cla.getName(), clazz.getName()));
                    }
                }
            } else {
                throw new RuntimeException("registyBeanClasses is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private HYBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        HYBeanDefinition beanDefinition = new HYBeanDefinition();
        beanDefinition.setFactoryBeanName(factoryBeanName);
        beanDefinition.setBeanClassName(beanClassName);
        return beanDefinition;
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public Properties getProperties() {
        return this.properties;
    }
}
