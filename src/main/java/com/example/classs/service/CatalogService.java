package com.example.classs.service;


import java.util.Map;

public interface CatalogService {

    Map<String, Object> getCatalogs(Integer pageNo, Integer pageSize);

    Map<String, Object> getSubject(Integer id, Integer pageNo, Integer pageSize);
}
