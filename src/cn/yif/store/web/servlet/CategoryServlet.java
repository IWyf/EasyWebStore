package cn.yif.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.yif.store.domain.Category;
import cn.yif.store.service.CategoryService;
import cn.yif.store.service.serviceImpl.CategoryServiceImpl;
import cn.yif.store.utils.JedisUtils;
import cn.yif.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	public String findAllCats (HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取Redis中的全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		
		
		if (null == jsonStr || "".equals(jsonStr)) {
			//调用业务层获取全部分类
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> allCateg = categoryService.getAllCateg();
			//将全部分类转换为json格式数据
			jsonStr = JSONArray.fromObject(allCateg).toString();
			
			System.out.println(jsonStr);
			
			jedis.set("allCats", jsonStr);
			
			System.out.println("redis缓存中没有数据");
			
			//将全部分类信息响应到客户端
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().print(jsonStr);
			
		}else{
			System.out.println("redis缓存中有数据");
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().print(jsonStr);
		}
		
		JedisUtils.closeJedis(jedis);
		return null;
	
	}
}
