package com.vitortenorio.descomplicando.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitortenorio.descomplicando.exception.BusinessException;
import com.vitortenorio.descomplicando.model.request.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtil {
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = Logger.getLogger(ObjectMapperUtil.class.getName());
    public JsonNode readTree(String json) {
        LOGGER.info("Reading tree");
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public Assert readValue(String jsonAssert, Class<Assert> assertClass) {
        LOGGER.info("Reading value");
        try {
            return objectMapper.readValue(jsonAssert, assertClass);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }
}
