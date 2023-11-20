package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.core.factory.QuestionFactory;
import com.vitortenorio.descomplicando.gateway.SingleQuestionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SingleQuestionClient implements SingleQuestionGateway {
    private final JsonNodeUtil jsonNodeUtil;
    private final QuestionFactory questionFactory;
    @Override
    public List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds) {
        JsonNode nodes = jsonNodeUtil.buildMainNode(question);

        if (nodes.isArray()) {
            List<JsonNode> questionList = jsonNodeUtil.createListFromSingleNode(nodes);

            List<QuestionAnswerEntity> answerResponseList = new ArrayList<>();
            questionFactory.findAndBuildQuestion(answerIds, questionList, answerResponseList);

            return answerResponseList;
        }
        throw new BusinessException("Json is not an array");
    }
}
