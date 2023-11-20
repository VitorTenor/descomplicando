package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class AssertionFactory {
    private final AnswerFactory answerFactory;
    private final Logger LOGGER = Logger.getLogger(AssertionFactory.class.getName());
    public void buildAssertion(List<Integer> answerIds, JsonNode assertionList,
                                JsonNode questionByQuestionId, List<QuestionAnswerEntity> answerResponseList) {

        LOGGER.info("Building assertion");
        for (JsonNode assertion : assertionList) {
            for (Integer answerId : answerIds) {
                if (assertion.toString().contains(answerId.toString())) {

                    LOGGER.info("Assertion built");
                    LOGGER.info("Building correct answer, answer => " + answerId);

                    answerFactory.buildAnswer(questionByQuestionId, assertion, answerId, answerResponseList);
                }
            }
        }

        LOGGER.info("Assertion built");
    }
}
