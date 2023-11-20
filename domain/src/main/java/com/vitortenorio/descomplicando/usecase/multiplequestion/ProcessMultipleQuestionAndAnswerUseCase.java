package com.vitortenorio.descomplicando.usecase.multiplequestion;

import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.gateway.MultipleQuestionGateway;

import javax.inject.Named;
import java.util.List;

@Named
public class ProcessMultipleQuestionAndAnswerUseCase {
    private final MultipleQuestionGateway multipleQuestionGateway;

    public ProcessMultipleQuestionAndAnswerUseCase(MultipleQuestionGateway multipleQuestionGateway) {
        this.multipleQuestionGateway = multipleQuestionGateway;
    }

    public List<QuestionAnswerEntity> execute(String question, List<Integer> answerIds) {
        return multipleQuestionGateway.processQuestionAndAnswer(question, answerIds);
    }
}
