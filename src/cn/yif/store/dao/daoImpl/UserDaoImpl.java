package cn.yif.store.dao.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.yif.store.dao.UserDao;
import cn.yif.store.domain.User;
import cn.yif.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "insert into user values(?, ?, ?, ?, ? , ?, ?, ?, ?, ?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		
		Object[] params = {user.getUid(), user.getUsername(), user.getPassword(),
						   user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(),
						   user.getSex(), user.getState(), user.getCode()};
		runner.update(sql, params);
	}

	@Override
	public User selectUserFromCode(String code) throws SQLException {
		String sql = "select * from user where code = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<User>(User.class), code);
		
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "update user set  username=?, password=?,"
				     + " name=?, email=?, telephone=?, birthday=?, sex=?, state=?, code=? where uid=?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUsername(), user.getPassword(),
						   user.getName(), user.getEmail(), user.getTelephone(), 
						   user.getBirthday(), user.getSex(), user.getState(), user.getCode(), user.getUid()};
		runner.update(sql, params);
	}

	@Override
	public User getUser(User user) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		
		return runner.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
		
	}

	@Override
	public boolean getUserFromUsername(String username) throws SQLException {
		String sql = "select username from user where username=?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		
	    User user = runner.query(sql, new BeanHandler<User>(User.class),username);
		if(user != null) return true;
		else{
			return false;
		}
	}
	public static void main(String[] args) throws SQLException {
		UserDaoImpl impl = new UserDaoImpl();
		System.out.println(impl.getUserFromUsername("admin"));
	}
}
