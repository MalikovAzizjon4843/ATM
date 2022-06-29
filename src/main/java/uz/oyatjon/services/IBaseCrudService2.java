package uz.oyatjon.services;

import uz.oyatjon.exceptions.APIException;
import uz.oyatjon.models.BaseEntity;
import uz.oyatjon.response.ResponseEntity;

import java.util.List;



public interface IBaseCrudService2<E extends BaseEntity> {
    ResponseEntity<String> create(E e) throws APIException;

    ResponseEntity<String> delete(String name);

    ResponseEntity<String> get(String name);

    ResponseEntity<List<E>> getAll();

    ResponseEntity<String> update(String name);
}
