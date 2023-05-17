package org.example.repository;

import org.example.domain.Entity;

public interface RepositoryFilter<T extends Entity> {
    public String getSql();
}
