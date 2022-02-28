package com.example.classs.service.impl;

import com.example.classs.entity.Catalog;
import com.example.classs.entity.Item;
import com.example.classs.repository.CatalogRepository;
import com.example.classs.repository.ItemRepository;
import com.example.classs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Map<String, Object> getTags(Integer pageNo, Integer pageSize) {
        Map<String, Object> resultMap = Collections.EMPTY_MAP;
        //查出所有
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<Catalog> page = catalogRepository.findAll(pageRequest);
        if (Objects.nonNull(page)) {
            resultMap = new HashMap<>();
            resultMap.put("conntent", page.getContent());
            resultMap.put("totalElements", page.getTotalElements());
            resultMap.put("totalPages", page.getTotalPages());
            resultMap.put("pageNo", page.getNumber() + 1);
            resultMap.put("pageSize", page.getSize());
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getSubject(Integer id, Integer pageNo, Integer pageSize) {
        Map<String, Object> resultMap = Collections.EMPTY_MAP;
        Item tmp = new Item();
        tmp.setCId(id);
        Example<Item> itemExample = Example.of(tmp);
        //查出所有
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<Item> page = itemRepository.findAll(itemExample, pageRequest);
        if (Objects.nonNull(page)) {
            resultMap = new HashMap<>();
            resultMap.put("conntent", page.getContent());
            resultMap.put("totalElements", page.getTotalElements());
            resultMap.put("totalPages", page.getTotalPages());
            resultMap.put("pageNo", page.getNumber() + 1);
            resultMap.put("pageSize", page.getSize());
        }
        return resultMap;
    }

    @Override
    public Catalog saveOrUpdateCatalog(Catalog catalog) {
        Catalog newCatalog = catalogRepository.save(catalog);
        return newCatalog;
    }

    @Override
    public void deleteCatalog(Integer id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public void updateCatalogState(Catalog catalog) {
        Catalog oldCatalog = catalogRepository.findById(catalog.getId()).get();
        oldCatalog.setIsSelected(catalog.getIsSelected());
        oldCatalog.setUpdateTime(new Date());
        catalogRepository.save(oldCatalog);
        //catalogRepository.updateCatalogState(catalog.getId(), catalog.getIsSelected());
    }

    @Override
    public Item saveOrUpdateItem(Item item) {
        Item newItem = itemRepository.save(item);
        return newItem;
    }

    @Override
    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void updateItemState(Item item) {
        Item oldItem = itemRepository.findById(item.getId()).get();
        oldItem.setIsSelected(item.getIsSelected());
        itemRepository.save(oldItem);
        //itemRepository.updateItemState(item.getId(), item.getIsSelected());
    }


}
