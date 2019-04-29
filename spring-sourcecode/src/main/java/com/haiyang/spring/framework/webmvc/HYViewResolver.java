package com.haiyang.spring.framework.webmvc;

import java.io.File;
import java.util.Locale;

/**
 * @ClassName: HYViewResolver
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-29 12:04
 * @Version: 1.0
 */
public class HYViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    private File templateRootDir;

    public HYViewResolver(String templateRootDir) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRootDir).getFile();
        this.templateRootDir = new File(templateRootPath);
    }
    public HYView resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new HYView(templateFile);
    }
}
