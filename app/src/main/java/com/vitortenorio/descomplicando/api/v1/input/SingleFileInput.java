package com.vitortenorio.descomplicando.api.v1.input;


import com.fasterxml.jackson.databind.JsonNode;

public record SingleFileInput(
        String lessonName,
        String subjectName,
        JsonNode questions,
        AnswerInput assertions
) {
}
