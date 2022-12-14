package com.bonial.challenge.configuration;

public enum ErrorCode {
    INTERNAL_ERROR(100, "Error interno del servidor"),
    WEB_CLIENT_GENERIC(101, "Error del Web Client"),
    DATABASE_CONNECTION_ERROR(102, "Database connection has timed-out"),
    RESOURCE_NOT_FOUND_ERROR(103, "Resource not found"),
    RESOURCE_DUPLICATE_ERROR(104, "Duplicated resource"),
    BAD_REQUEST(105, "The request is incorrect."),
    NO_LOCATIONS(106, "There are not locations near to your coordinates"),
    ID_NOT_MATCH(107, "Request Parameters not match");
    private final int value;
    private final String reasonPhrase;

    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
