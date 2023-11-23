package com.vitortenorio.descomplicando.core.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionFactory {
    private final AssertionFactory assertionFactory;
    public void findAndBuildQuestion(List<Integer> answerIds, List<JsonNode> filterList,
                                      List<QuestionAnswerEntity> answerResponseList) {
        for (JsonNode node : filterList) {
            JsonNode questionByQuestionId = node.path("questionByQuestionId");
            JsonNode assertionList = questionByQuestionId.path("assertionsByQuestionId").path("nodes");
            assertionFactory.buildAssertion(answerIds, assertionList, questionByQuestionId, answerResponseList);
        }
    }
}
