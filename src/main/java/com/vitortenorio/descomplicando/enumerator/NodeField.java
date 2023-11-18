package com.vitortenorio.descomplicando.enumerator;

import lombok.Getter;

@Getter
public enum NodeField {
    BODY("body"),
    NODES("nodes"),
    TEXT_BY_TEXT_ID("textByTextId"),
    CONTENTS_BY_QUESTION_ID("contentsByQuestionId"),
    CONTENTS_BY_ASSERTION_ID("contentsByAssertionId"),
    DATA("data"),
    LIST_BY_SLUG("listBySlug"),
    LIST_ITEMS_BY_LIST_ID("listItemsByListId");

    private final String field;

    NodeField(String field) {
        this.field = field;
    }
}
