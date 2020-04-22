package com.kaiming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.kaiming.dao.UserDao;
import com.kaiming.domain.User;
import com.kaiming.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User login(User user) {
		//JDBC操作
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
		//获得连接
			conn = JDBCUtils.getConnection();
		//编写SQL
			String sql = "SELECT * FROM user WHERE username = ? and password = ?";
		//预编译SQL
			pstmt = conn.prepareStatement(sql);
		//设置参数
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
		//执行
			rs = pstmt.executeQuery();
			if(rs.next()) {
				User existUser = new User();
				existUser.setUid(rs.getInt("uid"));
				existUser.setUsername(rs.getString("username"));
				existUser.setPassword(rs.getString("password"));
				
				return existUser;
			}else {
				return null;
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//释放资源
			JDBCUtils.release(rs, pstmt, conn);
		}	
		
		return null;
	}

}
