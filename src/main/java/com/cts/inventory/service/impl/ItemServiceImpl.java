package com.cts.inventory.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.inventory.model.Item;
import com.cts.inventory.repository.IItemRepository;
import com.cts.inventory.service.IItemService;
import com.cts.inventory.vo.AppConstantVO;

@Service
public class ItemServiceImpl implements IItemService{
	
	@Autowired IItemRepository iItemRepository;

	@Override
	public List<Item> getAllItems() throws Exception {
		List<Item> list = iItemRepository.findAll();
		return list;
	}

	@Override
	public String createItem(Item item) throws Exception {
		
		String status = AppConstantVO.OPERATION_FAILED;
		
		Item obj = iItemRepository.findByNameIgnoreCase(item.getName());
		if(obj == null){
			if(null != iItemRepository.save(item))
				status = AppConstantVO.OPERATION_SUCCESS;
		}else{
			status = AppConstantVO.DUPLICATE_ENTRY;
		}
		return status;
	}

	@Override
	public String deleteItem(Integer id) throws Exception {
		String status = AppConstantVO.OPERATION_FAILED;
		Optional<Item> obj = iItemRepository.findById(id);
		if(obj.isPresent()){
			iItemRepository.delete(obj.get());
			status = AppConstantVO.OPERATION_SUCCESS;
		} else {
			status = AppConstantVO.ENTRY_NOT_FOUND;
		}
		return status;
	}

	@Override
	public String updateItem(Item item) throws Exception {
		String status = AppConstantVO.OPERATION_FAILED;
		
		Optional<Item> obj = iItemRepository.findById(item.getId());
		if(obj.isPresent()){
			
			Item tmp = obj.get();
			tmp.setName(item.getName());
			tmp.setIsSold(item.getIsSold());
			iItemRepository.save(tmp);
			status = AppConstantVO.OPERATION_SUCCESS;
			
		} else
			status = AppConstantVO.ENTRY_NOT_FOUND;
		return status;
	}
	
	
	
	
	
	

}
