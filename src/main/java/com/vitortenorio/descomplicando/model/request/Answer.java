package com.vitortenorio.descomplicando.model.request;

import java.util.List;

public record Answer(
        String message,
        String identifier,
        List<QuestionAnswer> data
) {
}
