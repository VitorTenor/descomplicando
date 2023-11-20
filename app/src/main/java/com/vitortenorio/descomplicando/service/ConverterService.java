package com.vitortenorio.descomplicando.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.entity.AnswerDetail;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.factory.QuestionFactory;
import com.vitortenorio.descomplicando.model.request.Answer;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.core.util.Base64Util;
import com.vitortenorio.descomplicando.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.util.ObjectMapperUtil;
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
    private final ObjectMapperUtil objectMapperUtil;
    private final Logger LOGGER = Logger.getLogger(ConverterService.class.getName());

    public List<Integer> filterTrue(Answer assertt) {
        LOGGER.info("Filtering true assertions");
        Answer removeIncorrect = removeIncorrect(assertt);
        LOGGER.info("Assertions filtered");

        LOGGER.info("Converting base64 to integer and reversing it");
        return removeIncorrect.data().stream()
                .map(AnswerDetail::id)
                .map(Base64Util::convertBase64ToIntegerWithReverse)
                .toList();
    }

    public List<AnswerResponse> processQuestionByAnswer(String question, String answerJson) {
        LOGGER.info("Processing nodes with filterTrue");

        Answer answer = objectMapperUtil.readValue(answerJson, Answer.class);

        List<Integer> answerIds = filterTrue(answer);
        LOGGER.info("FilterTrue processed");

        LOGGER.info("Processing nodes by filter ids");
        return processQuestionByAnswer(question, answerIds);
    }

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

    private Answer removeIncorrect(Answer assertt) {
        LOGGER.info("Removing incorrect assertions");
        return new Answer(
                assertt.message(),
                assertt.identifier(),
                assertt.data().stream()
                        .filter(AnswerDetail::correct)
                        .toList()
        );
    }

    public List<AnswerResponse> processQuestionArray(String question, List<Integer> answerIds) {
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
