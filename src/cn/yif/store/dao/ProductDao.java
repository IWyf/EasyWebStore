package cn.yif.store.dao;

import java.sql.SQLException;
import java.util.List;

import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.Product;

public interface ProductDao {
	public List<Product> findHots() throws SQLException;
	
	public List<Product> findNews() throws SQLException;
	
	public Product findProductByPid(String pid) throws SQLException;
	

	public int findTotalRecords(String cid) throws SQLException;

	public List<Product> findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws SQLException;

	public int findTotalRecords() throws SQLException;


	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws SQLException ;

	public void saveProduct(Product product)  throws SQLException; 
	
}
