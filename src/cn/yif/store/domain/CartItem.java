package cn.yif.store.domain;
/**
 * 购物项
 * @author YiFi
 *
 */

public class CartItem {
	private Product product;
	private int num;			//数量
	private double subTotal;    //小计
	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}
	/**
	 * @return the subTotal
	 */
	public double getSubTotal() {
		return this.product.getShop_price() * num ;
	}
	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}
