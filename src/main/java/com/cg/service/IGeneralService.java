package com.cg.service;

import java.util.List;
import java.util.Optional;

public interface IGeneralService<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    T save(T t);
}
