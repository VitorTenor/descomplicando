package com.vitortenorio.descomplicando.infrastructure.filemanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.infrastructure.filemanager.util.ObjectMapperAbstract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class FileReaderService extends ObjectMapperAbstract {

    public <T> T readValue(File file, Class<T> clazz) {
        return super.readValue(file, clazz);
    }

    public JsonNode readTree(String json) {
        return super.readTree(json);
    }
}
