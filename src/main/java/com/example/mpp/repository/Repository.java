package com.example.mpp.repository;

import com.example.mpp.domain.Entity;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T extends Entity> {
    void push(T item) throws SQLException;

    List<T> get(Integer id);

    List<T> get(RepositoryFilter<T> filter) throws SQLException;

    List<T> getAll();

    void update(T oldValue, T newValue);

    int delete(Integer id);

    int delete(RepositoryFilter<T> filter);
}
