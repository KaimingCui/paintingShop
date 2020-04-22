package com.kaiming.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kaiming.dao.ProductDao;
import com.kaiming.domain.Product;
import com.kaiming.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findAll() {
		//查询所有商品返回List
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Product> list = null;
		
		try {
			//获得连接
			conn = JDBCUtils.getConnection();
			//编写sql
			String sql = "SELECT * FROM product p, category c WHERE p.cid = c.cid ORDER BY pid DESC";
			//预编译sql
			pstmt = conn.prepareStatement(sql);
			
			//获得结果集
			rs = pstmt.executeQuery();
			
			//遍历结果集
			list = new ArrayList();
			while(rs.next()) {
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				product.setPrice(rs.getDouble("price"));
				
				//封装所属分类
				product.getCategory().setCid(rs.getInt("cid"));
				product.getCategory().setCname(rs.getString("cname"));
				product.getCategory().setCdesc(rs.getString("cdesc"));
				
				list.add(product);
			}
			
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(rs, pstmt, conn);
		}
	
		return null;
	}

	@Override
	public void save(Product product) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "INSERTE INTO product VALUES(NULL,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, product.getPname());
			pstmt.setString(2, product.getAuthor());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setString(5, product.getFilename());
			pstmt.setString(6, product.getPath());
			pstmt.setInt(7,product.getCategory().getCid());
			
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(pstmt, conn);
		}
		
	}

	@Override
	public Product findOne(Integer pid) {
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT * FROM product p,category c WHERE p.cid = c.cid AND p.pid = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pid);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				product.setPrice(rs.getDouble("price"));
				
				//封装所属分类
				product.getCategory().setCid(rs.getInt("cid"));
				product.getCategory().setCname(rs.getString("cname"));
				product.getCategory().setCdesc(rs.getString("cdesc"));
				
				return product;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(pstmt, conn);
		}
		
		return null;
	}

	@Override
	public void update(Product product) {
		Connection conn=null;
		PreparedStatement pstmt = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "UPDATE product SET pname=?,author=?,price=?,description=?,filename=?,path=?,cid=? WHERE pid=?";
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, product.getPname());
			pstmt.setString(2, product.getAuthor());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setString(5, product.getFilename());
			pstmt.setString(6, product.getPath());
			pstmt.setObject(7,product.getCategory().getCid());
			pstmt.setInt(8, product.getPid());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(pstmt, conn);
		}
		
	}

	@Override
	public void delete(Integer pid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = JDBCUtils.getConnection();
			
			String sql = "DELETE FROM product WHERE pid = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pid);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(pstmt, conn);
		}
		
	}

	@Override
	public List<Product> findByCid(Integer cid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Product> list = null;
		
		try {
			//获得连接
			conn = JDBCUtils.getConnection();
			//编写sql
			String sql = "SELECT * FROM product p, category c WHERE p.cid = c.cid WHERE p.cid = ? ORDER BY pid DESC";
			//预编译sql
			pstmt.setInt(1, cid);
			pstmt = conn.prepareStatement(sql);
			
			//获得结果集
			rs = pstmt.executeQuery();
			
			//遍历结果集
			list = new ArrayList();
			while(rs.next()) {
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				product.setPrice(rs.getDouble("price"));
				
				product.getCategory().setCid(rs.getInt("cid"));
				product.getCategory().setCname(rs.getString("cname"));
				product.getCategory().setCdesc(rs.getString("cdesc"));
				
				list.add(product);
			}
			
			return list;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(rs, pstmt, conn);
		}
	
		return null;
	}

	@Override
	public void update(Connection conn, Product product) {
		PreparedStatement pstmt = null;
		
		try {
			
			String sql = "UPDATE product SET pname=?,author=?,price=?,description=?,filename=?,path=?,cid=? WHERE pid=?";
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, product.getPname());
			pstmt.setString(2, product.getAuthor());
			pstmt.setDouble(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setString(5, product.getFilename());
			pstmt.setString(6, product.getPath());
			pstmt.setObject(7,product.getCategory().getCid());
			pstmt.setInt(8, product.getPid());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
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

	@Override
	public int findCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long count = 0L;
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT COUNT(*) AS totalCount FROM product";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				count = rs.getLong("totalCount");
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(rs, pstmt, conn);
		}
		
		
		return count.intValue();
	}

	@Override
	public List<Product> findByPage(int begin, int limit) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> list = null;
		
		try {
			conn = JDBCUtils.getConnection();
			String sql = "SELECT * FROM product LIMIT ?,?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, begin);
			pstmt.setInt(2, limit);
			
			rs = pstmt.executeQuery();
			list = new ArrayList();
			while(rs.next()) {
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setAuthor(rs.getString("author"));
				product.setDescription(rs.getString("description"));
				product.setFilename(rs.getString("filename"));
				product.setPath(rs.getString("path"));
				product.setPrice(rs.getDouble("price"));
				
				list.add(product);
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.release(rs, pstmt, conn);
		}
		
		
		return list;
	}

}
