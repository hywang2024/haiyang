package com.haiyang.spring.framework.mvc.v3.servlet;

import com.haiyang.spring.framework.annection.HYAutowired;
import com.haiyang.spring.framework.annection.HYController;
import com.haiyang.spring.framework.annection.HYRequestMapping;
import com.haiyang.spring.framework.annection.HYService;
import com.haiyang.spring.framework.mvc.v3.handle.Handler;

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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: HYDispatcherServlet
 * @Description: v3 版本  设计模式
 * @Author haiyang
 * @Email hywang2017@qq.com
 * @CreateDate 2019/4/14 19:25
 * @Version 1.0
 */
public class HYDispatcherServlet extends HttpServlet {

    //初始化一些 用到的属性
    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();
    // 保存扫描的所有类名
    private List<String> classNames = new ArrayList<String>();

    //创建容器
    private Map<String, Object> ioc = new HashMap<>();

    //Handler 本身的功能就是把url和method对应关系，已经具备了Map的功能
    private List<Handler> handlerMapping = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatcher(req, resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Handler handler = BuildHandle(req);
            if (null == handler) {
                resp.getWriter().write("404 Not Found!!!");
                return;
            }

            //获取方法的形参列表
            Class<?>[] parameterTypes = handler.getParamTypes();
            Object[] paramValues = new Object[parameterTypes.length];

            //从reqest中拿到url传过来的参数
            Map<String, String[]> params = req.getParameterMap();
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String value = Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", "")
                        .replaceAll("\\s", ",");
                if (!handler.getParamIndexMapping().containsKey(entry.getKey())) {
                    continue;
                }
                Integer index = handler.getParamIndexMapping().get(entry.getKey());
                paramValues[index] = convert(parameterTypes[index], value);


                if (handler.getParamIndexMapping().containsKey(HttpServletRequest.class.getName())) {
                    int reqIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getName());
                    paramValues[reqIndex] = req;
                }

                if (handler.getParamIndexMapping().containsKey(HttpServletResponse.class.getName())) {
                    int respIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getName());
                    paramValues[respIndex] = resp;
                }

                Object returnValue = handler.getMethod().invoke(handler.getController(), paramValues);
                if (returnValue == null || returnValue instanceof Void) {
                    return;
                }
                resp.getWriter().write(returnValue.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //url传过来的参数都是String类型的，HTTP是基于字符串协议
    //只需要把String转换为任意类型就好
    private Object convert(Class<?> type, String value) {
        //如果是int
        if (Integer.class == type) {
            return Integer.valueOf(value);
        } else if (Double.class == type) {
            return Double.valueOf(value);
        }
        //如果还有double或者其他类型，继续加if
        //这时候，我们应该想到策略模式了
        //在这里暂时不实现，希望小伙伴自己来实现
        return value;
    }

    private Handler BuildHandle(HttpServletRequest req) {
        if (handlerMapping.isEmpty()) {
            return null;
        }
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String url = requestURI.replaceAll(contextPath, "").replaceAll("/+", "/");
        for (Handler handler : handlerMapping) {
            Matcher matcher = handler.getPattern().matcher(url);
            if (!matcher.matches()) {
                continue;
            }
            return handler;
        }
        return null;
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
        System.out.println("mvc init success");
    }

    private void doHandleMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            if (!clazz.isAnnotationPresent(HYController.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.isAnnotationPresent(HYRequestMapping.class)) {
                baseUrl = clazz.getAnnotation(HYRequestMapping.class).value();
            }
            //默认获取所有public方法
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(HYRequestMapping.class)) {
                    continue;
                }
                HYRequestMapping requestMapping = method.getAnnotation(HYRequestMapping.class);
                // //demo///query
                String regex = ("/" + baseUrl + "/" + requestMapping.value())
                        .replaceAll("/+","/");
                Pattern pattern = Pattern.compile(regex);
                handlerMapping.add(new Handler(pattern, method, entry.getValue()));
                System.out.println("Mapped :" + pattern + "," + method);
            }


        }

    }

    private void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        // 循环容器中的类，利用反射赋值
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(HYAutowired.class)) {
                    continue;
                }
                String beanName = field.getAnnotation(HYAutowired.class).value();
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                field.setAccessible(true);

                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void doInstance() {
        // 将扫描的类，并放入到ioc的容器中
        if (classNames.isEmpty()) {
            return;
        }
        try {
            // 循环扫描到的 所有类
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(HYController.class)) {
                    String name = toLowerFirstCase(clazz.getSimpleName());
                    Object instance = clazz.newInstance();
                    ioc.put(name, instance);
                } else if (clazz.isAnnotationPresent(HYService.class)) {
                    HYService service = clazz.getAnnotation(HYService.class);
                    String beanName = service.value();
                    if ("".equals(beanName)) {
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);

                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class cla : interfaces) {
                        if (ioc.containsKey(cla.getName())) {
                            throw new Exception("The “" + cla.getName() + "” is exists");
                        }
                        ioc.put(cla.getName(), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doScanPackage(String scanPackage) {
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

    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
