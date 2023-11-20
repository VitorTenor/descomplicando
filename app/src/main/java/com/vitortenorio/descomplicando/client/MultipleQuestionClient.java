package com.vitortenorio.descomplicando.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MultipleQuestionClient {
    private final ObjectMapperUtil objectMapperUtil;
    private final JsonNodeUtil jsonNodeUtil;


    public List<AnswerResponse> processMultipleQuestion(String question, List<Integer> answerIds) {
        JsonNode nodes = objectMapperUtil.readTree(question);
        JsonNode data = nodes.path("data");
        List<JsonNode> nodesList = jsonNodeUtil.createListFromSingleNode(data);
        List<AnswerResponse> answerResponseList = new ArrayList<>();
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
                        answerResponseList.add(AnswerResponse.builder()
                                .question(questionText)
                                .answer(cleanedAnswer)
                                .answerId(answerId)
                                .build());
                    }
                }
            }
        }
        return answerResponseList;
    }

}
