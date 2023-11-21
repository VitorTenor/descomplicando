package com.vitortenorio.descomplicando.api.v1.request;

public record AnswerDetailRequest(
        String id,
        Boolean correct
) {
}
