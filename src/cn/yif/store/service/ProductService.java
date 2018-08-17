package cn.yif.store.service;

import java.sql.SQLException;
import java.util.List;

import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.Product;

public interface ProductService {

	public List<Product> findHots() throws SQLException;
	public List<Product> findNews() throws SQLException;
	public Product findProductByPid(String pid) throws SQLException;
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws SQLException;
	public PageModel findAllProductsWithPage(int curNum) throws SQLException;
	public void saveProduct(Product product) throws SQLException; 
}
