package com.haiyang.adapter;

/**
 * @ClassName: IPassportForThird
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:31
 * @Version 1.0
 */
public interface IPassportForThird {
    /**
     * QQ登录
     * @param id
     * @return
     */
    ResultData loginForQQ(String id);
    /**
     * 手机号登录
     * @param telphone
     * @param code
     * @return
     */
    ResultData loginForTel(String telphone, String code);

}
