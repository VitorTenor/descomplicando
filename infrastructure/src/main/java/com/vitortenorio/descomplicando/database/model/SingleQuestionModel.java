package com.vitortenorio.descomplicando.database.model;

public record SingleQuestionModel(
        String question,
        String answer,
        Integer answerId,
        String lesson
) {
}
