package com.vitortenorio.descomplicando.service;

import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.factory.QuestionFactory;
import com.vitortenorio.descomplicando.model.request.Assert;
import com.vitortenorio.descomplicando.model.request.QuestionAssert;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.util.Base64Util;
import com.vitortenorio.descomplicando.util.JsonNodeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final ObjectMapper objectMapper;
    private final JsonNodeUtil jsonNodeUtil;
    private final QuestionFactory questionFactory;

    public List<Integer> filterTrue(Assert assertt) {
        Assert removeIncorrect = removeIncorrect(assertt);

        return removeIncorrect.data().stream()
                .map(QuestionAssert::id)
                .map(Base64Util::convertBase64ToIntegerWithReverse)
                .toList();
    }

    public List<AnswerResponse> processNodes(String json, Integer startIndex, String jsonAssert) {
        try {
            Assert assertObject = objectMapper.readValue(jsonAssert, Assert.class);
            List<Integer> filterTrue = filterTrue(assertObject);
            return processNodes(json, startIndex, filterTrue);
        } catch (JsonProcessingException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public List<AnswerResponse> processNodes(String json, Integer startIndex, List<Integer> answerIds) {
        JsonNode nodes = jsonNodeUtil.buildMainNode(json);

        if (nodes.isArray()) {
            List<JsonNode> nodesList = jsonNodeUtil.createListFromSingleNode(nodes);
            List<JsonNode> filterList = nodesList.subList(startIndex, Math.min(startIndex + 10, nodesList.size()));

            List<AnswerResponse> answerResponseList = new ArrayList<>();
            questionFactory.findAndBuildQuestion(answerIds, filterList, answerResponseList);

            return answerResponseList;
        }

        throw new BusinessException("Json is not an array");
    }

    private Assert removeIncorrect(Assert assertt) {
        return new Assert(
                assertt.message(),
                assertt.identifier(),
                assertt.data().stream()
                        .filter(QuestionAssert::correct)
                        .toList()
        );
    }
}
