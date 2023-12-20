package com.vitortenorio.descomplicando.infra.data.service;

import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SingleQuestionData {
    private final Map<String, List<SingleQuestionModel>> singleQuestionModelMap = new HashMap<>();

    public void addOrCreate(String key, List<SingleQuestionModel> data) {
        List<SingleQuestionModel> questionModelList = singleQuestionModelMap.computeIfAbsent(key, k -> new ArrayList<>());
        data.stream().map(questionModelList::add).toList();
    }

    public Map<String, List<SingleQuestionModel>> getAll() {
        return singleQuestionModelMap;
    }
}
