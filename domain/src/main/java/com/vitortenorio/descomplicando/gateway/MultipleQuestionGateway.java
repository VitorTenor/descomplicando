package com.vitortenorio.descomplicando.gateway;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;

import java.util.List;

public interface MultipleQuestionGateway {
    List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds);
}
