package cn.yif.store.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class BeanFactory {
	//解析xml
	public static Object createObject(String name) {
		try {
			System.out.println("调用BeanFactory");
			SAXReader reader = new SAXReader();
			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
			Document doc = reader.read(is);

			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			for (Element element : elements) {
				String id = element.attributeValue("id");
				if (id.equals(name)) {
					String clzStr = element.attributeValue("class");
					//反射
					Class<?> clazz = Class.forName(clzStr);
					return clazz.newInstance();	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static void main(String[] args) throws Exception {
//		UserDao ud=(UserDao)BeanFactory.createObject("UserDao");
//		User user=new User();
//		user.setUsername("aaa");
//		user.setPassword("aaa");
//		User uu = ud.getUser(user);
//		System.out.println(uu);
//	}
	
}
