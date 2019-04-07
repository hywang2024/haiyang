package com.haiyang.template.jdbc;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

/**
 * @ClassName: MemberDao
 * @Description:
 * @Author Administrator
 * @CreateDate 2019/4/7 19:12
 * @Version 1.0
 */
public class MemberDao extends JDBCTemplate{

    public MemberDao(DataSource dataSource) {
        super(dataSource);
    }
    public List<?> selectAll(){
        String sql = "select * from t_member";
        return super.executeQuery(sql, new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet rs, int rowNum) throws Exception {
                Member member = new Member();
                //字段过多，原型模式
                member.setUsername(rs.getString("username"));
                member.setPassword(rs.getString("password"));
                member.setAge(rs.getInt("age"));
                member.setAddr(rs.getString("addr"));
                return member;
            }
        },null);
    }
}
