package cn.yif.store.service.serviceImpl;

import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import cn.yif.store.dao.UserDao;
import cn.yif.store.dao.daoImpl.UserDaoImpl;
import cn.yif.store.domain.User;
import cn.yif.store.service.UserService;
import cn.yif.store.utils.BeanFactory;

public class UserServiceImpl implements UserService {
	
	UserDao userDao = (UserDao)BeanFactory.createObject("UserDao");
	@Override
	public void userRegist(User user) throws SQLException {
		// 实现注册
//		UserDao userDao = new UserDaoImpl();
		userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
//		UserDao userDao = new UserDaoImpl();
		User user = userDao.selectUserFromCode(code);
		if (null != user) {
			user.setState(1);
			user.setCode(null);
			userDao.updateUser(user);
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public User userLogin(User user) throws SQLException {
//		UserDao userDao = new UserDaoImpl();
		User userGet = userDao.getUser(user);
		if (userGet == null) {
			throw new RuntimeException("密码有误！");
		}else if (userGet.getState() == 0){
			throw new RuntimeException("用户未激活");
		}else{
			return userGet;
		}		
	}

}
