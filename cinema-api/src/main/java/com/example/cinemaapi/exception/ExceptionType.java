package com.example.cinemaapi.exception;

/**
 * .
 */
public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    WRONG_PASSWORD("wrong.password"),
    DUPLICATE_ENTITY("duplicate"),
    ENTITY_EXCEPTION("exception"),
    USER_NOT_VERIFY_YET("not.verify.yet");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    String getValue() {
        return this.value;
    }
}
