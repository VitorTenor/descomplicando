package com.vitortenorio.descomplicando.core.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AssertionFactory {
    private final AnswerFactory answerFactory;
    public void buildAssertion(List<Integer> answerIds, JsonNode assertionList,
                                JsonNode questionByQuestionId, List<QuestionAnswerEntity> answerResponseList) {

        for (JsonNode assertion : assertionList) {
            for (Integer answerId : answerIds) {
                if (assertion.toString().contains(answerId.toString())) {
                    answerFactory.buildAnswer(questionByQuestionId, assertion, answerId, answerResponseList);
                }
            }
        }
    }
}
