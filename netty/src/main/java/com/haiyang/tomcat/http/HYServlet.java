package com.haiyang.tomcat.http;

/**
 * @ClassName: HYServlet
 * @Description: servlet
 * @Author: hywang
 * @CreateDate: 2019-07-02 17:52
 * @Version: 1.0
 */
public abstract class HYServlet {
    public void service(HYRequest request, HYResponse response) throws Exception {

        //由service方法来决定，是调用doGet或者调用doPost
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }

    }

    public abstract void doGet(HYRequest request, HYResponse response) throws Exception;

    public abstract void doPost(HYRequest request, HYResponse response) throws Exception;
}
