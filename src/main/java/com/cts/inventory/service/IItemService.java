/**
 *
 */
package com.cts.inventory.service;

import java.util.List;
import java.util.Optional;

import com.cts.inventory.exception.ItemException;
import com.cts.inventory.model.Item;
import com.cts.inventory.vo.ItemVO;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
public interface IItemService {
	
	public List<Item> getAllItems()throws ItemException;

	public String createItem(ItemVO item)throws ItemException;

	public String deleteItem(Integer id)throws ItemException;

	public String updateItem(ItemVO item)throws ItemException;

}
