package com.vitortenorio.descomplicando.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;

public record SingleQuestionModel(
        String question,
        String answer,
        @JsonIgnore
        Integer answerId,
        @JsonIgnore
        String lesson
) {

    public static SingleQuestionModel fromQuestionAnswerEntity(QuestionAnswerEntity questionEntity, String lesson) {
        return new SingleQuestionModel(
                questionEntity.question(),
                questionEntity.answer(),
                questionEntity.answerId(),
                lesson
        );
    }
}
