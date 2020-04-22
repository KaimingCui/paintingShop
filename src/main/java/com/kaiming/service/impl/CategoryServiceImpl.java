package com.kaiming.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.kaiming.dao.CategoryDao;
import com.kaiming.dao.ProductDao;
import com.kaiming.dao.impl.CategoryDaoImpl;
import com.kaiming.dao.impl.ProductDaoImpl;
import com.kaiming.domain.Category;
import com.kaiming.domain.Product;
import com.kaiming.service.CategoryService;
import com.kaiming.utils.JDBCUtils;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() {
		//调用CategoryDAO来获取list
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.findAll();
	}

	@Override
	public void save(Category category) {
		//调用Dao
		CategoryDao categoryDao = new CategoryDaoImpl();
		categoryDao.save(category);
		
	}

	@Override
	public Category findOne(Integer cid) {
		//调用DAO
		CategoryDao categoryDao = new CategoryDaoImpl();
		return categoryDao.findOne(cid);
	}

	@Override
	public void update(Category category) {
		//调用Dao
		CategoryDao categoryDao = new CategoryDaoImpl();
		categoryDao.update(category);
	}

	@Override
	public void delete(Integer cid) {
		//事务管理，保证多个对数据库的操作的原子性
		//需要在业务层统一创建连接对象，使接下来的DAO操作共用一个连接
		//方法1：在业务层创建连接对象，然后传递给Dao，要在业务层释放
		//方法2：创建一个连接，将连接绑定到当前线程中(ThreadLocal)
		
		//这里使用方法1
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			//开启事务，禁止自动提交事务，改为手动提交
			conn.setAutoCommit(false);
		//要在删除分类前，先将所属分类的商品处理一下
		ProductDao productDao = new ProductDaoImpl();
		//查询没必要传递connection
		List<Product> list = productDao.findByCid(cid);
		
		
		for(Product product:list) {
			product.getCategory().setCid(null);
			productDao.update(conn,product);
		}
		
		//删除分类
		CategoryDao categoryDao = new CategoryDaoImpl();
		categoryDao.delete(conn,cid);
		//提交事务
		conn.commit();
		
		
		}catch(Exception e) {
			e.printStackTrace();
			//出现异常，回滚事务
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			//释放connection
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conn = null;
			}
			
		}
		
	}

}
