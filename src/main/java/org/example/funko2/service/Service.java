package org.example.funko2.service;

import java.util.List;

public interface Service<T,ID,R> {
    List<T> findAll();
    T findById(ID id);
    T save(R funko);
    T delete(ID id);
    T update(R funko, ID id);
}
