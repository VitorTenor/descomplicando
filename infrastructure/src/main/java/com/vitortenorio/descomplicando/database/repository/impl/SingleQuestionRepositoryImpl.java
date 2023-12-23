package com.vitortenorio.descomplicando.database.repository.impl;

import com.vitortenorio.descomplicando.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.database.defaultabstract.HashMapDataAbstract;
import com.vitortenorio.descomplicando.database.repository.HashMapRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public final class SingleQuestionRepositoryImpl extends HashMapDataAbstract<String, List<SingleQuestionModel>>
        implements HashMapRepository<String, List<SingleQuestionModel>> {
}
