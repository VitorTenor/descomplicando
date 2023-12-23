package com.vitortenorio.descomplicando.entity;

public record QuestionAnswerEntity(
        String question,
        String answer,
        Integer answerId
) {
}
