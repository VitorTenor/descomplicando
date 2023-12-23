package com.vitortenorio.descomplicando.gateway;

import com.vitortenorio.descomplicando.entity.AnswerEntity;

import javax.inject.Named;
import java.util.List;

@Named
public interface AnswerGateway {
    List<Integer> processTrueAnswer(List<AnswerEntity> answer);
}
