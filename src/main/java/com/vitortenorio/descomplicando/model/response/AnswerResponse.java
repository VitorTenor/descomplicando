package com.vitortenorio.descomplicando.model.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AnswerResponse {
    private String question;
    private String answer;
    private Integer answerId;

    public static AnswerResponse fromJsonNode(JsonNode question, JsonNode correct, Integer answerId) {
        return AnswerResponse.builder()
                .question(question.asText())
                .answer(correct.asText())
                .answerId(answerId)
                .build();
    }
}
