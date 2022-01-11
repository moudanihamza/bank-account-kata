package com.kata.bankaccount.domaine.account.exceptions;

public class AccountInsufficientBalanceException extends RuntimeException {
    private static final String MESSAGE = "Illegal Operation: Insufficient balance";

    public AccountInsufficientBalanceException() {
        super(MESSAGE);
    }
}
