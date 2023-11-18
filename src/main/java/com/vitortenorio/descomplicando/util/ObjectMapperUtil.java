package com.vitortenorio.descomplicando.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.model.request.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtil {
    private final ObjectMapper objectMapper;

    public JsonNode readTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Assert readValue(String jsonAssert, Class<Assert> assertClass) {
        try {
            return objectMapper.readValue(jsonAssert, assertClass);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
