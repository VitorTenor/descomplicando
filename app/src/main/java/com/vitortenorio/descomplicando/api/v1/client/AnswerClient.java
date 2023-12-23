package com.vitortenorio.descomplicando.api.v1.client;

import com.vitortenorio.descomplicando.core.util.Base64Util;
import com.vitortenorio.descomplicando.entity.AnswerEntity;
import com.vitortenorio.descomplicando.gateway.AnswerGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswerClient  {
//    @Override
    public List<Integer> processTrueAnswer(List<AnswerEntity> answer) {
        var correctAnswer = this.removeIncorrect(answer);

        return correctAnswer.stream()
                .map(AnswerEntity::id)
                .map(Base64Util::convertBase64ToIntegerWithReverse)
                .toList();
    }

    private List<AnswerEntity> removeIncorrect(List<AnswerEntity> answer) {
        return answer.stream()
                .filter(AnswerEntity::correct)
                .toList();
    }
}
