package com.vitortenorio.descomplicando.enums;

public enum ProblemType {
    SYSTEM_ERROR("/system-error", "System error"),
    BUSINESS_ERROR("/business-error", "Business error");

    private final String title;
    private final String uri;

    ProblemType(String uri, String title) {
        this.uri = uri;
        this.title = title;
    }

    public String title() {
        return title;
    }

    public String uri() {
        return uri;
    }
}
