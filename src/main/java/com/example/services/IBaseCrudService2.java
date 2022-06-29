package com.example.services;

import com.example.models.BaseEntity;
import com.example.response.ResponseEntity;
import com.example.exceptions.APIException;

import java.util.List;



public interface IBaseCrudService2<E extends BaseEntity> {
    ResponseEntity<String> create(E e) throws APIException;

    ResponseEntity<String> delete(String name);

    ResponseEntity<String> get(String name);

    ResponseEntity<List<E>> getAll();

    ResponseEntity<String> update(String name);
}
