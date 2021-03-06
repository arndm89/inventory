/**
 * 
 */
package com.cts.inventory.controller;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.inventory.exception.ItemException;
import com.cts.inventory.model.Item;
import com.cts.inventory.service.IItemService;
import com.cts.inventory.vo.AppConstantVO;
import com.cts.inventory.vo.ItemResponseVO;
import com.cts.inventory.vo.ItemVO;


/**
 * @author Arindam.Chowdhury@cognizant.com
 */
@RestController
@CrossOrigin
@RequestMapping("/item")
public class InventoryController {
	
	private static final Logger logger = LogManager.getLogger(InventoryController.class);
	
	@Autowired IItemService iItemService;
	
	//@Cacheable(value="item")
	@GetMapping("get/allItems")
	public ResponseEntity<ItemResponseVO> getAllItems(){
		logger.info("InventoryController.getAllItems - started");
		
		List<Item> list = null;
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		try {
			list = iItemService.getAllItems();
			resVo.setItemList(list);
			if(list!=null && !list.isEmpty()){
				resVo.setResponseText("Items found");
				res = ResponseEntity.status(HttpStatus.OK).body(resVo);
			}else{
				resVo.setResponseText("No items found");
				res = ResponseEntity.status(HttpStatus.NO_CONTENT).body(resVo);
			}
		} catch (ItemException e) {
			logger.log(Level.ERROR, "InventoryController.getAllItems EXCEPTION :: {0}.", e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		logger.info("InventoryController.getAllItems - ended");
		return res;
	}
	
	@GetMapping("get/byId/{id}")
	public ResponseEntity<ItemResponseVO> getItemById(@PathVariable Integer id){
		logger.info("InventoryController.getItemById - started");
		
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		try {
			Item item = iItemService.getItemById(id);
			if(item != null){
				resVo.setResponseText("Item found");
				resVo.setItem(item);
				res = ResponseEntity.status(HttpStatus.OK).body(resVo);
			}else{
				resVo.setResponseText("Not found");
				res = ResponseEntity.status(HttpStatus.NO_CONTENT).body(resVo);
			}
		} catch (ItemException e) {
			logger.log(Level.ERROR, "InventoryController.getItemById EXCEPTION :: {0}.", e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		logger.info("InventoryController.getItemById - ended");
		return res;
	}
	@PostMapping(path="/create")
	public ResponseEntity<ItemResponseVO> createItem(@RequestBody ItemVO itemVo){
		logger.info("InventoryController.createItem - started");
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> resp = null;
		String status = "";
		try {
			status = iItemService.createItem(itemVo);
			if(AppConstantVO.OPERATION_SUCCESS.equalsIgnoreCase(status)){
				resVo.setResponseText("Item has been created");
				resp = ResponseEntity.status(HttpStatus.CREATED).body(resVo);
				
			}else if (AppConstantVO.DUPLICATE_ENTRY.equalsIgnoreCase(status)){
				resVo.setResponseText("Item already exist");
				resp = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}else{
				resVo.setResponseText("Unable to create, see the log.");
				resp = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}
		} catch (ItemException e) {
			logger.log(Level.ERROR, "InventoryController.createItem EXCEPTION :: {0}.", e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		logger.info("InventoryController.createItem - ended");
		return resp;
	}
	
	@PutMapping("/update")
	public ResponseEntity<ItemResponseVO> updateItem(@RequestBody ItemVO itemVo){
		logger.info("InventoryController.updateItem - started");
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		String status = "";
		try {
			status = iItemService.updateItem(itemVo);
			if(AppConstantVO.OPERATION_SUCCESS.equalsIgnoreCase(status)){
				resVo.setResponseText("Item has been updated");
				res = ResponseEntity.status(HttpStatus.CREATED).body(resVo);
				
			}else if (AppConstantVO.ENTRY_NOT_FOUND.equalsIgnoreCase(status)){
				resVo.setResponseText("Item does not exist");
				res = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}else{
				resVo.setResponseText("Unable to update, see the log.");
				res = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}
		} catch (ItemException e) {
			logger.log(Level.ERROR, "InventoryController.updateItem EXCEPTION :: {0}.", e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		logger.info("InventoryController.updateItem - ended");
		return res;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ItemResponseVO> deleteItem(@PathVariable Integer id){
		logger.info("InventoryController.deleteItem - started");
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		String status = "";
		try {
			status = iItemService.deleteItem(id);
			
			if(AppConstantVO.OPERATION_SUCCESS.equalsIgnoreCase(status)){
				resVo.setResponseText("Item has been deleted");
				res = ResponseEntity.status(200).body(resVo);
				
			}else if (status.equalsIgnoreCase(AppConstantVO.ENTRY_NOT_FOUND)){
				resVo.setResponseText("Item does not exist");
				res = ResponseEntity.status(HttpStatus.NO_CONTENT).body(resVo);
			}else{
				resVo.setResponseText("Unable to delete, see the log.");
				res = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}
		} catch (ItemException e) {
			logger.log(Level.ERROR, "InventoryController.deleteItem EXCEPTION :: {0}.", e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		logger.info("InventoryController.deleteItem - ended");
		return res;
	}
	
	
}