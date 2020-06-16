/**
 *
 */
package com.cts.inventory.service;

import java.util.List;

import com.cts.inventory.model.Item;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
public interface IItemService {
	
	public List<Item> getAllItems()throws Exception;

	public String createItem(Item item)throws Exception;

	public String deleteItem(Integer id)throws Exception;

	public String updateItem(Item item)throws Exception;

}
