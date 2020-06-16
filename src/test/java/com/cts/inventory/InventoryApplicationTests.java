package com.cts.inventory;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cts.inventory.model.Item;
import com.cts.inventory.repository.IItemRepository;
import com.cts.inventory.service.IItemService;
import com.cts.inventory.vo.AppConstantVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryApplicationTests {
	
    @Autowired IItemRepository iItemRepository;
    
    @Autowired IItemService iItemService;
    
    @Mock IItemRepository iItemRepo;
    
    
    Item item = new Item();
	
    
    
    @Before
    public void setUp() throws SQLException {
        //when(iItemRepo.save(entity)).thenReturn(mockConn);
    	item.setName("Test-item");
        //Mockito.when(iItemRepo.save(Mockito.any(Item.class)).thenReturn(o));
    }
	
    @Test
    public void getAllItemsTest() throws Exception {
    	List<Item> list = iItemService.getAllItems();
    	assertNotNull(list);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void createItemTest() throws Exception {
    	
    	Item temp = new Item();
    	temp.setId(99);
    	temp.setName("Test-item-new");
    	String result = iItemService.createItem(temp);
    	
    	assertEquals(AppConstantVO.OPERATION_SUCCESS, result);
    }
    @Test
    @Transactional
    @Rollback(true)
    public void updateItemItem() throws Exception{
    	
    	Item temp = new Item();
    	temp.setId(1);
    	temp.setName("Gold-updated");    	
    	String result = iItemService.updateItem(temp);
    	
    	assertEquals(AppConstantVO.OPERATION_SUCCESS, result);
    }
    
    @Test
    @Transactional
    @Rollback(true)
    public void deleteItemTest() throws Exception {
    	
    	String result = iItemService.deleteItem(1); // not available
    	assertEquals(AppConstantVO.OPERATION_SUCCESS, result);
    }

}

