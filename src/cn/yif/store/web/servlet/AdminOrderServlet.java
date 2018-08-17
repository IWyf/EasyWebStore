package cn.yif.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yif.store.domain.Order;
import cn.yif.store.service.OrderService;
import cn.yif.store.service.serviceImpl.OrderServiceImpl;
import cn.yif.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class AdminOrderServlet
 */
public class AdminOrderServlet extends BaseServlet {
	
	public String findOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String state = request.getParameter("state");
		OrderService orderService = new OrderServiceImpl();
		List<Order> list = null;
		if (null == state || "".equals(state)) {
			list = orderService.findAllOrders();
		}else{
			list = orderService.findAllOrders(state);
		}
		
		request.setAttribute("allOrders", list);
		return "/admin/order/list.jsp";
	}
	
	public String findOrderByOidWithAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oid = request.getParameter("id");
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		String jsonStr = JSONArray.fromObject(order.getList()).toString();
		
		response.setContentType("appliction/json;charset=utf-8");
		response.getWriter().println(jsonStr);
		return null;
	}
	
	public String updateOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String oid = request.getParameter("oid");
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		
		order.setState(3);
		orderService.updateOrder(order);
		
		response.sendRedirect("/store_v5/AdminOrderServlet?method=findOrders&state=3");
		return null;
	}
	
}
