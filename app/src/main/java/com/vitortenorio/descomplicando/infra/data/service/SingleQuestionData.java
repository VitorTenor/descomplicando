package com.vitortenorio.descomplicando.infra.data.service;

import com.vitortenorio.descomplicando.infra.LocalData;
import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SingleQuestionData implements LocalData<SingleQuestionModel> {
    private final Map<String, List<SingleQuestionModel>> data = new HashMap<>();

    @Override
    public void addOrCreate(String key, List<SingleQuestionModel> data) {
        List<SingleQuestionModel> questionModelList = this.data.computeIfAbsent(key, k -> new ArrayList<>());
        data.stream().map(questionModelList::add).toList();
    }

    @Override
    public Map<String, List<SingleQuestionModel>> getAll() {
        return this.data;
    }
}
