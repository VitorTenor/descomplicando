package com.vitortenorio.descomplicando.gateway;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;

import java.util.List;

public interface SingleQuestionGateway {
    List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds);
}
