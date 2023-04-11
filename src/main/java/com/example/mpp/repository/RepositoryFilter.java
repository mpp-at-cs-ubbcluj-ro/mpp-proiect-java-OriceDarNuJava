package com.example.mpp.repository;

import com.example.mpp.domain.Entity;

public interface RepositoryFilter<T extends Entity> {
    public String getSql();
}
