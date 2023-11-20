package com.vitortenorio.descomplicando.api.v1.request;

import com.vitortenorio.descomplicando.entity.AnswerDetail;

import java.util.List;

public record AnswerRequest (
        String message,
        String identifier,
        List<AnswerDetail> data
){
}
