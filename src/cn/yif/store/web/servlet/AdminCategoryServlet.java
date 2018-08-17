package cn.yif.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yif.store.domain.Category;
import cn.yif.store.service.CategoryService;
import cn.yif.store.service.serviceImpl.CategoryServiceImpl;
import cn.yif.store.utils.UUIDUtils;
import cn.yif.store.web.base.BaseServlet;

public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取全部分类信息
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> allCateg = categoryService.getAllCateg();
		//全部分类信息放入request
		request.setAttribute("allCats", allCateg);
		//转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";

	}
	
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "/admin/category/add.jsp";
	}
	
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取分类名称
		String cname=req.getParameter("cname");
		//创建分类ID
		String id = UUIDUtils.getId();
		Category c = new Category();
		c.setCid(id);
		c.setCname(cname);
		//调用业务层添加分类功能
		CategoryService CategoryService = new CategoryServiceImpl();
		CategoryService.addCategory(c);
		//重定向到查询全部分类信息
		resp.sendRedirect("/store_v5/AdminCategoryServlet?method=findAllCats");
		return null;
	}
}
