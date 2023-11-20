package com.vitortenorio.descomplicando.gateway;

import com.vitortenorio.descomplicando.entity.AnswerDetail;

import java.util.List;

public interface AnswerGateway {
    List<Integer> processTrueAnswer(List<AnswerDetail> answer);
}
