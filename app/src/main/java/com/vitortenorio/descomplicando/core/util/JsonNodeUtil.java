package com.vitortenorio.descomplicando.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.vitortenorio.descomplicando.enums.NodeField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonNodeUtil {
    private final ObjectMapperUtil objectMapperUtil;

    public JsonNode getQuestion(JsonNode jsonNode) {
        return jsonNode.path(NodeField.CONTENTS_BY_QUESTION_ID.getField())
                .path(NodeField.NODES.getField()).get(0)
                .path(NodeField.TEXT_BY_TEXT_ID.getField())
                .path(NodeField.BODY.getField());
    }

    public JsonNode getSingleAnswer(JsonNode jsonNode) {
        return jsonNode.path(NodeField.CONTENTS_BY_ASSERTION_ID.getField())
                .path(NodeField.NODES.getField()).get(0)
                .path(NodeField.TEXT_BY_TEXT_ID.getField())
                .path(NodeField.BODY.getField());
    }

    public JsonNode getMultipleAnswer(JsonNode jsonNode) {
        return jsonNode.path(NodeField.CONTENTS_BY_ASSERTION_ID_LIST.getField())
                .get(0)
                .path(NodeField.TEXT_BY_TEXT_ID.getField())
                .path(NodeField.BODY.getField());
    }

    public JsonNode buildMainNode(String json) {
        JsonNode rootNode = objectMapperUtil.readTree(json);

        return rootNode.path(NodeField.DATA.getField())
                .path(NodeField.LIST_BY_SLUG.getField())
                .path(NodeField.LIST_ITEMS_BY_LIST_ID.getField())
                .path(NodeField.NODES.getField());

    }

    public List<JsonNode> createListFromSingleNode(JsonNode nodes) {
        List<JsonNode> nodesList = new ArrayList<>();
        for (JsonNode node : nodes) {
            nodesList.add(node);
        }
        return nodesList;
    }
}
