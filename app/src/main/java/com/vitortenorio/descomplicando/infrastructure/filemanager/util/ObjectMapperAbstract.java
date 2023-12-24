package com.vitortenorio.descomplicando.infrastructure.filemanager.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vitortenorio.descomplicando.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class ObjectMapperAbstract {
    private final ObjectMapper objectMapper = configureJsonObjectMapper();

    public JsonNode readTree(String json) {
        try {
            return this.objectMapper.readTree(json);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public <T> T readValue(File file, Class<T> clazz) {
        try {
            return this.objectMapper.readValue(file, clazz);
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
