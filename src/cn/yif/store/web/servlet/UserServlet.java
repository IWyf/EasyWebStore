package cn.yif.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import cn.yif.store.domain.User;
import cn.yif.store.service.UserService;
import cn.yif.store.service.serviceImpl.UserServiceImpl;
import cn.yif.store.utils.MailUtils;
import cn.yif.store.utils.MyBeanUtils;
import cn.yif.store.utils.UUIDUtils;
import cn.yif.store.web.base.BaseServlet;
import sun.awt.RepaintArea;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {

	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "/jsp/register.jsp";
	} 
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		return "/jsp/login.jsp";
	} 
	
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(request.getParameter("name"));
		//接收参数表参数
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		System.out.println(user.toString());
		try {
			//调用业务层注册功能
			UserService userService = new UserServiceImpl();
			userService.userRegist(user);
			//注册成功,向用户邮箱发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			
			request.setAttribute("msg", "用户注册成功，请激活");
		} catch (Exception e) {
			//注册失败
			request.setAttribute("msg", "用户注册失败，请重新注册");
		}
		return "/jsp/info.jsp";
	} 
	
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String code = request.getParameter("code");
		System.out.println(code);
		UserService service = new UserServiceImpl();
		boolean flag;
		try {
			flag = service.userActive(code);
			if(flag){
				
				request.setAttribute("msg", "您已激活成功，请登录");
				return "/jsp/login.jsp";
			}else{
				request.setAttribute("msg", "对不起，激活码无效");
				return "/jsp/info.jsp";
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/jsp/error.jsp";
	} 

	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 User user = new User();
		 MyBeanUtils.populate(user, request.getParameterMap());
		 //调用service层
		 UserService userService = new UserServiceImpl();
		 
		 try{
			user = userService.userLogin(user);
			request.getSession().setAttribute("loginUser", user);
			response.sendRedirect("/store_v5/index.jsp");
			return null;
		 }catch (Exception e) {
			// TODO: handle exception
			 String msg = e.getMessage();
			 System.err.println(msg);
			 //在request中放入失败信息
			 request.setAttribute("msg", msg);
			 return "/jsp/login.jsp";
		 }
	}
	
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		response.sendRedirect("/store_v5/index.jsp");
		return null;
	}
}
