package com.haiyang.proxy.jdkproxy;

import java.lang.reflect.Method;

/**
 * @ClassName: JDKTest
 * @Description: 测试
 * @Author Administrator
 * @CreateDate 2019/4/6 10:55
 * @Version 1.0
 */
public class JDKTest {

    public static void main(String[] args) {
        try {
        Object obj = new JDKMeiPo().getInstance(new Girl());
        Method method = obj.getClass().getMethod("findLove",null);
        method.invoke(obj);

        //obj.findLove();

        //$Proxy0
         /* byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
          FileOutputStream os = new FileOutputStream("E://$Proxy0.class");
          os.write(bytes);
          os.close();*/
        }catch (Throwable e){

        }
    }
}
