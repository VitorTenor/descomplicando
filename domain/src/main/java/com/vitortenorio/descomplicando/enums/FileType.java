package com.vitortenorio.descomplicando.enums;

public enum FileType {
    XLSX(".xlsx"),
    JSON(".json");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }
}
