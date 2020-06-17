package com.cts.inventory.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.cts.inventory.exception.ItemException;
import com.cts.inventory.model.Item;
import com.cts.inventory.service.IItemService;
import com.cts.inventory.vo.AppConstantVO;
import com.cts.inventory.vo.ItemVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
//@WebMvcTest(InventoryController.class)
public class InventoryApplicationCtrlTests {
	
	
	//@MockBean private InventoryController itemCtrlMock;

	
    //@Autowired IItemRepository iItemRepository;
    
    //@Autowired IItemService iItemService;
    
    //@MockBean IItemRepository iItemRepoMock;
    @MockBean IItemService iItemServiceMock;
    
    
    @InjectMocks private InventoryController inventoryController;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    
    ItemVO itemVo = new ItemVO(1, "Item_updated", false);
    Item item = new Item(99, "Test-Mock-Obj", false);
	
    @Before
    public void setUp() throws SQLException {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	
    	List<Item> itemList = Collections.singletonList(item);
    	 
        try {
        	Mockito.when(iItemServiceMock.getAllItems()).thenReturn(itemList);
        	Mockito.when(iItemServiceMock.deleteItem(1)).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			//Mockito.when(iItemServiceMock.updateItem(itemVo)).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			//Mockito.when(iItemServiceMock.createItem(itemVo)).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			
			Mockito.when(iItemServiceMock.createItem(any(ItemVO.class))).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			Mockito.when(iItemServiceMock.updateItem(any(ItemVO.class))).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			
		} catch (ItemException e) {
			e.printStackTrace();
		}
    }
    
    
    @Test
    public void testGetAllItemsCtrl() throws Exception {
	    String jsonRequest = objectMapper.writeValueAsString(new Item());
	    MvcResult result = (MvcResult) mockMvc
	    		.perform(get("/item/get/allItems")
	    		.content(jsonRequest)
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect((ResultMatcher) status().is(302)).andReturn();
	    
	    String resultContent = result.getResponse().getContentAsString();
	    Assert.assertTrue(resultContent, true);
    }
    
    @Test
    public void testCreateItemCtrl() throws Exception {
	    String jsonRequest = objectMapper.writeValueAsString(new ItemVO(null, "New_Test_item", false));
	    MvcResult result = (MvcResult) mockMvc
	    		.perform(post("/item/create")
	    		.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect((ResultMatcher) status().is(201)).andReturn();
	    
	    String resultContent = result.getResponse().getContentAsString();
	    Assert.assertTrue(resultContent, true);
    }
    
    
    @Test
    public void testUpdateItemCtrl() throws Exception {
	    String jsonRequest = objectMapper.writeValueAsString(itemVo);
	    MvcResult result = (MvcResult) mockMvc
	    		.perform(put("/item/update")
	    		.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect((ResultMatcher) status().is(201)).andReturn();
	    
	    String resultContent = result.getResponse().getContentAsString();
	    Assert.assertTrue(resultContent, true);
    }
    
    @Test
    public void testDeleteItemCtrl() throws Exception {
	    //String jsonRequest = objectMapper.writeValueAsString(itemVo);
	    MvcResult result = (MvcResult) mockMvc
	    		.perform(delete("/item/delete/1")
	    		//.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect((ResultMatcher) status().is(200)).andReturn();
	    
	    String resultContent = result.getResponse().getContentAsString();
	    Assert.assertTrue(resultContent, true);
    }
    
    

}

