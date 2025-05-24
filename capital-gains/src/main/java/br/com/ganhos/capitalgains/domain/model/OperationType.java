package br.com.ganhos.capitalgains.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OperationType {
    BUY, SELL;

    @JsonCreator
    public static OperationType fromString(String value) {
        return OperationType.valueOf(value.toUpperCase());
    }
}
