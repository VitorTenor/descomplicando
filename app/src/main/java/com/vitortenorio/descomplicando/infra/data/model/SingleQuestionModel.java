package com.vitortenorio.descomplicando.infra.data.model;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;

public record SingleQuestionModel (
    String question,
    String answer,
    Integer answerId,
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
