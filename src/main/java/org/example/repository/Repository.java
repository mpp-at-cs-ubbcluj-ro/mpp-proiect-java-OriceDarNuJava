package org.example.repository;

import org.example.domain.Entity;

import java.util.List;

public interface Repository<T extends Entity> {
    void push();

    List<T> get(Integer id);

    List<T> get(RepositoryFilter filter);

    List<T> getAll();

    void update(T oldValue, T newValue);

    int delete(Integer id);

    int delete(RepositoryFilter filter);
}
