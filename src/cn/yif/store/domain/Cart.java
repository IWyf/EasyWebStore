package cn.yif.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 购物车
 * 购物车包含多个购物项
 * @author YiFi
 *
 */
public class Cart {
	//个数不确定的购物项
	Map<String, CartItem> map = new HashMap<String, CartItem>();
	
	//总计 / 积分
	private double total = 0;
	
	//添加购物项到购物车
	public void  addCartItemToCart(CartItem cartItem) {
		String pid = cartItem.getProduct().getPid();	
		if (map.containsKey(pid)) {
			CartItem oldItem = map.get(pid);
			oldItem.setNum(oldItem.getNum() + cartItem.getNum());
		}else{
			map.put(pid, cartItem);
		}
	}
	
	
	//返回map中所有的值
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
	
	//移除购物项
	public void removeCartItem(String pid) {
		map.remove(pid);
	}
	 
	//清空购物车
	 public void clearCart() {
		map.clear();
	}
	
	//获取总金额
	public double getTotal() {
		total = 0;
		for(Entry<String, CartItem> e : map.entrySet()){
			CartItem cartItem = e.getValue();
			total += cartItem.getSubTotal();
		}
		return total;
	}
	
}
