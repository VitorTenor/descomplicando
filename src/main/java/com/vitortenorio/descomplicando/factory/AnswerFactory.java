package com.vitortenorio.descomplicando.factory;

import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.util.JsonNodeUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswerFactory {
    private final JsonNodeUtil jsonNodeUtil;

    public void buildAnswer(JsonNode questionByQuestionId, JsonNode assertion,
                             Integer answerId, List<AnswerResponse> filterList) {

        JsonNode question = jsonNodeUtil.getQuestion(questionByQuestionId);
        JsonNode answer = jsonNodeUtil.getAnswer(assertion);
        AnswerResponse answerResponse = AnswerResponse.fromJsonNode(question, answer, answerId);

        filterList.add(answerResponse);
    }
}
