/**
 * 
 */
package com.cts.inventory.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	//private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

	
	@Autowired IItemService iItemService;
	
	//@Cacheable(value="item")
	//@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("get/allItems")
	public ResponseEntity<ItemResponseVO> getAllItems(){
		
		//logger.debug("InventoryController.getAllItems - started");
		
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
			//e.printStackTrace();
			//logger.debug("InventoryController.getAllItems EXCEPTION :: "+e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		//logger.debug("InventoryController.getAllItems - ended");
		return res;
	}
	@PostMapping(path="/create")
	public ResponseEntity<ItemResponseVO> createItem(@RequestBody ItemVO itemVo){
		//logger.debug("InventoryController.createItem - started");
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
			//logger.debug("InventoryController.createItem EXCEPTION :: "+e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		//logger.debug("InventoryController.createItem - ended");
		return resp;
	}
	
	@PutMapping("/update")
	public ResponseEntity<ItemResponseVO> updateItem(@RequestBody ItemVO itemVo){
		//logger.debug("InventoryController.updateItem - started");
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
			//e.printStackTrace();
			//logger.debug("InventoryController.updateItem EXCEPTION :: "+e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		//logger.debug("InventoryController.updateItem - ended");
		return res;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ItemResponseVO> deleteItem(@PathVariable Integer id){
		//logger.debug("InventoryController.deleteItem - started");
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
			//e.printStackTrace();
			//logger.debug("InventoryController.deleteItem :: "+e.getMessage());
			resVo.setResponseText(AppConstantVO.EXCEPTION_OCURED);
			res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resVo);
		}
		//logger.debug("InventoryController.deleteItem - started");
		return res;
	}
	
	
}