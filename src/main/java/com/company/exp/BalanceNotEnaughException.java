package com.company.exp;

public class BalanceNotEnaughException extends RuntimeException{
    public BalanceNotEnaughException(String message) {
        super(message);
    }
}
