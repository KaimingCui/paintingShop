package com.kaiming.service.impl;

import java.util.List;

import com.kaiming.dao.ProductDao;
import com.kaiming.dao.impl.ProductDaoImpl;
import com.kaiming.domain.PageBean;
import com.kaiming.domain.Product;
import com.kaiming.service.ProductService;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findAll() {
		//调用productDao来与数据库交互
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findAll();
	}

	@Override
	public void save(Product product) {
		
		ProductDao productDao = new ProductDaoImpl();
		productDao.save(product);
		
	}

	@Override
	public Product findOne(Integer pid) {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findOne(pid);
	}

	@Override
	public void update(Product product) {
		ProductDao productDao = new ProductDaoImpl();
		productDao.update(product);
		
	}

	@Override
	public void delete(Integer pid) {
		ProductDao productDao = new ProductDaoImpl();
		productDao.delete(pid);
		
	}

	@Override
	public PageBean<Product> findByPage(int page) {
		PageBean<Product> pageBean = new PageBean();
		//封装当前页
		pageBean.setPage(page);
		
		//封装每页的记录数
		int limit = 6;
		pageBean.setLimit(limit);
		
		//封装总记录数
		ProductDao productDao = new ProductDaoImpl();
		int totalCount = productDao.findCount();
		pageBean.setTotalCount(totalCount);
		
		//封装总页数
		int totalPage = 0;
		if(totalCount%limit == 0) {
			totalPage = totalCount/limit;
		}else {
			totalPage = totalCount/limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		
		//封装每页包含的数据
		int begin = (page-1)*limit;
		List<Product> list = productDao.findByPage(begin,limit);
		pageBean.setList(list);
		
		return pageBean;
	}

}
