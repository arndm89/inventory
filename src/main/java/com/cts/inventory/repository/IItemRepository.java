/**
 *
 */
package com.cts.inventory.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.inventory.model.Item;

/**
 * @author: Arindam.Chowdhury@cognizant.com / ctsjavafsd24
 *
 */
@Repository
public interface IItemRepository extends JpaRepository<Item, Integer>{
	
	@Transactional
	public List<Item> findAll();
	
	//@Query("SELECT f FROM Foo f WHERE LOWER(f.name) = LOWER(:name)")
	public Item findByNameIgnoreCase(String name);

}
