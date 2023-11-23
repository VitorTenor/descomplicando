package com.vitortenorio.descomplicando.core.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswerFactory {
    private final JsonNodeUtil jsonNodeUtil;

    public void buildAnswer(JsonNode questionByQuestionId, JsonNode assertion,
                             Integer answerId, List<QuestionAnswerEntity> filterList) {

        String question = jsonNodeUtil.getQuestion(questionByQuestionId).asText();
        String answer = jsonNodeUtil.getAnswer(assertion).asText();

        String cleanQuestion = Jsoup.parse(question).text();

        QuestionAnswerEntity answerResponse = new QuestionAnswerEntity(cleanQuestion, answer, answerId);
        filterList.add(answerResponse);
    }
}
