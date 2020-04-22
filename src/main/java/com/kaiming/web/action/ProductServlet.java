package com.kaiming.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaiming.domain.Category;
import com.kaiming.domain.Product;
import com.kaiming.service.CategoryService;
import com.kaiming.service.ProductService;
import com.kaiming.service.impl.CategoryServiceImpl;
import com.kaiming.service.impl.ProductServiceImpl;
import com.kaiming.utils.UploadUtils;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//接收method参数
		String method = request.getParameter("method");
		
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
	 * 删除商品的方法
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//接收数据
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		
		//调用业务层
		ProductService productService = new ProductServiceImpl();
		//查询商品信息
		Product product = productService.findOne(pid);
		//删除图片
		String path = product.getPath();
		if(path != null&&!"".equals(path)) {
			//获得文件的磁盘绝对路径
			String realPath = this.getServletContext().getRealPath(path);
			File file = new File(realPath);
			if(file.exists()) {
				file.delete();
			}
		}
		//删除商品
		productService.delete(pid);
		
		
		//跳转到显示商品页面
		response.sendRedirect(request.getContextPath()+"/ProductServlet?method=findAll");
		
	}

	
	/**
	 * 修改商品
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//接收数据
		Map<String,String> map = UploadUtils.uploadFile(request);
		//封装数据
		Product product = new Product();
		product.setPid(Integer.parseInt(map.get("pid")));
		product.setAuthor(map.get("author"));
		product.setDescription(map.get("description"));
		product.setPname(map.get("pname"));
		product.setPrice(Double.parseDouble(map.get("price")));
		product.setFilename(map.get("filename"));
		product.setPath(map.get("path"));
		product.getCategory().setCid(Integer.parseInt(map.get("cid")));
		
		//调用业务层处理数据
		ProductService productService = new ProductServiceImpl();
		productService.update(product);
		
		//跳转页面
		response.sendRedirect(request.getContextPath()+"/ProductServlet?method=findAll");
		
	}

	
	/**
	 * 编辑商品
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void edit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//接收数据
		Integer pid = Integer.parseInt(request.getParameter("pid"));
		//调用业务层处理
		//查询要修改的商品信息
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findOne(pid);
		
		//查询所有分类
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> categoryList = categoryService.findAll();
		//跳转到编辑页面
		request.setAttribute("product", product);
		request.setAttribute("categoryList", categoryList);
		request.getRequestDispatcher("/admin/product_update.jsp").forward(request,response);
		
	}

	/**
	 * 后台商品管理保存商品方法，添加商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//文件上传
		System.out.println("save功能启动");
		Map<String,String> map = UploadUtils.uploadFile(request);
		System.out.println(map);
		//将数据封装
		Product product = new Product();
		product.setPname(map.get("pname"));
		product.setAuthor(map.get("author"));
		product.setPrice(Double.parseDouble(map.get("price")));
		product.setDescription(map.get("description"));
		product.setFilename(map.get("filename"));
		product.setPath(map.get("path"));
		product.getCategory().setCid(Integer.parseInt(map.get("cid")));
		//处理数据
		ProductService productService = new ProductServiceImpl();
		productService.save(product);
		
		//跳转页面，显示商品列表
		response.sendRedirect(request.getContextPath() + "/ProductServlet?method=findAll");
	}


	/**
	 * 跳转到添加页面 同时查询出所有分类信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void saveUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//查询所有分类信息
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.findAll();
		//页面跳转
		request.setAttribute("categoryList", list);
		request.getRequestDispatcher("/admin/product_add.jsp").forward(request,response);
	}

	/**
	 * 查询所有的商品
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//没有参数可以接收
		//调用业务层
		ProductService productService = new ProductServiceImpl();
		List<Product> list = productService.findAll();
		//跳转页面
		request.setAttribute("list",list);
		request.getRequestDispatcher("/admin/product_list.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
