package cn.yif.store.service;

import java.util.List;

import cn.yif.store.domain.Category;

public interface CategoryService {

	List<Category> getAllCateg() throws Exception;

	void addCategory(Category c) throws Exception;

}
