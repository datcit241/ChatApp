package com.repositories;

import java.util.*;
import java.util.function.Predicate;

public class Repository<Entity> implements RepositoryInterface<Entity> {

    private List<Entity> entityList;

    public Repository() {
        this.entityList = new ArrayList<>();
    }

    public Repository(List<Entity> entityList) {
        this.entityList = entityList;
    }

    @Override
    public Iterable<Entity> get(Predicate<Entity> predicate, Comparator<Entity> orderBy) {
        List<Entity> match;

        if (predicate == null) {
            return entityList;
        }

        if (orderBy != null) {
            match = this.entityList.stream().filter(predicate).sorted(orderBy).toList();
        } else {
            match = this.entityList.stream().filter(predicate).toList();
        }

        return match;
    }

    @Override
    public Entity find(Predicate<Entity> predicate) {
        Entity entity = this.entityList.stream().filter(predicate).findFirst().orElse(null);
        return entity;
    }

    @Override
    public void insert(Entity entity) {
        this.entityList.add(entity);
    }

    @Override
    public void delete(Entity entity) {
        this.entityList.remove(entity);
    };

}
