package com.haiyang.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @ClassName: TestJson
 * @Description:  fastjson  容易出问题的地方
 * @Author: hywang
 * @CreateDate: 2018/11/23 3:45 PM
 * @Version: 1.0
 */
public class TestJson {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a","a");
        jsonObject.put("b","[b,b]");
        jsonObject.put("c",null);//保证null节点不被过滤调

        //json toString的四种方法
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject.toJSONString());
        String s1 = JSONObject.toJSONString(jsonObject, SerializerFeature.WRITE_MAP_NULL_FEATURES);
        System.out.println(s1);
       /* String s = JSONObject.toJSONString(jsonObject);
        System.out.println(s);

        // 增加一个参数，保证null 不被过滤
        String s1 = JSONObject.toJSONString(jsonObject, SerializerFeature.WRITE_MAP_NULL_FEATURES);
        System.out.println(s1);

        JSONObject jsonObject1 = JSONObject.parseObject(s);
        System.out.println(jsonObject);
        net.sf.json.JSONObject jsonObject2 = net.sf.json.JSONObject.fromObject(s);
        System.out.println(jsonObject2);*/

    }
}
