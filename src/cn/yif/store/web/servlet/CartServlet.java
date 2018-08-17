package cn.yif.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.yif.store.domain.Cart;
import cn.yif.store.domain.CartItem;
import cn.yif.store.domain.Product;
import cn.yif.store.service.ProductService;
import cn.yif.store.service.serviceImpl.ProductServiceImpl;
import cn.yif.store.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	//添加购物项到购物车
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//从session获取购物车
		HttpSession session = request.getSession();
		Cart cart = (Cart)session.getAttribute("cart");
		
		if (null == cart) {
			//获取不到
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("quantity"));
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		
		//获取到购买项
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		cartItem.setSubTotal(cartItem.getSubTotal());
		
		cart.addCartItemToCart(cartItem);
		response.sendRedirect("/store_v5/jsp/cart.jsp");
		return null;
	}
	
	public String removeCartItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取待删商品pid
		String pid = request.getParameter("id");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		cart.removeCartItem(pid);
		
		response.sendRedirect("/store_v5/jsp/cart.jsp");
		return null;
		
	}
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.clearCart();
		response.sendRedirect("/store_v5/jsp/cart.jsp");
		return null;
	
	}
	
}
