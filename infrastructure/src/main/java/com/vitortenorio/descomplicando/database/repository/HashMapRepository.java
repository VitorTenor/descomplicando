package com.vitortenorio.descomplicando.database.repository;

import java.util.List;
import java.util.Map;

public interface HashMapRepository <ID, T> {
    void add(ID id, T data);
    void addOrCreate(ID id, T data);

    T get(ID id);

    List<T> getAll();

    Map<ID, T> getAllMap();

    void remove(ID id);

    void update(ID id, T data);
}
