package com.vitortenorio.descomplicando.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record SingleQuestionModel(
        String question,
        String answer,
        @JsonIgnore
        Integer answerId,
        @JsonIgnore
        String lesson
) {
}
