package com.kaiming.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kaiming.domain.User;

/**
 * Servlet Filter implementation class PrivilegeFilter
 */
@WebFilter(urlPatterns = {"/admin/*","/ProductServlet","/CategoryServlet"})
public class PrivilegeFilter implements Filter {


	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//获得session中的User属性
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		User existUser = (User)session.getAttribute("existUser");
		
		//判断是否已经登录
		if(existUser == null) {
			//没有登录
			request.setAttribute("msg", "您还没有登录，请先登录");
			request.getRequestDispatcher("/login.jsp").forward(request,response);
			return;
		}
		
		
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
