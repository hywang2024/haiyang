package com.javax.core.common.jdbc;


/**
 * @ClassName: Order
 * @Description: 排序
 * @Author: hywang
 * @CreateDate: 2019-04-30 15:29
 * @Version: 1.0
 */
public class Order {
	private boolean ascending; //升序还是降序
	private String propertyName; //哪个字段升序，哪个字段降序
	
	public String toString() {
		return propertyName + ' ' + (ascending ? "asc" : "desc");
	}

	/**
	 * Constructor for Order.
	 */
	protected Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	/**
	 * Ascending order
	 *
	 * @param propertyName
	 * @return Order
	 */
	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	/**
	 * Descending order
	 *
	 * @param propertyName
	 * @return Order
	 */
	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}

}
