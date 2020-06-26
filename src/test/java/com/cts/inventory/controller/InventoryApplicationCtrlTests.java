package com.cts.inventory.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

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
public class InventoryApplicationCtrlTests {
	
	
    @MockBean IItemService iItemServiceMock;
    
    @InjectMocks private InventoryController inventoryController;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    
    ItemVO itemVo = new ItemVO(1, "Item_updated", false);
    Item item = new Item(99, "Test-Mock-Obj", false);
    
    
	
    @Before
    public void setUp() {
    	
    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    	
    	List<Item> itemList = Collections.singletonList(item);
    	 
        try {
        	Mockito.when(iItemServiceMock.getAllItems()).thenReturn(itemList);
        	
        	Mockito.when(iItemServiceMock.getItemById(99)).thenReturn(item);
        	Mockito.when(iItemServiceMock.getItemById(100)).thenReturn(null);
        	Mockito.when(iItemServiceMock.getItemById(111)).thenThrow(new ItemException("", new Throwable()));
        	
        	Mockito.when(iItemServiceMock.deleteItem(1)).thenReturn(AppConstantVO.OPERATION_SUCCESS);
        	Mockito.when(iItemServiceMock.deleteItem(100)).thenReturn(AppConstantVO.ENTRY_NOT_FOUND);
        	Mockito.when(iItemServiceMock.deleteItem(101)).thenReturn("");
        	Mockito.when(iItemServiceMock.deleteItem(111)).thenThrow(new ItemException("", new Throwable()));
        	
			Mockito.when(iItemServiceMock.createItem(any(ItemVO.class))).thenReturn(AppConstantVO.OPERATION_SUCCESS);
			
			Mockito.when(iItemServiceMock.updateItem(any(ItemVO.class))).thenReturn(AppConstantVO.OPERATION_SUCCESS);
		} catch (ItemException e) {
			e.printStackTrace();
		}
    }
    
	@Test
    public void testGetAllItemsCtrl() throws Exception {
		
		String url = "/item/get/allItems";
		
	    MvcResult result = mockMvc
	    		.perform(get(url)
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect(status().is(200)).andReturn();
	    
	    int statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 200);
	    
	    
	    Mockito.when(iItemServiceMock.getAllItems()).thenReturn(null);
	    result = mockMvc
	    		.perform(get(url)
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect(status().is(204)).andReturn();
	    
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 204);
	    
	    Mockito.when(iItemServiceMock.getAllItems()).thenThrow(new ItemException("", new Throwable()));
	    result = mockMvc
	    		.perform(get(url)
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect(status().is(500)).andReturn();
	    
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 500);
    }
	
	@Test
    public void testGetItemByIdCtrl() throws Exception {
	    MvcResult result = mockMvc
	    		.perform(get("/item/get/byId/99")
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect(status().is(200)).andReturn();
	    
	    int statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 200);
	    
	    
	    result = mockMvc
	    		.perform(get("/item/get/byId/100")
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect(status().is(204)).andReturn();
	    
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 204);
	    
	    
	    result = mockMvc
	    		.perform(get("/item/get/byId/111")
	    		.content(MediaType.APPLICATION_JSON_VALUE))
	    		.andExpect(status().is(500)).andReturn();
	    
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 500);
    }
    
    @Test
    public void testCreateItemCtrl() throws Exception {
    	
    	String url = "/item/create";
    	
	    String jsonRequest = objectMapper.writeValueAsString(new ItemVO(null, "New_Test_item", false));
	    MvcResult result = mockMvc
	    		.perform(post(url)
	    		.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(201)).andReturn();
	    int statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 201);
	    
	    Mockito.when(iItemServiceMock.createItem(any(ItemVO.class))).thenReturn(AppConstantVO.DUPLICATE_ENTRY);
	    result = mockMvc
	    		.perform(post(url)
	    		.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(417)).andReturn();
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 417);
	    
	    
	    Mockito.when(iItemServiceMock.createItem(any(ItemVO.class))).thenReturn("");
	    result = mockMvc
	    		.perform(post(url)
	    		.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(417)).andReturn();
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 417);
	    
	    Mockito.when(iItemServiceMock.createItem(any(ItemVO.class))).thenThrow(new ItemException("", new Throwable()));
	    result = mockMvc
	    		.perform(post(url)
	    		.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(500)).andReturn();
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 500);
    }
    
    
    @Test
    public void testUpdateItemCtrl() throws Exception {
    	String url = "/item/update";
	    String jsonRequest = objectMapper.writeValueAsString(itemVo);
	    MvcResult result = mockMvc
	    		.perform(put(url)
	    		.content(jsonRequest)
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(201)).andReturn();
	    
	    int statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 201);
	    
	    Mockito.when(iItemServiceMock.updateItem(any(ItemVO.class))).thenReturn(AppConstantVO.ENTRY_NOT_FOUND);
	    result = mockMvc
			.perform(put(url)
			.content(objectMapper.writeValueAsString(new ItemVO()))
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(417)).andReturn();
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 417);
	    
	    Mockito.when(iItemServiceMock.updateItem(any(ItemVO.class))).thenReturn("");
	    result = mockMvc
				.perform(put(url)
				.content(objectMapper.writeValueAsString(new ItemVO()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(417)).andReturn();
		    statusCode = result.getResponse().getStatus();
		    assertEquals(statusCode, 417);
		    
	    Mockito.when(iItemServiceMock.updateItem(any(ItemVO.class))).thenThrow(new ItemException("", new Throwable()));
	    result = mockMvc
				.perform(put(url)
				.content(objectMapper.writeValueAsString(new ItemVO()))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(500)).andReturn();
		    statusCode = result.getResponse().getStatus();
		    assertEquals(statusCode, 500);
	}
    
    @Test
    public void testDeleteItemCtrl() throws Exception {
	    MvcResult result = mockMvc
	    		.perform(delete("/item/delete/1")
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(200)).andReturn();
	    int statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 200);
	    
	    
	    result = mockMvc
	    		.perform(delete("/item/delete/100")
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(204)).andReturn();
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 204);
	    
	    
	    result = mockMvc
	    		.perform(delete("/item/delete/101")
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(417)).andReturn();
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 417);
	    
	    result = mockMvc
	    		.perform(delete("/item/delete/111")
	    		.accept(MediaType.APPLICATION_JSON)
	    		.contentType(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is(500)).andReturn();
	    statusCode = result.getResponse().getStatus();
	    assertEquals(statusCode, 500);
	    
	    
    }

}

