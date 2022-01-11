package com.kata.bankaccount.domaine.account.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public static final String MESSAGE = "Illegal Operation: Account Not Found";
    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
