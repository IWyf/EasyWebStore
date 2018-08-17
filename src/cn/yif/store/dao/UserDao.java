package cn.yif.store.dao;

import java.sql.SQLException;

import cn.yif.store.domain.User;

public interface UserDao {

	void userRegist(User user) throws SQLException;

	User selectUserFromCode(String code) throws SQLException;

	void updateUser(User user) throws SQLException;

	User getUser(User user) throws SQLException;

	boolean getUserFromUsername(String username) throws SQLException;

}
