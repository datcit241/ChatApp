package com.utilities;

@FunctionalInterface
public interface Filter<Entity> {
    boolean filter(Entity entity);
}
