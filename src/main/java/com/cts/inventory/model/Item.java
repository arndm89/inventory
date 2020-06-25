
package com.cts.inventory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cts.inventory.vo.ItemVO;

/**
 * @author Arindam.Chowdhury@cognizant.com
 */
@Entity
@Table(name="item")
//@RedisHash("Item")
public class Item implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 340973743814995349L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")	
	Integer id;
	@Column(name="name")
	String name;
	@Column(name="is_sold")
	Boolean isSold = false;
	
	public Item(){}
	
	/**
	 * @param id
	 * @param name
	 * @param isSold
	 */
	public Item(Integer id, String name, Boolean isSold) {
		this.id = id;
		this.name = name;
		this.isSold = isSold;
	}
	
	public Item(ItemVO itemVO) {
		this.id = itemVO.id;
		this.name = itemVO.name;
		this.isSold = itemVO.isSold;
	}

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsSold() {
		return isSold;
	}
	public void setIsSold(Boolean isSold) {
		this.isSold = isSold;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", isSold=" + isSold + "]";
	}
}
