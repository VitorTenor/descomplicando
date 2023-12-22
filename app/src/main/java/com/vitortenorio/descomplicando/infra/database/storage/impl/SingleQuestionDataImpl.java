package com.vitortenorio.descomplicando.infra.database.storage.impl;

import com.vitortenorio.descomplicando.infra.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.infra.database.storage.SingleQuestionData;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SingleQuestionDataImpl implements SingleQuestionData {
    private final Map<String, List<SingleQuestionModel>> data = new HashMap<>();

    public void addOrCreate(String key, List<SingleQuestionModel> data) {
        List<SingleQuestionModel> questionModelList = this.data.computeIfAbsent(key, k -> new ArrayList<>());
        data.stream().map(questionModelList::add).toList();
    }

    public Map<String, List<SingleQuestionModel>> getAll() {
        return this.data;
    }
}
