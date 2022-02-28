package com.example.classs.repository;

import com.example.classs.entity.Protocol;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtocolRepository extends CrudRepository<Protocol, Integer> {
}
