package com.vitortenorio.descomplicando.infra.database.storage;

import com.vitortenorio.descomplicando.infra.database.model.AnswerModel;

import java.util.*;

public interface AnswerData {
    void addOrCreate(String key, List<AnswerModel> data);
    Map<String, List<AnswerModel>> getAll();
    List<AnswerModel> get(String key);
}
