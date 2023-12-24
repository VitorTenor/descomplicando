package com.vitortenorio.descomplicando.infrastructure.database.repository;

import java.util.Map;

public interface HashMapRepository <ID, T> {
    void add(ID id, T data);
    void addOrCreate(ID id, T data);
    Map<ID, T> getAllMap();
}
