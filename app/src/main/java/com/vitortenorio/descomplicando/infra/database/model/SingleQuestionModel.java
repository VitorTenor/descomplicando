package com.vitortenorio.descomplicando.infra.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;

public record SingleQuestionModel (
    String question,
    String answer,
    @JsonIgnore
    Integer answerId,
    @JsonIgnore
    String lesson
){
    public static SingleQuestionModel fromQuestionAnswerEntity(QuestionAnswerEntity questionAnswerEntity, String lesson) {
        return new SingleQuestionModel(
                questionAnswerEntity.question(),
                questionAnswerEntity.answer(),
                questionAnswerEntity.answerId(),
                lesson
        );
    }
}
