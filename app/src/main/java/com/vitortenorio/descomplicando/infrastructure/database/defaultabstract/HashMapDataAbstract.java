package com.vitortenorio.descomplicando.infrastructure.database.defaultabstract;

import com.vitortenorio.descomplicando.infrastructure.database.repository.HashMapRepository;

import java.util.*;

public abstract class HashMapDataAbstract<ID, T> implements HashMapRepository<ID, T> {
    protected final Map<ID, T> dataMap = new HashMap<>();

    @Override
    public void add(ID id, T data) {
        dataMap.put(id, data);
    }

    @Override
    public void addOrCreate(ID id, T data) {
        dataMap.putIfAbsent(id, data);
    }

    @Override
    public Map<ID, T> getAllMap() {
        return dataMap;
    }
}
