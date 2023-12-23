package com.vitortenorio.descomplicando.api.v1.repository;

import com.vitortenorio.descomplicando.database.model.SingleQuestionModel;
import com.vitortenorio.descomplicando.database.repository.SingleQuestionRepository;
import com.vitortenorio.descomplicando.database.service.HashMapDataService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SingleQuestionRepositoryImpl extends HashMapDataService<String, List<SingleQuestionModel>>
        implements SingleQuestionRepository {
}
