package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class QuestionFactory {
    private final AssertionFactory assertionFactory;
    private final Logger LOGGER = Logger.getLogger(QuestionFactory.class.getName());
    public void findAndBuildQuestion(List<Integer> answerIds, List<JsonNode> filterList,
                                      List<QuestionAnswerEntity> answerResponseList) {
        LOGGER.info("Finding and building questions");

        for (JsonNode node : filterList) {
            JsonNode questionByQuestionId = node.path("questionByQuestionId");
            JsonNode assertionList = questionByQuestionId.path("assertionsByQuestionId").path("nodes");
            assertionFactory.buildAssertion(answerIds, assertionList, questionByQuestionId, answerResponseList);
        }

        LOGGER.info("Questions found and built");
    }
}
