package com.vitortenorio.descomplicando.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vitortenorio.descomplicando.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.logging.Logger;

@Slf4j
@Component
@RequiredArgsConstructor
public class ObjectMapperUtil {
    private final ObjectMapper objectMapper = configureJsonObjectMapper();

    public JsonNode readTree(String json) {
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public <T> T readValue(File file, Class<T> clazz) {
        try {
            return objectMapper.readValue(file, clazz);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public void writeValueAsFile(File file, Object value) {
        try {
            objectMapper.writeValue(file, value);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    private ObjectMapper configureJsonObjectMapper(){
        var mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
}
