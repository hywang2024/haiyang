package com.haiyang.mybatis.v1;

import com.haiyang.mybatis.v1.mapper.Blog;

import java.sql.*;

/**
 * @ClassName: HYExcotor
 * @Description:
 * @Author: hywang
 * @CreateDate: 2019-05-10 21:18
 * @Version: 1.0
 */
public class HYExecutor {
    public <T> T query(String sql, Object paramater) {
        Connection conn = null;
        Statement stmt = null;
        Blog blog = new Blog();

        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gp-mybatis", "root", "123456");

            // 执行查询
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(String.format(sql, paramater));

            // 获取结果集
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String author = rs.getString("author");
                String name = rs.getString("name");
                Date createTime = rs.getDate("create_time");
                blog.setId(id);
                blog.setAuthor(author);
                blog.setName(name);
                blog.setCreateTime(createTime);
            }
            System.out.println(blog);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != stmt) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return (T) blog;
    }
}
