package cn.yif.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import cn.yif.store.domain.Category;
import cn.yif.store.domain.PageModel;
import cn.yif.store.domain.Product;
import cn.yif.store.service.CategoryService;
import cn.yif.store.service.ProductService;
import cn.yif.store.service.serviceImpl.CategoryServiceImpl;
import cn.yif.store.service.serviceImpl.ProductServiceImpl;
import cn.yif.store.utils.UUIDUtils;
import cn.yif.store.utils.UploadUtils;
import cn.yif.store.web.base.BaseServlet;

public class AdminProductServlet extends BaseServlet {
       
	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用业务层查全部商品
		ProductService productService = new ProductServiceImpl();
		PageModel pm;
		try {
			pm = productService.findAllProductsWithPage(curNum);
			//将pageModel放入request
			request.setAttribute("page", pm);		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";
	}
	
	
	
	public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCateg();
		request.setAttribute("allCats", list);
		return "/admin/product/add.jsp";
	}
	
	public String addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//存储表单中的数据
			Map<String, String> map = new HashMap<String, String>();
			//利用req.getInputStream();获取到请求体中全部数据,进行拆分和封装
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("utf-8");
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					//如果当前的FileItem对象是普通项
					//将普通项上name属性的值作为键,将获取到的内容作为值,放入MAP中
					// {username<==>tom,password<==>1234}
					map.put(item.getFieldName(), item.getString("utf-8"));
				}else{
					//如果当前的FileItem对象是上传项
					//获取到原始的文件名称
					String oldFileName = item.getName();
					//获取到要保存文件的名称
					String newFileName = UploadUtils.getUUIDName(oldFileName);
					
					InputStream is = item.getInputStream();
					
					//获取到当前项目下products/3下的真实路径
					//D:\tomcat\tomcat71_sz07\webapps\store_v5\products\3
					String realPath = getServletContext().getRealPath("/products/3/");
					String dir = UploadUtils.getDir(newFileName);
					String path = realPath + dir;
				
					File newDir = new File(path);
					if (!newDir.exists()) {
						newDir.mkdirs();
					}
					
					//在服务端创建一个空文件(后缀必须和上传到服务端的文件名后缀一致)
					File finalFile = new File(newDir, newFileName);
					if (!finalFile.exists()) {
						finalFile.createNewFile();
					}
					
					OutputStream os = new FileOutputStream(finalFile);
					IOUtils.copy(is, os);
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					
					map.put("pimage", "/products/3/"+dir+"/"+newFileName);
				}
			}
			
			Product product = new Product();
			//7_利用BeanUtils将MAP中的数据填充到Product对象上
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
//			
//			//8_调用servcie_dao将user上携带的数据存入数据仓库,重定向到查询全部商品信息路径
			ProductService ProductService=new ProductServiceImpl();
			ProductService.saveProduct(product);
			
			response.sendRedirect("/store_v5/AdminProductServlet?method=findAllProductsWithPage&num=1");
		} catch (Exception e) {
		}
		return null;
	}

}
