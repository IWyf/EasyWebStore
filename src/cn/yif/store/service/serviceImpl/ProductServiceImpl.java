package cn.yif.store.service.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.yif.store.dao.OrderDao;
import cn.yif.store.dao.ProductDao;
import cn.yif.store.dao.daoImpl.ProductDaoImpl;
import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.Product;
import cn.yif.store.service.ProductService;
import cn.yif.store.utils.BeanFactory;
import cn.yif.store.utils.JDBCUtils;
import jdk.management.resource.internal.TotalResourceContext;

public class ProductServiceImpl implements ProductService {

	ProductDao  productDao = (ProductDao)BeanFactory.createObject("ProductDao");
	@Override
	public List<Product> findHots() throws SQLException {
		
		return productDao.findHots();
	}

	@Override
	public List<Product> findNews() throws SQLException {
		return productDao.findNews();
		
	}
	
	@Override
	public Product findProductByPid(String pid) throws SQLException {
		
		return productDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws SQLException {
		//统计当前分类下的产品个数
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, totalRecords, 12);
		List<Product> list = productDao.findProductsByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		//关联url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid=" + cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws SQLException {
		//创建对象
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		//关联集合
		List<Product>list = productDao.findAllProductsWithPage(pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		//关联URL
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		
		return pm;
	}
	
	@Override
	public void saveProduct(Product product) throws SQLException {
		productDao.saveProduct(product);
	}
	
}
