package com.vitortenorio.descomplicando.usecase.answer;

import com.vitortenorio.descomplicando.entity.AnswerDetail;
import com.vitortenorio.descomplicando.gateway.AnswerGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class ProcessTrueAnswerUseCase {
    private final AnswerGateway answerGateway;

    public ProcessTrueAnswerUseCase(AnswerGateway answerGateway) {
        this.answerGateway = answerGateway;
    }

    public List<Integer> execute(List<AnswerDetail> answer) {
        return answerGateway.processTrueAnswer(answer);
    }
}
