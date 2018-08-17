package cn.yif.store.dao;

import java.util.List;

import cn.yif.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCateg() throws Exception;

	void addCategory(Category c) throws Exception;
	
}
