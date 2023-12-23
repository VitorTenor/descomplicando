package com.vitortenorio.descomplicando.database.service;

import com.vitortenorio.descomplicando.database.repository.HashMapRepository;

import java.util.*;

public abstract class HashMapDataService<ID, T> implements HashMapRepository<ID, T> {
    private final Map<ID, T> dataMap = new HashMap<>();

    @Override
    public void add(ID id, T data) {
        dataMap.put(id, data);
    }

    @Override
    public void addOrCreate(ID id, T data) {
        dataMap.putIfAbsent(id, data);
    }

    @Override
    public T get(ID id) {
        return dataMap.get(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(dataMap.values());
    }

    @Override
    public Map<ID, T> getAllMap() {
        return dataMap;
    }

    @Override
    public void remove(ID id) {
        dataMap.remove(id);
    }

    @Override
    public void update(ID id, T data) {
        dataMap.put(id, data);
    }
}
