package com.vitortenorio.descomplicando.infra.database.service;

import com.vitortenorio.descomplicando.infra.database.model.SingleQuestionModel;

import java.util.List;
import java.util.Map;

public interface SingleQuestionData {
    void addOrCreate(String key, List<SingleQuestionModel> data);

    Map<String, List<SingleQuestionModel>> getAll();
}
