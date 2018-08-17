package cn.yif.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.yif.store.domain.User;

/**
 * Servlet Filter implementation class PrivilegeFilter
 */
public class PrivilegeFilter implements Filter {

    public PrivilegeFilter() {
    }
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//判断session中是否存在已登录用户
		System.out.println("来到过滤器");
		HttpServletRequest req = (HttpServletRequest)request;
		User user = (User)req.getSession().getAttribute("loginUser");
		//如果存在，放行
		if (user != null) {
			chain.doFilter(request, response);			
		}else{
			//不存在，转到提示
			req.setAttribute("msg", "请先登录！");
			req.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
