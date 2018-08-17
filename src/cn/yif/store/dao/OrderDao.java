package cn.yif.store.dao;

import java.sql.Connection;
import java.util.List;

import cn.yif.store.domain.Order;
import cn.yif.store.domain.OrderItem;
import cn.yif.store.domain.User;

public interface OrderDao {

	void saveOrder(Order order) throws Exception;

	void saveOrderItem(OrderItem item) throws Exception;

	int getTotalRecords(User user) throws Exception;

	List findMyOrderWithPage(User user, int startIndex, int pageSize) throws Exception;

	Order findOrderByOid(String oid) throws Exception;
	
	void updateOrder(Order order) throws Exception;

	List<Order> findAllOrders() throws Exception;

	List<Order> findAllOrders(String state) throws Exception;
}
