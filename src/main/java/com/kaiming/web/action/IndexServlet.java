package com.kaiming.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaiming.domain.Category;
import com.kaiming.domain.PageBean;
import com.kaiming.domain.Product;
import com.kaiming.service.CategoryService;
import com.kaiming.service.ProductService;
import com.kaiming.service.impl.CategoryServiceImpl;
import com.kaiming.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//查询等操作
		//1.查询所有分类
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAll();
		
		//2.分页查询每页的数据
		int page = 1;
		String currentPage = request.getParameter("page");
		if(currentPage!=null && !"".equals(currentPage)) {
			page = Integer.parseInt(currentPage);
		}else {
			page = 1;
		}
		ProductService productService = new ProductServiceImpl();
		PageBean<Product> pageBean = productService.findByPage(page);
		
		
		//页面跳转
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("pageBean", pageBean);
//		System.out.println(pageBean.getList().get(1));
		request.getRequestDispatcher("/index.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
