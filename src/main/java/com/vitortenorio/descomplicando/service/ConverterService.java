package com.vitortenorio.descomplicando.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.factory.QuestionFactory;
import com.vitortenorio.descomplicando.model.request.Assert;
import com.vitortenorio.descomplicando.model.request.QuestionAssert;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.util.Base64Util;
import com.vitortenorio.descomplicando.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final JsonNodeUtil jsonNodeUtil;
    private final QuestionFactory questionFactory;
    private final ObjectMapperUtil objectMapperUtil;
    private final Logger LOGGER = Logger.getLogger(ConverterService.class.getName());

    public List<Integer> filterTrue(Assert assertt) {
        LOGGER.info("Filtering true assertions");
        Assert removeIncorrect = removeIncorrect(assertt);
        LOGGER.info("Assertions filtered");

        LOGGER.info("Converting base64 to integer and reversing it");
        return removeIncorrect.data().stream()
                .map(QuestionAssert::id)
                .map(Base64Util::convertBase64ToIntegerWithReverse)
                .toList();
    }

    public List<AnswerResponse> processNodes(String json, Integer startIndex, String jsonAssert) {
        LOGGER.info("Processing nodes with filterTrue");
        Assert assertObject = objectMapperUtil.readValue(jsonAssert, Assert.class);
        List<Integer> filterTrue = filterTrue(assertObject);
        LOGGER.info("FilterTrue processed");

        LOGGER.info("Processing nodes by filter ids");
        return processNodes(json, startIndex, filterTrue);
    }

    public List<AnswerResponse> processNodes(String json, Integer startIndex, List<Integer> answerIds) {
        LOGGER.info("Processing nodes");
        JsonNode nodes = jsonNodeUtil.buildMainNode(json);

        if (nodes.isArray()) {
            LOGGER.info("Json is an array");
            List<JsonNode> nodesList = jsonNodeUtil.createListFromSingleNode(nodes);
            List<JsonNode> filterList = nodesList.subList(startIndex, Math.min(startIndex + 10, nodesList.size()));

            LOGGER.info("Finding and building questions");
            List<AnswerResponse> answerResponseList = new ArrayList<>();
            questionFactory.findAndBuildQuestion(answerIds, filterList, answerResponseList);

            LOGGER.info("Questions found and built");
            return answerResponseList;
        }
        LOGGER.info("Json is not an array");
        throw new BusinessException("Json is not an array");
    }

    private Assert removeIncorrect(Assert assertt) {
        LOGGER.info("Removing incorrect assertions");
        return new Assert(
                assertt.message(),
                assertt.identifier(),
                assertt.data().stream()
                        .filter(QuestionAssert::correct)
                        .toList()
        );
    }
}
