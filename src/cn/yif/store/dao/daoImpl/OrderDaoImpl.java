package cn.yif.store.dao.daoImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.yif.store.dao.OrderDao;
import cn.yif.store.domain.Order;
import cn.yif.store.domain.OrderItem;
import cn.yif.store.domain.Product;
import cn.yif.store.domain.User;
import cn.yif.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Order order) throws Exception {
		String sql = "INSERT INTO orders values(?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(), 
						   order.getState(), order.getAddress(), order.getName(),
						   order.getTelephone(), order.getUser().getUid()};
		runner.update(sql, params);
	}

	@Override
	public void saveOrderItem(OrderItem item) throws Exception {
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";;
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {item.getItemid(), item.getQuantity(), item.getTotal(),
				           item.getProduct().getPid(), item.getOrder().getOid()};
		runner.update(sql, params);
	}

	@Override
	public int getTotalRecords(User user) throws Exception {
		String sql = "select count(0) from orders where uid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)runner.query(sql, new ScalarHandler(), user.getUid());
		
		return num.intValue();
	}
	
	@Override
	public List findMyOrderWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql = "select * from orders where uid = ? limit ?, ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> orderList = runner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);
		// 通过订单查询订单项
		for (Order order : orderList) {
			//查询每笔订单下的订单项
			String oid = order.getOid();
			sql = "select * from orderItem o, product p where o.pid = p.pid and oid = ?";
			List<Map<String, Object>> list = runner.query(sql, new MapListHandler(), oid);
			//每笔订单
			for (Map<String, Object> map : list) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();				
				DateConverter converter = new DateConverter();
				converter.setPattern("yyyy-MM-dd");
				ConvertUtils.register(converter, Date.class);
				BeanUtils.populate(orderItem, map);
				BeanUtils.populate(product, map);
				orderItem.setProduct(product);
				order.getList().add(orderItem);
			}
		}
		return orderList;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql = "select * from orders where oid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Order order =  runner.query(sql, new BeanHandler<Order>(Order.class), oid);
		//根据订单id查询订单下所有的订单项以及订单项对应的商品信息
		sql="select * from orderitem o, product p where o.pid=p.pid and oid=?";
		List<Map<String, Object>> list02 = runner.query(sql, new MapListHandler(), oid);
		//遍历list
		for (Map<String, Object> map : list02) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			//将map中属于orderItem的数据自动填充到orderItem对象上
			BeanUtils.populate(orderItem, map);
			//将map中属于product的数据自动填充到product对象上
			BeanUtils.populate(product, map);
			
			//让每个订单项和商品发生关联关系
			orderItem.setProduct(product);
			//将每个订单项存入订单下的集合中
			order.getList().add(orderItem);
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		String sql="UPDATE orders SET ordertime=? ,total=? ,state= ?, address=?,NAME=?, telephone =? WHERE oid=?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		Object[] params={order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		qr.update(sql,params);
		
	}
	
	@Override
	public List<Order> findAllOrders() throws Exception {
		String sql = "select * from orders";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class));
	}
	
	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		String sql = "select * from orders where state=?";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class), state);
	}
}
