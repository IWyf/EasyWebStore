package cn.yif.store.dao.daoImpl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.yif.store.dao.CategoryDao;
import cn.yif.store.domain.Category;
import cn.yif.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCateg() throws Exception {
		String sql = "select * from category";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}
	@Override
	public void addCategory(Category c) throws Exception {
		String sql = "insert into category values (?, ?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		runner.update(sql, c.getCid(), c.getCname());
	}
}
