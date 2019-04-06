package com.haiyang.proxy.staticproxy;

/**
 * @ClassName: Mother
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/6 10:45
 * @Version 1.0
 */
public class Mother implements Person{
    private Son person;

    public Mother(Son person){
        this.person = person;
    }
    @Override
    public void findLove(){
        System.out.println("母亲物色对象");
        this.person.findLove();
        System.out.println("双方父母同意，确立关系");
    }


}
