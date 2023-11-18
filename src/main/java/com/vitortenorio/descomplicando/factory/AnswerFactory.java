package com.vitortenorio.descomplicando.factory;

import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.util.JsonNodeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class AnswerFactory {
    private final JsonNodeUtil jsonNodeUtil;
    private final Logger LOGGER = Logger.getLogger(AnswerFactory.class.getName());

    public void buildAnswer(JsonNode questionByQuestionId, JsonNode assertion,
                             Integer answerId, List<AnswerResponse> filterList) {

        LOGGER.info("Building answer");
        JsonNode question = jsonNodeUtil.getQuestion(questionByQuestionId);
        JsonNode answer = jsonNodeUtil.getAnswer(assertion);
        AnswerResponse answerResponse = AnswerResponse.fromJsonNode(question, answer, answerId);

        LOGGER.info("Adding answer to filter list");
        filterList.add(answerResponse);

        LOGGER.info("Answer built");
    }
}
