package com.haiyang.mybatis.v1.mapper;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: Blok
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:06
 * @Version: 1.0
 */
@Data
public class Blog {
    private Integer id;
    private String name;
    private String author;
    private Date createTime;
}
