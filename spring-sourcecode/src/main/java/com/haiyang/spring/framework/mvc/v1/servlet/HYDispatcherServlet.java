package com.haiyang.spring.framework.mvc.v1.servlet;

import com.haiyang.spring.framework.annection.HYAutowired;
import com.haiyang.spring.framework.annection.HYController;
import com.haiyang.spring.framework.annection.HYRequestMapping;
import com.haiyang.spring.framework.annection.HYService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName: HYDispatcherServlet
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/13 11:15
 * @Version 1.0
 */
public class HYDispatcherServlet extends HttpServlet {

    private Map<String, Object> map = new HashMap<String, Object>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatcher(req, resp);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
            Properties properties = new Properties();
            properties.load(is);
            // 获取扫包配置
            String scanPackage = properties.getProperty("scanPackage");
            doScanPackage(scanPackage);

            //循环扫描之后的类
            Map<String,Object> scanMap = new HashMap<String, Object>();
            for (String className : map.keySet()) {
                if (!className.contains(".")) {
                    continue;
                }
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(HYController.class)) {
                    //如果是controller 就实例化 放入容器
                    scanMap.put(className, clazz.newInstance());
                    String baseUrl = "";
                    if (clazz.isAnnotationPresent(HYRequestMapping.class)) {
                        HYRequestMapping requestMapping = clazz.getAnnotation(HYRequestMapping.class);
                        baseUrl = requestMapping.value();
                    }
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (!method.isAnnotationPresent(HYRequestMapping.class)) {
                            continue;
                        }
                        HYRequestMapping requestMapping = method.getAnnotation(HYRequestMapping.class);
                        String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                        scanMap.put(url, method);
                        System.out.println("Mapped " + url + "," + method);
                    }

                } else if (clazz.isAnnotationPresent(HYService.class)) {
                    HYService service = clazz.getAnnotation(HYService.class);
                    String beanName = service.value();
                    if ("".equals(beanName)) {
                        beanName = clazz.getName();
                    }
                    Object instance = clazz.newInstance();
                    scanMap.put(beanName, instance);
                    for (Class<?> cla : clazz.getInterfaces()) {
                        scanMap.put(cla.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
            map.putAll(scanMap);
            //循环容器 map
            for (Object object : map.values()) {
                if (null == object) {
                    continue;
                }
                Class<?> clazz = object.getClass();
                if (clazz.isAnnotationPresent(HYController.class)) {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (!field.isAnnotationPresent(HYAutowired.class)) {
                            continue;
                        }
                        HYAutowired autowired = field.getAnnotation(HYAutowired.class);
                        String beanName = autowired.value();
                        if ("".equals(beanName)) {
                            beanName = field.getType().getName();
                        }
                        field.setAccessible(true);
                        field.set(map.get(clazz.getName()), map.get(beanName));

                    }

                }
            }

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
            System.out.print("mvc is init");
        }
    }


    private void doScanPackage(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classDir = new File(url.getFile());
        for (File file : classDir.listFiles()) {
            if (file.isDirectory()) {
                doScanPackage(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                String className = scanPackage + "." + file.getName().replace(".class", "");
                map.put(className, null);
            }

        }

    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String requestURI = req.getRequestURI();
            String contextPath = req.getContextPath();
            String url = requestURI.replace(contextPath, "").replaceAll("/+", "/");
            if (!map.containsKey(url)) {
                resp.getWriter().write("404 Not Found!!");
                return;
            }

            Method method = (Method) map.get(url);
            // 获取氢气参数  为什么泛型中是 string[]
            Map<String, String[]> parameterMap = req.getParameterMap();
            method.invoke(map.get(method.getDeclaringClass().getName()), new Object[]{req, resp, parameterMap.get("name")[0]});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
