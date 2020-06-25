package com.cts.inventory.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.inventory.exception.ItemException;
import com.cts.inventory.infrastructure.db.repo.item.IItemRepository;
import com.cts.inventory.model.Item;
import com.cts.inventory.service.IItemService;
import com.cts.inventory.vo.AppConstantVO;
import com.cts.inventory.vo.ItemVO;


@Service
public class ItemServiceImpl implements IItemService{
	
	private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class);
	
	@Autowired IItemRepository iItemRepository;

	@Override
	public List<Item> getAllItems() throws ItemException {
		logger.info("ItemServiceImpl.getAllItems - started");
		List<Item> list = null;
		try{
			list = iItemRepository.findAll();
		}catch(Exception e){
			logger.log(Level.ERROR, "ItemServiceImpl.getAllItems EXCEPTION :: {0}.", e.getMessage());
			throw new ItemException(e.getMessage(), e);
		}
		logger.info("ItemServiceImpl.getAllItems - ended");
		return list;
	}

	@Override
	public String createItem(ItemVO itemVo) throws ItemException {
		logger.info("ItemServiceImpl.createItem - started");
		String status = "";
		try{
			Item obj = iItemRepository.findByNameIgnoreCase(itemVo.name);
			if(obj == null){
				iItemRepository.save(new Item(itemVo));
				status = AppConstantVO.OPERATION_SUCCESS;
			}else{
				status = AppConstantVO.DUPLICATE_ENTRY;
			}
		}catch(Exception e){
			logger.log(Level.ERROR, "ItemServiceImpl.createItem EXCEPTION :: {0}.", e.getMessage());
			throw new ItemException(e.getMessage(), e);
		}
		logger.info("ItemServiceImpl.createItem - ended");
		return status;
	}

	@Override
	public String deleteItem(Integer id) throws ItemException {
		logger.info("ItemServiceImpl.deleteItem - started");
		String status = "";
		try{
			Optional<Item> obj = iItemRepository.findById(id);
			if(obj.isPresent()){
				iItemRepository.delete(obj.get());
				status = AppConstantVO.OPERATION_SUCCESS;
			} else {
				status = AppConstantVO.ENTRY_NOT_FOUND;
			}
		}catch(Exception e){
			logger.log(Level.ERROR, "ItemServiceImpl.deleteItem EXCEPTION :: {0}.", e.getMessage());
			throw new ItemException(e.getMessage(), e);
		}
		logger.info("ItemServiceImpl.deleteItem - ended");
		return status;
	}
	//@Transactional
	@Override
	public String updateItem(ItemVO itemVo) throws ItemException {
		logger.info("ItemServiceImpl.deleteItem - started");
		String status = "";
		Optional<Item> obj = iItemRepository.findById(itemVo.id);
		if(obj.isPresent()){
			
			Item tmp = obj.get();
			tmp.setName(itemVo.name);
			tmp.setIsSold(itemVo.isSold);
			iItemRepository.save(tmp);
			status = AppConstantVO.OPERATION_SUCCESS;
			
		}else{
			status = AppConstantVO.ENTRY_NOT_FOUND;
		}
		logger.info("ItemServiceImpl.deleteItem - ended");
		return status;
	}
	
	

}
