package com.vitortenorio.descomplicando.infra.data;


import java.util.List;
import java.util.Map;

public interface LocalData <T> {
    void addOrCreate(String key, List<T> data);

    Map<String, List<T>> getAll();
}
