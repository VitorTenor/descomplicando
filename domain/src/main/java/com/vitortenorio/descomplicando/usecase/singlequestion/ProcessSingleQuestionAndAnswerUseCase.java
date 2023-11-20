package com.vitortenorio.descomplicando.usecase.singlequestion;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.gateway.SingleQuestionGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class ProcessSingleQuestionAndAnswerUseCase {
    private final SingleQuestionGateway singleQuestionGateway;

    public ProcessSingleQuestionAndAnswerUseCase(SingleQuestionGateway singleQuestionGateway) {
        this.singleQuestionGateway = singleQuestionGateway;
    }

    public List<QuestionAnswerEntity> execute(String question, List<Integer> answerIds) {
        return singleQuestionGateway.processQuestionAndAnswer(question, answerIds);
    }
}
