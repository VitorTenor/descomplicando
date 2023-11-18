package com.vitortenorio.descomplicando.factory;

import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionFactory {
    private final AssertionFactory assertionFactory;
    public void findAndBuildQuestion(List<Integer> answerIds, List<JsonNode> filterList,
                                      List<AnswerResponse> answerResponseList) {

        for (JsonNode node : filterList) {
            JsonNode questionByQuestionId = node.path("questionByQuestionId");
            JsonNode assertionList = questionByQuestionId.path("assertionsByQuestionId").path("nodes");
            assertionFactory.buildAssertion(answerIds, assertionList, questionByQuestionId, answerResponseList);
        }
    }
}
