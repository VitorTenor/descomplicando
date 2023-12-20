package com.vitortenorio.descomplicando.infra.database.service;

import com.vitortenorio.descomplicando.infra.database.model.SingleQuestionModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class SingleQuestionDataImpl implements SingleQuestionData {
    private final Map<String, List<SingleQuestionModel>> data = new HashMap<>();

    public void addOrCreate(String key, List<SingleQuestionModel> data) {
        List<SingleQuestionModel> questionModelList = this.data.computeIfAbsent(key, k -> new ArrayList<>());
        data.stream().map(questionModelList::add).toList();
    }

    public Map<String, List<SingleQuestionModel>> getAll() {
        return this.data;
    }
}
