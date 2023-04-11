package com.example.mpp.domain;

public abstract class Entity {
    private Integer id;

    public Entity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
