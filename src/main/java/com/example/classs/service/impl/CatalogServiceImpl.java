package com.example.classs.service.impl;

import com.example.classs.entity.Catalog;
import com.example.classs.entity.Item;
import com.example.classs.repository.CatalogRepository;
import com.example.classs.repository.ItemRepository;
import com.example.classs.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Map<String, Object> getCatalogs(Integer pageNo, Integer pageSize) {
        Map<String, Object> resultMap = Collections.EMPTY_MAP;
        Catalog catalog = new Catalog();
        catalog.setIsSelected((short) 1);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("isSelected", matcher -> matcher.exact());
        Example<Catalog> example = Example.of(catalog, exampleMatcher);
        //查出所有
        PageRequest pageRequest = PageRequest.of(pageNo - 1, pageSize);
        Page<Catalog> page = catalogRepository.findAll(example, pageRequest);
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
        tmp.setIsSelected((short) 1);
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


}
