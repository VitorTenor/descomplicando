package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
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
                             Integer answerId, List<QuestionAnswerEntity> filterList) {

        LOGGER.info("Building answer");
        JsonNode question = jsonNodeUtil.getQuestion(questionByQuestionId);
        JsonNode answer = jsonNodeUtil.getAnswer(assertion);
        QuestionAnswerEntity answerResponse = new QuestionAnswerEntity(question.asText(), answer.asText(), answerId);

        LOGGER.info("Adding answer to filter list");
        filterList.add(answerResponse);

        LOGGER.info("Answer built");
    }
}
