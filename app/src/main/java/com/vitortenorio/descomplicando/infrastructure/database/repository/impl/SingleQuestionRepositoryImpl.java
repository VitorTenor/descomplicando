package com.vitortenorio.descomplicando.infrastructure.database.repository.impl;

import com.vitortenorio.descomplicando.infrastructure.database.defaultabstract.HashMapDataAbstract;
import com.vitortenorio.descomplicando.infrastructure.database.model.SingleQuestionModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public final class SingleQuestionRepositoryImpl extends HashMapDataAbstract<String, List<SingleQuestionModel>> {

    @Override
    public void addOrCreate(String id, List<SingleQuestionModel> data) {
        List<SingleQuestionModel> questionModelList = super.dataMap.computeIfAbsent(id, k -> new ArrayList<>());
        data.stream().map(questionModelList::add).toList();
    }
}
