package com.repositories;

import com.utilities.Filter;

import java.util.*;

public interface Repository<Entity> {
    List<Entity> get(Filter<Entity> filter, Comparator<Entity> orderBy);
    Entity find(Filter<Entity> filter);
    void insert(Entity entity);
    void delete(Entity entity);
}
