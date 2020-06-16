package com.cts.inventory;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cts.inventory.controller.InventoryController;
import com.cts.inventory.exception.ItemException;
import com.cts.inventory.model.Item;
import com.cts.inventory.repository.IItemRepository;
import com.cts.inventory.service.IItemService;
import com.cts.inventory.vo.AppConstantVO;
import com.cts.inventory.vo.ItemVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest(InventoryController.class)
public class InventoryApplicationTests {
	
	
	//@MockBean private InventoryController itemCtrlMock;

	
    @Autowired IItemRepository iItemRepository;
    
    @Autowired IItemService iItemService;
    
    @MockBean IItemRepository iItemRepoMock;
    @MockBean IItemService iItemServiceMock;
    
    
    @InjectMocks private InventoryController inventoryController;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    ObjectMapper om = new ObjectMapper();
    
   // @Autowired MockMvc mvc;
    
    
    ItemVO itemVo = new ItemVO(1, "Item_updated", false);
    Item item = new Item(99, "Test-Mock-Obj", false);
	
    @Before
    public void setUp() throws SQLException {
    	
    	//mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	
    	List<Item> itemList = Collections.singletonList(item);
    	 
        try {
        	Mockito.when(iItemRepoMock.findAll()).thenReturn(itemList);
        	Mockito.when(iItemServiceMock.deleteItem(1)).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			Mockito.when(iItemServiceMock.updateItem(itemVo)).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			Mockito.when(iItemServiceMock.createItem(itemVo)).thenReturn(AppConstantVO.OPERATION_SUCCESS);
		} catch (ItemException e) {
			e.printStackTrace();
		}
    }
    
    
    @Test
    public void testGetAllInventories() throws Exception {
	    Item inv = new Item();
	    String jsonRequest = om.writeValueAsString(inv);
	    MvcResult result = (MvcResult) mockMvc
	    		.perform(get("/item/get/allItems")
	    		.content(jsonRequest)
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect((ResultMatcher) status().is(302)).andReturn();
	    
	    String resultContent = result.getResponse().getContentAsString();
	    
	    Assert.assertTrue(resultContent, true);
    }
    
    @Test
    public void getAllItemsTest() throws Exception {
    	List<Item> list = iItemService.getAllItems();
    	assertNotNull(list);
    }
    
    @Test
    /*@Transactional
    @Rollback(true)*/
    public void createItemTest() throws Exception {
    	
    	String result = iItemService.createItem(itemVo);
    	assertEquals(AppConstantVO.OPERATION_SUCCESS, result);
    }
    @Test
    /*@Transactional
    @Rollback(true)*/
    public void updateItemItem() throws Exception{
    	
    	/*ItemVO temp = new ItemVO();
    	temp.id = 1;
    	temp.name = "Gold-updated";*/    	
    	String result = iItemService.updateItem(itemVo);
    	
    	assertEquals(AppConstantVO.OPERATION_SUCCESS, result);
    }
    
    @Test
   /* @Transactional
    @Rollback(true)*/
    public void deleteItemTest() throws Exception {
    	
    	String result = iItemService.deleteItem(1); // not available
    	assertEquals(AppConstantVO.OPERATION_SUCCESS, result);
    }

}

