package com.vitortenorio.descomplicando.database.repository.impl;

import com.vitortenorio.descomplicando.database.defaultabstract.HashMapDataAbstract;
import com.vitortenorio.descomplicando.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.database.repository.HashMapRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public final class SingleQuestionRepositoryImpl extends HashMapDataAbstract<String, List<SingleQuestionModel>>
        implements HashMapRepository<String, List<SingleQuestionModel>> {

    @Override
    public void addOrCreate(String id, List<SingleQuestionModel> data) {
        List<SingleQuestionModel> questionModelList = super.dataMap.computeIfAbsent(id, k -> new ArrayList<>());
        data.stream().map(questionModelList::add).toList();
    }
}
