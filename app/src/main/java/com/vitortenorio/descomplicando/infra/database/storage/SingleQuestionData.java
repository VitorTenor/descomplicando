package com.vitortenorio.descomplicando.infra.database.storage;

import com.vitortenorio.descomplicando.infra.database.model.SingleQuestionModel;

import java.util.*;

public interface SingleQuestionData {
    void addOrCreate(String key, List<SingleQuestionModel> data);

    Map<String, List<SingleQuestionModel>> getAll();
}
