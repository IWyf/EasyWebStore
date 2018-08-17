package cn.yif.store.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.yif.store.dao.ProductDao;
import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.Product;
import cn.yif.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao{

	@Override
	public List<Product> findHots() throws SQLException {
		String sql = "select * from product where pflag = 0 and is_hot = 1 order by pdate desc limit 0, 9";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}
	
	@Override
	public List<Product> findNews() throws SQLException {
		String sql = "select * from product where pflag = 0 order by pdate desc limit 0, 9";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
		
	}
	
	@Override
	public Product findProductByPid(String pid) throws SQLException{
		String sql = "select * from product where pid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
	
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
		
	}

	
	@Override
	public int findTotalRecords(String cid) throws SQLException {
		String sql = "select count(0) from product where cid= ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) runner.query(sql, new ScalarHandler(), cid);
		return num.intValue();
	}

	@Override
	public List<Product> findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws SQLException {
		String sql = "select * from product where cid = ? limit ?, ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class), cid, startIndex, pageSize);
	}
	
	@Override
	public int findTotalRecords() throws SQLException {
		String sql = "select count(0) from product";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) runner.query(sql, new ScalarHandler());
		return num.intValue();
	}
	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws SQLException {
		String sql = "select * from product order by pdate desc limit ?, ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class),startIndex, pageSize);
	}
	
	@Override
	public void saveProduct(Product product) throws SQLException {
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params={product.getPid(), product.getPname(), product.getMarket_price(), product.getShop_price(),
						 product.getPimage(), product.getPdate(), product.getIs_hot(), product.getPdesc(), product.getPflag(),product.getCid()};
		runner.update(sql, params);
	}
}
