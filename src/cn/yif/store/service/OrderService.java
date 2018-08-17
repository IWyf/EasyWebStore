package cn.yif.store.service;

import java.util.List;

import cn.yif.store.domain.Order;
import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.User;

public interface OrderService {

	void saveOrder(Order order) throws Exception;

	PageModel findMyOrdersWithPage(User user, int curNum) throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	List<Order> findAllOrders() throws Exception ;

	List<Order> findAllOrders(String state) throws Exception;

}
