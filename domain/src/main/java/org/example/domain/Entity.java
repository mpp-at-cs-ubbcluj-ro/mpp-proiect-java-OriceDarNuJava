package org.example.domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    private Integer id;

    public Entity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
