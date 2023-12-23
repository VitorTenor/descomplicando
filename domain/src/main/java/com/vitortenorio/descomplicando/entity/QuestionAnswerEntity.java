package com.vitortenorio.descomplicando.entity;

public class QuestionAnswerEntity
//        (
//        String question,
//        String answer,
//        Integer answerId
//)
{
    private String question;
    private String answer;
    private Integer answerId;

    public QuestionAnswerEntity() {
    }

    public QuestionAnswerEntity(String question, String answer, Integer answerId) {
        this.question = question;
        this.answer = answer;
        this.answerId = answerId;
    }

    public String question() {
        return question;
    }

    public String answer() {
        return answer;
    }

    public Integer answerId() {
        return answerId;
    }
}
