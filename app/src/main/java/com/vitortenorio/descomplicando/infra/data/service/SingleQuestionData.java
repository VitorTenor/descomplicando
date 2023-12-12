package com.vitortenorio.descomplicando.infra.data.service;

import com.vitortenorio.descomplicando.infra.data.model.SingleQuestionModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SingleQuestionData {

    private Map<String, List<SingleQuestionModel>> singleQuestionModelMap = new HashMap<>();


    public void save(String key, List<SingleQuestionModel> singleQuestionModelList) {
        singleQuestionModelMap.put(key, singleQuestionModelList);
    }


    public void addSingleQuestionModel(String lesson, List<SingleQuestionModel> singleQuestionModel) {
        List<SingleQuestionModel> questionModelList = singleQuestionModelMap.computeIfAbsent(lesson, k -> new ArrayList<>());
        singleQuestionModel.stream().map(questionModelList::add).toList();
    }

    public Map<String, List<SingleQuestionModel>> getSingleQuestionModelMap() {
        return singleQuestionModelMap;
    }
}
