package com.vitortenorio.descomplicando.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitortenorio.descomplicando.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class ObjectMapperUtil {
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = Logger.getLogger(ObjectMapperUtil.class.getName());
    public JsonNode readTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public <T> T readValue(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public <T> T readValue(File file, Class<T> clazz) {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }
}
