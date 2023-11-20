package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.core.util.Base64Util;
import com.vitortenorio.descomplicando.entity.AnswerDetail;
import com.vitortenorio.descomplicando.gateway.AnswerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswerClient implements AnswerGateway {
    @Override
    public List<Integer> processTrueAnswer(List<AnswerDetail> answer) {
        List<AnswerDetail> correctAnswer = removeIncorrect(answer);

        return correctAnswer.stream()
                .map(AnswerDetail::id)
                .map(Base64Util::convertBase64ToIntegerWithReverse)
                .toList();
    }

    private List<AnswerDetail> removeIncorrect(List<AnswerDetail> answer) {
        return answer.stream()
                .filter(AnswerDetail::correct)
                .toList();
    }
}
