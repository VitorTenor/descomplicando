package com.vitortenorio.descomplicando.api.v1.input;

import com.vitortenorio.descomplicando.entity.AnswerEntity;

import java.util.List;

public record AnswerInput(
        String message,
        String identifier,
        List<AnswerDetailInput> data
){
    public List<AnswerEntity> toAnswerEntityList() {
        return data.stream()
                .map(answer -> new AnswerEntity(answer.id(), answer.correct()))
                .toList();
    }

    private record AnswerDetailInput(String id, Boolean correct) {}
}

