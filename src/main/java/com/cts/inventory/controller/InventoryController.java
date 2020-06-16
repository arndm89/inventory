/**
 * 
 */
package com.cts.inventory.controller;

import java.util.List;

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
	
	@Autowired IItemService iItemService;
	
	@GetMapping("get/allItems")
	//@Cacheable(value = "get/allItems")
	public ResponseEntity<ItemResponseVO> getAllItems(){
		List<Item> list = null;
		
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		
		try {
			list = iItemService.getAllItems();
			resVo.setItemList(list);
			if(list!=null && !list.isEmpty()){
				resVo.setResponseText("Items found");
				res = ResponseEntity.status(HttpStatus.FOUND).body(resVo);
			}else{
				resVo.setResponseText("No items found");
				res = ResponseEntity.status(HttpStatus.NO_CONTENT).body(resVo);
			}
		} catch (ItemException e) {
			e.printStackTrace();
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		return res;
	}
	
	@PostMapping("/create")
	public ResponseEntity<ItemResponseVO> createItem(@RequestBody ItemVO itemVo){
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		String status = "";
		try {
			status = iItemService.createItem(itemVo);
			if(status.equalsIgnoreCase(AppConstantVO.OPERATION_SUCCESS)){
				resVo.setResponseText("Item has been created");
				res = ResponseEntity.status(HttpStatus.CREATED).body(resVo);
				
			}else if (status.equalsIgnoreCase(AppConstantVO.DUPLICATE_ENTRY)){
				resVo.setResponseText("Item already exist");
				res = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}
		} catch (ItemException e) {
			e.printStackTrace();
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		return res;
	}
	
	@PutMapping("/update")
	public ResponseEntity<ItemResponseVO> updateItem(@RequestBody ItemVO itemVo){
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		String status = "";
		try {
			status = iItemService.updateItem(itemVo);
			if(status.equalsIgnoreCase(AppConstantVO.OPERATION_SUCCESS)){
				resVo.setResponseText("Item has been updated");
				res = ResponseEntity.status(HttpStatus.CREATED).body(resVo);
				
			}else if (status.equalsIgnoreCase(AppConstantVO.ENTRY_NOT_FOUND)){
				resVo.setResponseText("Item does not exist");
				res = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}else{
				resVo.setResponseText("Unable to update, see the log.");
				res = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resVo);
			}
		} catch (ItemException e) {
			e.printStackTrace();
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		return res;
	}
	
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ItemResponseVO> deleteItem(@PathVariable Integer id){
		
		ItemResponseVO resVo = new ItemResponseVO();
		ResponseEntity<ItemResponseVO> res = null;
		String status = "";
		try {
			status = iItemService.deleteItem(id);
			
			if(status.equalsIgnoreCase(AppConstantVO.OPERATION_SUCCESS)){
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
			e.printStackTrace();
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		return res;
	}
	
	
}