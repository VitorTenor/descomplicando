package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.gateway.SingleQuestionGateway;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SingleQuestionClient implements SingleQuestionGateway {
    private final JsonNodeUtil jsonNodeUtil;
    @Override
    public List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds) {
        JsonNode nodes = jsonNodeUtil.buildMainNode(question);

        if (nodes.isArray()) {
            List<JsonNode> questionList = jsonNodeUtil.createListFromSingleNode(nodes);

            List<QuestionAnswerEntity> answerResponseList = new ArrayList<>();
            findAndBuildQuestion(answerIds, questionList, answerResponseList);

            return answerResponseList;
        }
        throw new BusinessException("Json is not an array");
    }

    private void findAndBuildQuestion(List<Integer> answerIds, List<JsonNode> filterList,
                                     List<QuestionAnswerEntity> answerResponseList) {
        for (JsonNode node : filterList) {
            JsonNode questionByQuestionId = node.path("questionByQuestionId");
            JsonNode assertionList = questionByQuestionId.path("assertionsByQuestionId").path("nodes");
            buildAssertion(answerIds, assertionList, questionByQuestionId, answerResponseList);
        }
    }

    private void buildAssertion(List<Integer> answerIds, JsonNode assertionList,
                               JsonNode questionByQuestionId, List<QuestionAnswerEntity> answerResponseList) {

        for (JsonNode assertion : assertionList) {
            for (Integer answerId : answerIds) {
                if (assertion.toString().contains(answerId.toString())) {
                    buildAnswer(questionByQuestionId, assertion, answerId, answerResponseList);
                }
            }
        }
    }

    private void buildAnswer(JsonNode questionByQuestionId, JsonNode assertion,
                            Integer answerId, List<QuestionAnswerEntity> filterList) {

        String question = jsonNodeUtil.getQuestion(questionByQuestionId).asText();
        String answer = jsonNodeUtil.getSingleAnswer(assertion).asText();

        String cleanQuestion = Jsoup.parse(question).text();

        QuestionAnswerEntity answerResponse = new QuestionAnswerEntity(cleanQuestion, answer, answerId);
        filterList.add(answerResponse);
    }
}
