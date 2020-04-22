package com.kaiming.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaiming.domain.Category;
import com.kaiming.service.CategoryService;
import com.kaiming.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断是什么请求
		//localhost:8080/shop/CategoryServlet?method=findAll
		String method = request.getParameter("method");
		
		//查询所有分类
		if("findAll".equals(method)) {
			findAll(request,response);
		}else if("saveUI".equals(method)) {
			saveUI(request,response);
		}else if("save".equals(method)) {
			save(request,response);
		}else if("edit".equals(method)) {
			edit(request,response);
		}else if("update".equals(method)) {
			update(request,response);
		}else if("delete".equals(method)) {
			delete(request,response);
		}
	}




	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收参数
		//封装数据
		//调用业务层服务
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.findAll();
		//页面跳转
		request.setAttribute("list", list);
		request.getRequestDispatcher("/admin/category_list.jsp").forward(request,response);
		
	}
	/**
	 * 后台分类管理跳转到添加分类页面
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void saveUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/admin/category_add.jsp").forward(request,response);
		
	}
	
	/**
	 * 后台分类管理，添加一条分类到数据库
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收数据
		String cname = request.getParameter("cname");
		String cdesc = request.getParameter("cdesc");
		//封装数据
		Category category = new Category();
		category.setCname(cname);
		category.setCdesc(cdesc);
		//调用业务层处理数据
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.save(category);
		//页面跳转
		request.getRequestDispatcher("/CategoryServlet?method=findAll").forward(request, response);;
		
	}
	
	private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收数据
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		//调用业务层
		CategoryService categoryService = new CategoryServiceImpl();
		Category category = categoryService.findOne(cid);
		//页面跳转到修改页面，显示要编辑的对象
		request.setAttribute("category", category);
		request.getRequestDispatcher("/admin/category_update.jsp").forward(request, response);
	
	}
	

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//接收数据
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		String cname = request.getParameter("cname");
		String cdesc = request.getParameter("cdesc");
		//封装数据
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		category.setCdesc(cdesc);
		//调用业务层
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.update(category);
		//页面跳转
		response.sendRedirect(request.getContextPath() + "/CategoryServlet?method=findAll");
	}
	

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//接收数据
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		//调用业务层
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.delete(cid);
		//页面跳转
		response.sendRedirect(request.getContextPath() + "/CategoryServlet?method=findAll");
	}




}
