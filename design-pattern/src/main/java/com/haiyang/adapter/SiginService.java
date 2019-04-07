package com.haiyang.adapter;

/**
 * @ClassName: SiginService
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:32
 * @Version 1.0
 */
public class SiginService {
    /**
     * 注册方法
     * @param username
     * @param password
     * @return
     */
    public ResultData regist(String username,String password){
        return  new ResultData(200,"注册成功",new Member());
    }


    /**
     * 登录的方法
     * @param username
     * @param password
     * @return
     */
    public ResultData login(String username,String password){
        return null;
    }
}
