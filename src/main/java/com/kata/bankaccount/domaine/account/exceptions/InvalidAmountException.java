package com.kata.bankaccount.domaine.account.exceptions;

public class InvalidAmountException extends RuntimeException {

    public static final String MESSAGE = "Illegal Operation: Amount Invalid";

    public InvalidAmountException() {
        super(MESSAGE);
    }
}
