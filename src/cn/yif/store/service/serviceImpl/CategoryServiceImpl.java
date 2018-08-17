package cn.yif.store.service.serviceImpl;

import java.util.List;

import cn.yif.store.dao.CategoryDao;
import cn.yif.store.dao.daoImpl.CategoryDaoImpl;
import cn.yif.store.domain.Category;
import cn.yif.store.service.CategoryService;
import cn.yif.store.utils.BeanFactory;
import cn.yif.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {
	
	CategoryDao dao = (CategoryDao)BeanFactory.createObject("CategoryDao");
	@Override
	public List<Category> getAllCateg() throws Exception {
		
		
		return dao.getAllCateg();
	}
	@Override
	public void addCategory(Category c) throws Exception {
		dao.addCategory(c);
		//更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

}
