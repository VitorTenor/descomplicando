package com.vitortenorio.descomplicando.model.request;

import java.util.List;

public record Assert(
        String message,
        String identifier,
        List<QuestionAssert> data
) {
}
