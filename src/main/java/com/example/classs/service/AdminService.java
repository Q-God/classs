package com.example.classs.service;

import com.example.classs.entity.Catalog;
import com.example.classs.entity.Item;

import java.util.Map;

public interface AdminService {
    Map<String, Object> getTags(Integer pageNo, Integer pageSize);

    Map<String, Object> getSubject(Integer id, Integer pageNo, Integer pageSize);

    Catalog saveOrUpdateCatalog(Catalog catalog);

    void deleteCatalog(Integer id);

    void updateCatalogState(Catalog catalog);

    Item saveOrUpdateItem(Item item);

    void deleteItem(Integer id);

    void updateItemState(Item item);
}
