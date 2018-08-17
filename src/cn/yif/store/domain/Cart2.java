package cn.yif.store.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 购物车
 * 购物车包含多个购物项
 * @author YiFi
 *
 */
public class Cart2 {
	//总计 / 积分
	private double total;
	//个数不确定的购物项
	private List<CartItem> list = new ArrayList<>();
	
	//添加购物项到购物车
	public void  addCartItemToCart(CartItem cartItem) {
		//判断购物车是否有此商品
		boolean flag = false;
		Iterator<CartItem> iterator = list.iterator();
		while (iterator.hasNext()) {
			CartItem c = iterator.next();
			if (c.getProduct().getPid().equals(cartItem.getProduct().getPid())) {
				c.setNum(c.getNum() + cartItem.getNum());
				flag = true;
				break;
			}
		} 
		if (!flag) {
			 list.add(cartItem); 	
		}
	}
	
	//移除购物项
	public void removeCartItem(String pid){
		//遍历list，删除此pid的项
		Iterator<CartItem> iterator = list.iterator();
		while (iterator.hasNext()) {
			CartItem cartItem = iterator.next();
			if (cartItem.getProduct().getPid().equals(pid)) {
				list.remove(cartItem);
				return;
			}
		}
	}
	
	//清空购物车
	
	public void clearCart() {
		list.clear();
	}
}
