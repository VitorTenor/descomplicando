package com.vitortenorio.descomplicando.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.enums.NodeField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JsonNodeUtil {
    private final ObjectMapperUtil objectMapperUtil;
    private final Logger LOGGER = Logger.getLogger(JsonNodeUtil.class.getName());

    public JsonNode getQuestion(JsonNode jsonNode) {
        LOGGER.info("Getting question");
        return jsonNode.path(NodeField.CONTENTS_BY_QUESTION_ID.getField())
                .path(NodeField.NODES.getField()).get(0)
                .path(NodeField.TEXT_BY_TEXT_ID.getField())
                .path(NodeField.BODY.getField());
    }

    public JsonNode getAnswer(JsonNode jsonNode) {
        LOGGER.info("Getting answer");
        return jsonNode.path(NodeField.CONTENTS_BY_ASSERTION_ID.getField())
                .path(NodeField.NODES.getField()).get(0)
                .path(NodeField.TEXT_BY_TEXT_ID.getField())
                .path(NodeField.BODY.getField());
    }

    public JsonNode getAnswer2(JsonNode jsonNode) {
        LOGGER.info("Getting answer");
        return jsonNode.path(NodeField.CONTENTS_BY_ASSERTION_ID_LIST.getField())
                .get(0)
                .path(NodeField.TEXT_BY_TEXT_ID.getField())
                .path(NodeField.BODY.getField());
    }

    public JsonNode buildMainNode(String json) {
        LOGGER.info("Building main node");
        JsonNode rootNode = objectMapperUtil.readTree(json);

        return rootNode.path(NodeField.DATA.getField())
                .path(NodeField.LIST_BY_SLUG.getField())
                .path(NodeField.LIST_ITEMS_BY_LIST_ID.getField())
                .path(NodeField.NODES.getField());

    }

    public List<JsonNode> createListFromSingleNode(JsonNode nodes) {
        LOGGER.info("Creating list from single node");
        List<JsonNode> nodesList = new ArrayList<>();
        for (JsonNode node : nodes) {
            nodesList.add(node);
        }
        return nodesList;
    }
}
