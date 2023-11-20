package com.vitortenorio.descomplicando.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.entity.AnswerDetail;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.factory.QuestionFactory;
import com.vitortenorio.descomplicando.model.request.Answer;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.core.util.Base64Util;
import com.vitortenorio.descomplicando.core.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.core.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final JsonNodeUtil jsonNodeUtil;
    private final QuestionFactory questionFactory;
    private final Logger LOGGER = Logger.getLogger(ConverterService.class.getName());

    public List<AnswerResponse> processQuestionByAnswer(String question, List<Integer> answerIds) {
        LOGGER.info("Processing nodes");
        JsonNode nodes = jsonNodeUtil.buildMainNode(question);

        if (nodes.isArray()) {
            LOGGER.info("Json is an array");
            List<JsonNode> questionList = jsonNodeUtil.createListFromSingleNode(nodes);

            LOGGER.info("Finding and building questions");
            List<AnswerResponse> answerResponseList = new ArrayList<>();
            questionFactory.findAndBuildQuestion(answerIds, questionList, answerResponseList);

            LOGGER.info("Questions found and built");
            return answerResponseList;
        }
        LOGGER.info("Json is not an array");
        throw new BusinessException("Json is not an array");
    }
}
