package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bean.User;

@WebFilter("/*")
public class Loginfilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.print("登陆拦截器销毁");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String servletPathString = req.getServletPath();
		if(servletPathString.contains("login.jsp") || servletPathString.contains("LoginServlet")  
			|| servletPathString.contains("introduce.jsp")|| servletPathString.endsWith(".css") 
			||servletPathString.endsWith(".png") ||servletPathString.endsWith(".js")) {
			chain.doFilter(req,resp);
		}else {
			User user = (User)req.getSession().getAttribute("userInfo");
			if (user == null) {
				resp.sendRedirect("login.jsp");	
			}else {
				chain.doFilter(req, resp);
			}
		}			
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.print("初始化");
	}

}
