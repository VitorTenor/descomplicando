package com.vitortenorio.descomplicando.api.v1.request;

import com.vitortenorio.descomplicando.entity.AnswerEntity;

import java.util.List;

public record AnswerRequest (
        String message,
        String identifier,
        List<AnswerDetailRequest> data
){
    public List<AnswerEntity> toAnswerEntityList() {
        return data.stream()
                .map(answer -> new AnswerEntity(
                        answer.id(),
                        answer.correct()
                ))
                .toList();
    }
}
