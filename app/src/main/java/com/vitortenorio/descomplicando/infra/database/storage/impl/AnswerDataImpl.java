package com.vitortenorio.descomplicando.infra.database.storage.impl;


import com.vitortenorio.descomplicando.infra.database.model.AnswerModel;
import com.vitortenorio.descomplicando.infra.database.storage.AnswerData;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AnswerDataImpl implements AnswerData {
    private final Map<String, List<AnswerModel>> data = new HashMap<>();

    @Override
    public void addOrCreate(String key, List<AnswerModel> data) {
        List<AnswerModel> questionModelList = this.data.computeIfAbsent(key, k -> new ArrayList<>());
        data.stream().map(questionModelList::add).toList();
    }

    @Override
    public Map<String, List<AnswerModel>> getAll() {
        return data;
    }

    @Override
    public List<AnswerModel> get(String key) {
        return data.get(key);
    }
}
