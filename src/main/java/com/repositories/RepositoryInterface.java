package com.repositories;

import java.util.*;
import java.util.function.Predicate;

public interface RepositoryInterface<Entity> {
    Iterable<Entity> get(Predicate<Entity> predicate, Comparator<Entity> orderBy);
    Entity find(Predicate<Entity> predicate);
    void insert(Entity entity);
    void delete(Entity entity);
}
