package com.vitortenorio.descomplicando.api.v1.input;


import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.api.v1.request.AnswerRequest;

public record SingleFileInput(
        String lessonName,
        String disciplinaName,
        JsonNode questions,
        AnswerRequest assertions
) {
}
