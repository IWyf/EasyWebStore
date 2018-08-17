package cn.yif.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yif.store.domain.Product;
import cn.yif.store.service.CategoryService;
import cn.yif.store.service.ProductService;
import cn.yif.store.service.serviceImpl.CategoryServiceImpl;
import cn.yif.store.service.serviceImpl.ProductServiceImpl;
import cn.yif.store.web.base.BaseServlet;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
       
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		/*//调用业务层功能:获取全部分类信息，返回集合
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCateg();
		//将返回的集合放入request
		req.setAttribute("allCategs", list);*/
		
		
		//调用业务层， 查询最新商品，查询最热商品，返回两个集合
		ProductService productService = new ProductServiceImpl();
		List<Product> list01 = productService.findHots();
		List<Product> list02 = productService.findNews();
		
		//将2个集合放入request
		req.setAttribute("hots", list01);
		req.setAttribute("news", list02);
		//转发到真实的首页
		return "/jsp/index.jsp";
	}
}
