package com.cts.inventory.vo;

import java.util.List;

import com.cts.inventory.model.Item;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
public class ItemResponseVO {
	
	int responseCode;
	String responseMsg;
	String responseText;
	List<Item> itemList;
	Item item;
	
	
	public ItemResponseVO(){
		super();
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
}
