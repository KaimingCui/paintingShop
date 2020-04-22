package com.kaiming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kaiming.dao.CategoryDao;
import com.kaiming.domain.Category;
import com.kaiming.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() {
		//与数据库交互得到所有category
		//JDBC
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Category> list = null;
				
		try {
			
		//获得连接
		conn = JDBCUtils.getConnection();
		//编写SQL
		String sql = "SELECT * FROM category";
		//预编译SQL 使用preparedStatement
		pstmt = conn.prepareStatement(sql);
		//设置参数
		//执行SQL
		rs = pstmt.executeQuery();
		//获得结果并封装数据，添加到list中，返回list
		list = new ArrayList<>();
		while(rs.next()) {
			Category category = new Category();
			category.setCid(rs.getInt("cid"));
			category.setCname(rs.getString("cname"));
			category.setCdesc(rs.getString("cdesc"));
			
			list.add(category);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
		//释放资源
			JDBCUtils.release(rs, pstmt, conn);
		}
		
		return list;
	}

	@Override
	public void save(Category category) {
		//与数据库交互，插入一条数据到数据库中
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			//获得连接
			conn = JDBCUtils.getConnection();
			//编写SQL
			String sql = "INSERT INTO category VALUES(null,?,?)";
			//预编译sql
			pstmt = conn.prepareStatement(sql);
			//设置参数
			pstmt.setString(1, category.getCname());
			pstmt.setString(2, category.getCdesc());
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//释放资源
			JDBCUtils.release(pstmt, conn);
		}
		
	}

	@Override
	public Category findOne(Integer cid) {
		//查询一条
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT * FROM category WHERE cid = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Category category = new Category();
				category.setCid(rs.getInt("cid"));
				category.setCname(rs.getString("cname"));
				category.setCdesc(rs.getString("cdesc"));
				
				return category;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(rs, pstmt, conn);
		}
		
		
		return null;
	}

	@Override
	public void update(Category category) {
		//更新数据
		Connection conn = null;
		PreparedStatement pstmt =null;
		
		try {
			conn  = JDBCUtils.getConnection();
			String sql = "UPDATE category SET cname = ?, cdesc = ? WHERE cid = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, category.getCname());
			pstmt.setString(2, category.getCdesc());
			pstmt.setInt(3, category.getCid());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(pstmt, conn);
		}
		
	}

	@Override
	public void delete(Integer cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "DELETE FROM category WHERE cid = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cid);
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(pstmt,conn);
		}
		
	}

	@Override
	public void delete(Connection conn, Integer cid) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "DELETE FROM category WHERE cid = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cid);
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pstmt=null;
			}
			
		}
		
	}

}
