package com.vitortenorio.descomplicando.core.factory;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
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
        String question = jsonNodeUtil.getQuestion(questionByQuestionId).asText();
        String answer = jsonNodeUtil.getAnswer(assertion).asText();

        String cleanQuestion = Jsoup.parse(question).text();

        QuestionAnswerEntity answerResponse = new QuestionAnswerEntity(cleanQuestion, answer, answerId);

        LOGGER.info("Adding answer to filter list");
        filterList.add(answerResponse);

        LOGGER.info("Answer built");
    }
}
