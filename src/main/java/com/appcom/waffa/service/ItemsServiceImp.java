package com.appcom.waffa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcom.waffa.constant.Status;
import com.appcom.waffa.entity.Category;
import com.appcom.waffa.entity.Items;
import com.appcom.waffa.exceptions.InternalServerErrorException;
import com.appcom.waffa.model.ItemModel;
import com.appcom.waffa.respository.CategoryRepository;
import com.appcom.waffa.respository.ItemsRepository;

@Service
public class ItemsServiceImp implements ItemsService {

	@Autowired
	private ItemsRepository itemRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<ItemModel> getAllRelatedItems(int categoryId) {
		Category cat = categoryRepository.findOneCategoryById(categoryId);

		List<Items> list = itemRepository.findAllItemsByCategoryAndIsActive(cat,Status.Y);
		List<ItemModel> mdlList = new ArrayList<ItemModel>();
		try {
			for (Items item : list) {
				ItemModel itemMdl = new ItemModel();
				itemMdl.setItemId(item.getId());
				itemMdl.setItemDescriptionAr(item.getItemDescriptionAr());
				itemMdl.setItemDescriptionEn(item.getItemDescriptionEn());
				itemMdl.setItemImageUrl(item.getItemImageUrl());
				itemMdl.setItemTitleAr(item.getItemTitleAr());
				itemMdl.setItemTitleEn(item.getItemTitleEn());
				itemMdl.setPrice(item.getPrice());
				
				mdlList.add(itemMdl);
			}

		} catch (Exception e) {
			throw new InternalServerErrorException("some went wrong while fetching the items list");
		}
		return mdlList;
	}
	
	@Override
	public void createItem(ItemModel itemMdl) {
		Category category = categoryRepository.findOneCategoryById(itemMdl.getCategoryId());
		try {
		/*	if(!CoreValidations.validArabic(itemMdl.getItemDescriptionAr()) || !CoreValidations.validArabic(itemMdl.getItemTitleAr()))
			{
				throw new BadRequestException("please enter valid arabic letters");
			}*/
			Items items = new Items();
			items.setItemDescriptionAr(itemMdl.getItemDescriptionAr());
			items.setItemDescriptionEn(itemMdl.getItemDescriptionEn());
			items.setId(itemMdl.getItemId());
			items.setItemTitleAr(itemMdl.getItemTitleAr());
			items.setItemTitleEn(itemMdl.getItemTitleEn());
			items.setItemImageUrl(itemMdl.getItemImageUrl());
			items.setCategory(category);
			items.setPrice(itemMdl.getPrice());
			items.setIsActive(Status.Y);
			itemRepository.save(items);

		} catch (Exception e) {
			throw new InternalServerErrorException(
					"some thing went wrong when trying to save the object" + e.toString());

		}
	}
	
	@Override
	public String deleteItem(ItemModel itemMdl) {
		try {
			Items item = itemRepository.findOneById(itemMdl.getItemId());
			itemRepository.delete(item);
			return "Deleted";
		} catch (Exception e) {
			throw new InternalServerErrorException("failed to delete item");

		}
	}
	
	@Override
	public List<ItemModel> getItems(){
		
		List<Items> list = itemRepository.findAll();
		List<ItemModel> mdlList = new ArrayList<ItemModel>();
		try {
			for(Items item :list) {
				ItemModel itemMdl=new ItemModel();
				itemMdl.setItemId(item.getId());
				itemMdl.setItemDescriptionAr(item.getItemDescriptionAr());
				itemMdl.setItemDescriptionEn(item.getItemDescriptionEn());
				itemMdl.setItemImageUrl(item.getItemImageUrl());
				itemMdl.setItemTitleAr(item.getItemTitleAr());
				itemMdl.setItemTitleEn(item.getItemTitleEn());
				itemMdl.setPrice(item.getPrice());
				mdlList.add(itemMdl);
			}
			
		}catch(Exception e)
		{
			throw new InternalServerErrorException("some went wrong while fetching the items list");
		}
		return mdlList;
	}


}
