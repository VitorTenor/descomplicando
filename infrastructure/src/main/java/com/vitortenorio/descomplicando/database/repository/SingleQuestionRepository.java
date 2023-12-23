package com.vitortenorio.descomplicando.database.repository;

import com.vitortenorio.descomplicando.database.model.SingleQuestionModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleQuestionRepository extends HashMapRepository<String, List<SingleQuestionModel>> {
}
