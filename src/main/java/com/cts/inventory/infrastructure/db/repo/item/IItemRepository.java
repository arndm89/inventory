/**
 *
 */
package com.cts.inventory.infrastructure.db.repo.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.inventory.exception.ItemException;
import com.cts.inventory.model.Item;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
@Repository
public interface IItemRepository extends JpaRepository<Item, Integer>{
	
	public Item findByNameIgnoreCase(String name) throws ItemException;

}
