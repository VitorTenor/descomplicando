package com.vitortenorio.descomplicando.model.request;

import com.vitortenorio.descomplicando.entity.AnswerDetail;

import java.util.List;

public record Answer(
        String message,
        String identifier,
        List<AnswerDetail> data
) {
}