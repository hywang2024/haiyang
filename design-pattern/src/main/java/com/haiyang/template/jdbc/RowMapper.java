package com.haiyang.template.jdbc;

import java.sql.ResultSet;

/**
 * @ClassName: RowMapper
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:02
 * @Version 1.0
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws Exception;
}
