package com.haiyang.prototype;

/**
 * @ClassName: Client
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-04-04 17:57
 * @Version: 1.0
 */
public class Client {

    private Prototype prototype;

    public Client(Prototype prototype) {
        this.prototype = prototype;
    }

    public Prototype startClone(Prototype concretePrototype) {
        return (Prototype) concretePrototype.clone();
    }

}
