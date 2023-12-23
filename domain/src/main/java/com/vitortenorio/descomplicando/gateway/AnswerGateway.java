package com.vitortenorio.descomplicando.gateway;

import com.vitortenorio.descomplicando.entity.AnswerEntity;

import java.util.List;

public interface AnswerGateway {
    List<Integer> processTrueAnswer(List<AnswerEntity> answer);
}
