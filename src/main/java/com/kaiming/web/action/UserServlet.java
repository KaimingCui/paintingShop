package com.kaiming.web.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaiming.domain.User;
import com.kaiming.service.UserService;
import com.kaiming.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if("login".equals(method)) {
			login(request,response);
		}else if("logout".equals(method)) {
			logout(request,response);
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1.销毁session
		request.getSession().invalidate();
		//2.页面重定向到登录页面
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 接收用户登录数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username + ":" + password);
		//路径问题
		String realpath = this.getServletContext().getRealPath("/"+request.getContextPath() + "/upload");

		
		System.out.println("realpath:" + realpath);
		//数据封装
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);	
		//处理数据，交给业务层来完成
		UserService userService = new UserServiceImpl();
		User existUser = userService.login(user);
		
		//判断用户是否存在
		//用户不存在或不正确
		if(existUser == null) {
			request.setAttribute("msg","用户名或密码不正确");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}else {
			//登录成功，保存信息并重定向
			request.getSession().setAttribute("existUser", existUser);
			response.sendRedirect(request.getContextPath() + "/CategoryServlet?method=findAll");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
