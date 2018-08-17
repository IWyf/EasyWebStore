package cn.yif.store.dao.oracleDaoImpl;

import java.sql.SQLException;
import java.util.List;

import cn.yif.store.dao.ProductDao;
import cn.yif.store.domain.Product;

public class OracleProductDaoImp implements ProductDao {

	@Override
	public List<Product> findHots() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> findNews() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findTotalRecords(String cid) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Product> findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findTotalRecords() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveProduct(Product product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
}
