package com.vitortenorio.descomplicando.enums;


public enum NodeField {
    DATA("data"),
    BODY("body"),
    NODES("nodes"),
    LIST_BY_SLUG("listBySlug"),
    TEXT_BY_TEXT_ID("textByTextId"),
    LIST_ITEMS_BY_LIST_ID("listItemsByListId"),
    ASSERTIONS_BY_QUESTION_ID("assertionsByQuestionId"),
    CONTENTS_BY_QUESTION_ID("contentsByQuestionId"),
    QUESTION_BY_QUESTION_ID("questionByQuestionId"),
    CONTENTS_BY_ASSERTION_ID("contentsByAssertionId"),
    CONTENTS_BY_ASSERTION_ID_LIST("contentsByAssertionIdList");

    private final String field;

    NodeField(String field) {
        this.field = field;
    }

    public String field() {
        return field;
    }
}
