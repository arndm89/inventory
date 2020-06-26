package com.cts.inventory.repository;


import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.cts.inventory.exception.ItemException;
import com.cts.inventory.infrastructure.db.repo.item.IItemRepository;
import com.cts.inventory.model.Item;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryApplicationRepositoryTests {
	
    @MockBean IItemRepository iItemRepoMock;
   
    @Before
    public void setUp() {
    	Item item = new Item(1, "Iron", false);
        try {
        	Mockito.when(iItemRepoMock.findByNameIgnoreCase("Iron")).thenReturn(item);
		} catch (ItemException e) {
			e.printStackTrace();
		}
    }
    @Test
    public void createItemTest() throws ItemException {
    	Item result = iItemRepoMock.findByNameIgnoreCase("Iron");
    	assertNotNull(result);
    }
}