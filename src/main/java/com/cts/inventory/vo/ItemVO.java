
package com.cts.inventory.vo;


/**
 * @author Arindam.Chowdhury@cognizant.com
 */
public class ItemVO {
	
	public Integer id;
	public String name;
	public Boolean isSold = false;
	
	public ItemVO(){}
	
	/**
	 * @param id
	 * @param name
	 * @param isSold
	 */
	public ItemVO(Integer id, String name, Boolean isSold) {
		this.id = id;
		this.name = name;
		this.isSold = isSold;
	}
}
