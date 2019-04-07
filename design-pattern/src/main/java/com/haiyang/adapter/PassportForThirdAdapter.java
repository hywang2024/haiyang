package com.haiyang.adapter;

/**
 * @ClassName: PassportForThirdAdapter
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:32
 * @Version 1.0
 */
public class PassportForThirdAdapter extends SiginService implements IPassportForThird{

    @Override
    public ResultData loginForQQ(String id) {
//        return processLogin(id,RegistForQQAdapter.class);
        return processLogin(id,LoginForQQAdapter.class);
    }

    @Override
    public ResultData loginForTel(String telphone, String code) {
        return processLogin(telphone,LoginForTelAdapter.class);
    }


    public ResultData loginForTelphone(String telphone, String code) {
        return processLogin(telphone,LoginForTelAdapter.class);
    }

    public ResultData loginForRegist(String username, String passport) {
        super.regist(username,passport);
        return super.login(username,passport);
    }

    private ResultData processLogin(String key,Class<? extends LoginAdapter> clazz){
        try{
            //适配器不一定要实现接口
            LoginAdapter adapter = clazz.newInstance();

            //判断传过来的适配器是否能处理指定的逻辑
            if(adapter.support(adapter)){
                return adapter.login(key,adapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
