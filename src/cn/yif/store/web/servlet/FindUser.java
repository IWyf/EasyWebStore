package cn.yif.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yif.store.dao.UserDao;
import cn.yif.store.dao.daoImpl.UserDaoImpl;


/**
 * 根据用户名 查询数据库中是否存在此数据
 */
public class FindUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("utf-8");
			String username = request.getParameter("username");
			System.out.println(username);
			UserDao dao = new UserDaoImpl();
			boolean flag = dao.getUserFromUsername(username);
			if(flag){
				response.getWriter().println(1);
			}else{
				response.getWriter().println(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
