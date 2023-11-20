package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.gateway.MultipleQuestionGateway;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MultipleQuestionClient implements MultipleQuestionGateway {
    private final JsonNodeUtil jsonNodeUtil;
    private final ObjectMapperUtil objectMapperUtil;
    @Override
    public List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds) {
        JsonNode nodes = objectMapperUtil.readTree(question);
        JsonNode data = nodes.path("data");
        List<JsonNode> nodesList = jsonNodeUtil.createListFromSingleNode(data);
        List<QuestionAnswerEntity> answerResponseList = new ArrayList<>();
        for (JsonNode node : nodesList) {
            JsonNode questionNode = node.path("data");
            JsonNode questionById = questionNode.path("questionById");
            JsonNode assertionsByQuestionId = questionById.path("assertionsByQuestionIdList");
            for (JsonNode assertion : assertionsByQuestionId) {
                for (Integer answerId : answerIds) {
                    if (assertion.toString().contains(answerId.toString())) {
                        String correctAnswer = jsonNodeUtil.getAnswer2(assertion).asText();
                        String cleanedAnswer = Jsoup.parse(correctAnswer).text();

                        String questionText = assertion.path("id").asText();
                        answerResponseList.add(new QuestionAnswerEntity(questionText, cleanedAnswer, answerId));
                    }
                }
            }
        }
        return answerResponseList;
    }
}
