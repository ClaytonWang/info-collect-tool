package com.dw3c.infocollecttool.utils;

import lombok.Getter;

@Getter
public enum Status {
    SUCCESS("SUCCESS"),
    FAILURE("FAILURE"),
    ERROR("ERROR"),
    PROCESSING("PROCESSING"),
    PROCESSED("PROCESSED"),
    UPLOADED("UPLOADED");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
