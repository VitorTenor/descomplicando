package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.gateway.SingleQuestionGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SingleQuestionClient implements SingleQuestionGateway {
    @Override
    public List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds) {
        return null;
    }
}
