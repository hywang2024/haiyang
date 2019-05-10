package com.haiyang.mybatis.v1.mapper;

import com.haiyang.mybatis.annotation.HYEntity;
import com.haiyang.mybatis.annotation.HYSelect;

/**
 * @ClassName: BlogMapper
 * @Description: 映射
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:08
 * @Version: 1.0
 */
@HYEntity(Blog.class)
public interface BlogMapper {
    /**
     * 根据主键查询文章
     *
     * @param bid
     * @return
     */
    @HYSelect("select * from blog where bid = %d")
    public Blog selectBlogById(Integer bid);
}
