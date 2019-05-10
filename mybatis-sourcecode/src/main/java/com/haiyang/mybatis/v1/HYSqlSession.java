package com.haiyang.mybatis.v1;

/**
 * @ClassName: HYSqlSession
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:17
 * @Version: 1.0
 */
public class HYSqlSession {
    private HYConfiguration configuration;
    private HYExecutor executor;

    public <T> T selectOne(String statementId, Object paramater){
        // 根据statementId拿到SQL
        String sql = configuration.sqlMappings.getString(statementId);
        if(null != sql && !"".equals(sql)){
            return executor.query(sql, paramater );
        }
        return null;
    }

    public <T> T getMapper(Class clazz){
        return configuration.getMapper(clazz, this);
    }

}
