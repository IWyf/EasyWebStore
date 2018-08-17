package cn.yif.store.service.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.yif.store.dao.OrderDao;
import cn.yif.store.dao.daoImpl.OrderDaoImpl;
import cn.yif.store.domain.Order;
import cn.yif.store.domain.OrderItem;
import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.User;
import cn.yif.store.service.OrderService;
import cn.yif.store.utils.BeanFactory;
import cn.yif.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	
	OrderDao orderDao = (OrderDao)BeanFactory.createObject("OrderDao");
	
	
	@Override
	public void saveOrder(Order order) throws SQLException{
		// 保存订单和订单下的所有订单项
		try {
			JDBCUtils.startTransaction();
			orderDao.saveOrder(order);
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
			
			e.printStackTrace();
		}
		
		/*Connection conn = null;
		try {
			//获取链接
			conn = JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//保存订单
			orderDao.saveOrder(conn, order);
			//保存订单项
			
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn, item);
			}
			conn.commit(); 
		} catch (Exception e) {
			conn.rollback();
		}finally {
			if (null != conn) {
				conn.close();
				conn = null;
			}
		}*/
		
	}
	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		
		//1_创建PageModel对象,目的:计算并且携带分页参数
		//select count(*) from orders where uid=?
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, totalRecords, 3);
		//2_关联集合  select * from orders where uid=? limit ? ,?
		List list = orderDao.findMyOrderWithPage(user, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}
	
	
	@Override
	public Order findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
	}
	
	@Override
	public void updateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
	}
	
	@Override
	public List<Order> findAllOrders() throws Exception {
		
		return orderDao.findAllOrders();
	}
	
	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		return  orderDao.findAllOrders(state);
	}
}
