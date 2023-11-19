package com.vitortenorio.descomplicando.enumerator;

import lombok.Getter;

@Getter
public enum NodeField {
    DATA("data"),
    BODY("body"),
    NODES("nodes"),
    LIST_BY_SLUG("listBySlug"),
    TEXT_BY_TEXT_ID("textByTextId"),
    LIST_ITEMS_BY_LIST_ID("listItemsByListId"),
    CONTENTS_BY_QUESTION_ID("contentsByQuestionId"),
    CONTENTS_BY_ASSERTION_ID("contentsByAssertionId"),
    CONTENTS_BY_ASSERTION_ID_LIST("contentsByAssertionIdList");

    private final String field;

    NodeField(String field) {
        this.field = field;
    }
}
