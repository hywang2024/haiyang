package com.haiyang.spring.framework.mvc.v2.servlet;

import com.haiyang.spring.framework.annection.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @ClassName: HYDispatcherServlet
 * @Description: 第二个版本
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/13 19:40
 * @Version 1.0
 */
public class HYDispatcherServlet extends HttpServlet {

    //保存配置信息application.properties
    private Properties contextConfig = new Properties();
    //保存扫描的所有类型
    private List<String> classNames = new ArrayList<String>();
    // ioc
    private Map<String, Object> ioc = new HashMap<String, Object>();
    //保存所有的url和method的对应关系
    private Map<String, Method> handleMapping = new HashMap<String, Method>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatcher(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("500 Exection,Detail : " + Arrays.toString(e.getStackTrace()));
        }
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //2.扫描相关的类 并将类放入到 list中
        String scanPackage = contextConfig.getProperty("scanPackage");
        doScanPackage(scanPackage);
        //3.初始化扫描的类，并放入到ioc的容器中
        doInstance();
        //4.完成依赖注入
        doAutowired();
        //5.初始化handleMapping
        doHandleMapping();
    }

    private void doHandleMapping() {
        //初始化url和Method的一对一对应关系
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(HYController.class)) {
                continue;
            }

            //保存类上面的 requestmapping
            String baseUrl = "";
            if (clazz.isAnnotationPresent(HYRequestMapping.class)) {
                baseUrl = clazz.getAnnotation(HYRequestMapping.class).value().trim();
            }
            //获取所有public方法
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(HYRequestMapping.class)) {
                    continue;
                }
                String requstMapping = method.getAnnotation(HYRequestMapping.class).value().trim();
                // //demo///query
                String url = ("/" + baseUrl + "/" + requstMapping)
                        .replaceAll("/+", "/");
                handleMapping.put(url, method);
                System.out.println("Mapped :" + url + "," + method);
            }
        }


    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        //循环容器中的所有类
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            //那到类的所有属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(HYAutowired.class)) {
                    continue;
                }
                String beanName = field.getAnnotation(HYAutowired.class).value().trim();
                if ("".equals(beanName)) {
                    //获得接口的类型，作为key待会拿这个key到ioc容器中去取值
                    beanName = field.getType().getName();
                }
                //暴力访问
                field.setAccessible(true);
                try {
                    //利用反射 给字段赋值
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

    }

    private void doInstance() {
        if (classNames.isEmpty()) {
            return;
        }
        try {
            //循环所有类
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                // 如果类注解有 controller
                if (clazz.isAnnotationPresent(HYController.class)) {
                    Object instance = clazz.newInstance();
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    //clazz.getName()
                    // 将类的实例化放入的 ioc 容器中
                    ioc.put(beanName, instance);
                } else if (clazz.isAnnotationPresent(HYService.class)) {
                    // 如果类注解是service
                    //自定义beanName
                    String beanName = clazz.getAnnotation(HYService.class).value();
                    if ("".equals(beanName)) {
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    //获取所有接口
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class cla : interfaces) {
                        if (ioc.containsKey(cla.getName())) {
                            throw new Exception("the key :" + cla.getName() + " is exists");
                        }
                        ioc.put(cla.getName(), cla.newInstance());
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {

        }
    }

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doScanPackage(String scanPackage) {
        // 转换文件路径（就是把.换成/）
        URL url = this.getClass().getClassLoader().getResource(scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanPackage(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = (scanPackage + "." + file.getName()).replace(".class", "");
                classNames.add(className);
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = null;
        try {
            // 直接从类的路径下找到spring的主配置文件所在的路径
            // 并将其读到 定义好的Properties对象中
            is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
            contextConfig.load(is);
        } catch (Exception e) {
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
    }


    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String requestURI = req.getRequestURI();
            String contextPath = req.getContextPath();
            String url = requestURI.replaceAll(contextPath, "").replaceAll("/+", "/");
            if (!handleMapping.containsKey(url)) {
                resp.getWriter().write("404 Not Found!!!");
                return;
            }

            Method method = handleMapping.get(url);
            //从reqest中拿到url传过来的参数
            Map<String, String[]> params = req.getParameterMap();
            //获取方法的形参列表
            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] paramValues = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                //不能用instanceof，parameterType它不是实参，而是形参
                Class<?> parameterType = parameterTypes[i];
                if (parameterType == HttpServletRequest.class) {
                    paramValues[i] = req;
                    continue;
                } else if (parameterType == HttpServletResponse.class) {
                    paramValues[i] = resp;
                    continue;
                } else if (parameterType == String.class) {
                 /*   if (parameterType.isAnnotationPresent(HYRequestParam.class)) {
                        HYRequestParam requestParam = parameterType.getAnnotation(HYRequestParam.class);
                        //todo  获取的requestParam 为什么是null
                        if (params.containsKey(requestParam.value())) {
                            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                                String value = Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", "")
                                        .replaceAll("\\s", ",");
                                paramValues[i] = value;
                            }

                        }
                    }*/

                    Annotation[][] pa = method.getParameterAnnotations();
                    for (int j = 0; j < pa.length ; j ++) {
                        for(Annotation a : pa[j]){
                            if(a instanceof HYRequestParam){
                                String paramName = ((HYRequestParam) a).value();
                                if(params.containsKey(paramName)) {
                                    for (Map.Entry<String,String[]> param : params.entrySet()){
                                        String value = Arrays.toString(param.getValue())
                                                .replaceAll("\\[|\\]","");
                                        paramValues[i] = value;
                                    }
                                }
                            }
                        }
                    }
                }

            }
            //通过反射拿到method所在class，拿到class之后还是拿到class的名称
            //再调用toLowerFirstCase获得beanName
            String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
            method.invoke(ioc.get(beanName), paramValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
