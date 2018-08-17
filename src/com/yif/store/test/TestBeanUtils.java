package com.yif.store.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import cn.yif.store.domain.User;

public class TestBeanUtils {
	
	@Test
	public void test01(){
		Map<String, String[]> hashMap = new HashMap<String, String[]>();	
		hashMap.put("username", new String[]{"tom"});
		hashMap.put("password", new String[]{"1234"});
		hashMap.put("birthday", new String[]{"1996-9-1"});
		DateConverter converter = new DateConverter();
		converter.setPattern("yyyy-MM-dd");
		ConvertUtils.register(converter, Date.class);
		User user = new User();
		try {
			BeanUtils.populate(user, hashMap);
			System.out.println(user);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
