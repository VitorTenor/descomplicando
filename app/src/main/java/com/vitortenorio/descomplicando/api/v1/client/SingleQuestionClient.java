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
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SingleQuestionClient implements SingleQuestionGateway {
    private final JsonNodeUtil jsonNodeUtil;

    @Override
    public List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds) {
        JsonNode questionNodes = jsonNodeUtil.buildMainNode(question);
        List<JsonNode> questions = getQuestionsFromNodes(questionNodes);

        List<QuestionAnswerEntity> answerResponses = new ArrayList<>();
        findAndBuildQuestions(answerIds, questions, answerResponses);

        return answerResponses;
    }

    private List<JsonNode> getQuestionsFromNodes(JsonNode nodes) {
        return Objects.requireNonNull(jsonNodeUtil.createListFromSingleNode(nodes));
    }

    private void findAndBuildQuestions(List<Integer> answerIds, List<JsonNode> questions,
                                       List<QuestionAnswerEntity> answerResponses) {
        for (JsonNode question : questions) {
            buildAssertions(answerIds, question, answerResponses);
        }
    }

    private void buildAssertions(List<Integer> answerIds, JsonNode question,
                                 List<QuestionAnswerEntity> answerResponses) {
        JsonNode questionByQuestionId = question.path("questionByQuestionId");
        JsonNode assertionList = questionByQuestionId.path("assertionsByQuestionId").path("nodes");
        validateAssertions(answerIds, assertionList, questionByQuestionId, answerResponses);
    }

    private void validateAssertions(List<Integer> answerIds, JsonNode assertionList,
                                    JsonNode questionByQuestionId, List<QuestionAnswerEntity> answerResponses) {
        for (JsonNode assertion : assertionList) {
            validateAssertion(answerIds, assertion, questionByQuestionId, answerResponses);
        }
    }

    private void validateAssertion(List<Integer> answerIds, JsonNode assertion,
                                   JsonNode questionByQuestionId, List<QuestionAnswerEntity> answerResponses) {
        for (Integer answerId : answerIds) {
            if (assertion.toString().contains(answerId.toString())) {
                buildAnswer(questionByQuestionId, assertion, answerId, answerResponses);
            }
        }
    }

    private void buildAnswer(JsonNode questionByQuestionId, JsonNode assertion,
                             Integer answerId, List<QuestionAnswerEntity> answerResponses) {
        String questionText = jsonNodeUtil.getQuestion(questionByQuestionId).asText();
        String answerText = jsonNodeUtil.getSingleAnswer(assertion).asText();

        String cleanQuestion = Jsoup.parse(questionText).text();

        QuestionAnswerEntity answerResponse = new QuestionAnswerEntity(cleanQuestion, answerText, answerId);
        answerResponses.add(answerResponse);
    }
}
