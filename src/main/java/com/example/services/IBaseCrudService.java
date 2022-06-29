package com.example.services;

import com.example.models.BaseEntity;
import com.example.response.ResponseEntity;

import java.util.List;

public interface IBaseCrudService<E extends BaseEntity> {
    ResponseEntity<String> create(E e);

    ResponseEntity<String> delete(E e);

    E get(String id);

    List<E> getAll();

    ResponseEntity<String> update(String id, E e);
}
