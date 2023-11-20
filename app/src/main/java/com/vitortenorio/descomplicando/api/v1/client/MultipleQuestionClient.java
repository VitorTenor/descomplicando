package com.vitortenorio.descomplicando.api.v1.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.entity.QuestionAnswerEntity;
import com.vitortenorio.descomplicando.gateway.MultipleQuestionGateway;
import com.vitortenorio.descomplicando.model.response.AnswerResponse;
import com.vitortenorio.descomplicando.util.JsonNodeUtil;
import com.vitortenorio.descomplicando.util.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MultipleQuestionClient implements MultipleQuestionGateway {

    @Override
    public List<QuestionAnswerEntity> processQuestionAndAnswer(String question, List<Integer> answerIds) {
        return null;
    }
}
