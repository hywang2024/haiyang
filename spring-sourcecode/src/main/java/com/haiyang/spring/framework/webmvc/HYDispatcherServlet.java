package com.haiyang.spring.framework.webmvc;

import com.haiyang.spring.framework.annection.HYController;
import com.haiyang.spring.framework.annection.HYRequestMapping;
import com.haiyang.spring.framework.context.HYApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: HYDispatcherServlet
 * @Description: MVC 启动入口
 * @Author: hywang
 * @CreateDate: 2019-04-29 11:59
 * @Version: 1.0
 */

public class HYDispatcherServlet extends HttpServlet {

    private final String LOACTION = "contextConfigLocation";
    private HYApplicationContext context;
    private List<HYHandlerMapping> handleMappings = new ArrayList<>();
    private Map<HYHandlerMapping, HYHandlerAdapter> handleAdapterMap = new HashMap<>();

    private List<HYViewResolver> viewResolvers = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n" + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "").replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
//            new GPModelAndView("500");

        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        HYHandlerMapping handler = getHandler(req);

        if (handler == null) {
            processDispatchResult(req, resp, new HYModelAndView("404"));
            return;
        }

        //2、准备调用前的参数
        HYHandlerAdapter ha = getHandlerAdapter(handler);

        //3、真正的调用方法,返回ModelAndView存储了要穿页面上值，和页面模板的名称
        HYModelAndView mv = ha.handle(req, resp, handler);

        //这一步才是真正的输出
        processDispatchResult(req, resp, mv);

    }

    private HYHandlerAdapter getHandlerAdapter(HYHandlerMapping handler) {

        if (this.handleAdapterMap.isEmpty()) {
            return null;
        }
        HYHandlerAdapter ha = this.handleAdapterMap.get(handler);
        if (ha.supports(handler)) {
            return ha;
        }
        return null;
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, HYModelAndView modelAndView) throws Exception {
        //把给我的ModleAndView变成一个HTML、OuputStream、json、freemark、veolcity
        //ContextType
        if (null == modelAndView) {
            return;
        }

        //如果ModelAndView不为null，怎么办？
        if (this.viewResolvers.isEmpty()) {
            return;
        }

        for (HYViewResolver viewResolver : this.viewResolvers) {
            HYView view = viewResolver.resolveViewName(modelAndView.getViewName(), null);
            view.render(modelAndView.getModel(), req, resp);
            return;
        }


    }

    private HYHandlerMapping getHandler(HttpServletRequest req) {
        if (this.handleMappings.isEmpty()) {
            return null;
        }

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");

        for (HYHandlerMapping handler : this.handleMappings) {
            try {
                Matcher matcher = handler.getPattern().matcher(url);
                //如果没有匹配上继续下一个匹配
                if (!matcher.matches()) {
                    continue;
                }

                return handler;
            } catch (Exception e) {
                throw e;
            }
        }
        return null;

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //1 初始化ApplicationContext
        String initParameter = config.getInitParameter(LOACTION);
        context = new HYApplicationContext(initParameter);

        //2 初始化spirngMVC 九大组件
        initStrategies(context);

    }

    private void initStrategies(HYApplicationContext context) {
        //多文件上传组件
        initMultipartResolver(context);
        // 初始化本地语言组件
        initLocaleResolver(context);
        //初始化模版处理器
        initThemeResolver(context);

        //初始化映射关系  重要
        initHandlerMappings(context);
        //初始化参数适配器 重要
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);

        //初始化视图转换器  重要
        initViewResolvers(context);
        // 初始化 参数缓存器
        initFlashMapManager(context);


    }

    private void initFlashMapManager(HYApplicationContext context) {


    }

    private void initViewResolvers(HYApplicationContext context) {
        //拿到模板的存放目录
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);
        String[] templates = templateRootDir.list();
        for (int i = 0; i < templates.length; i++) {
            //这里主要是为了兼容多模板，所有模仿Spring用List保存
            //在我写的代码中简化了，其实只有需要一个模板就可以搞定
            //只是为了仿真，所有还是搞了个List
            this.viewResolvers.add(new HYViewResolver(templateRoot));
        }


    }

    private void initRequestToViewNameTranslator(HYApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(HYApplicationContext context) {

    }

    private void initHandlerAdapters(HYApplicationContext context) {
        //把一个requet请求变成一个handler，参数都是字符串的，自动配到handler中的形参
        if (CollectionUtils.isEmpty(handleMappings)) {
            throw new RuntimeException("handleMappings is empty");
        }
        for (HYHandlerMapping handleMapping : handleMappings) {
            this.handleAdapterMap.put(handleMapping, new HYHandlerAdapter());
        }

    }

    private void initHandlerMappings(HYApplicationContext context) {
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        try {
            for (String beanName : beanDefinitionNames) {
                Object obj = context.getBean(beanName);
                Class<?> clazz = obj.getClass();
                if (!clazz.isAnnotationPresent(HYController.class)) {
                    continue;
                }
                String baseUrl = "";
                //获取controller 的url配置
                if (clazz.isAnnotationPresent(HYRequestMapping.class)) {
                    baseUrl = clazz.getAnnotation(HYRequestMapping.class).value();
                }

                //获取 方法的 url
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (!method.isAnnotationPresent(HYRequestMapping.class)) {
                        continue;
                    }
                    //映射url
                    HYRequestMapping requestMapping = method.getAnnotation(HYRequestMapping.class);

                    String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*", ".*")).replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);

                    this.handleMappings.add(new HYHandlerMapping(obj, method, pattern));
                    System.out.println("Mapped " + regex + "," + method);

                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initThemeResolver(HYApplicationContext context) {
    }

    private void initLocaleResolver(HYApplicationContext context) {
    }

    private void initMultipartResolver(HYApplicationContext context) {
    }
}
