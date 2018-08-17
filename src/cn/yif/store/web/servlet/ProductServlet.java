package cn.yif.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.Product;
import cn.yif.store.service.ProductService;
import cn.yif.store.service.serviceImpl.ProductServiceImpl;
import cn.yif.store.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	
	private ProductService productService = new ProductServiceImpl();

	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取商品pid
		String pid = request.getParameter("pid");
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		return "/jsp/product_info.jsp";
	}
	
	
	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取cid，num
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		PageModel page = productService.findProductsByCidWithPage(cid, curNum);
		request.setAttribute("page", page);
		return "/jsp/product_list.jsp";
	}
}
