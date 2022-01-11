package com.kata.bankaccount.domaine.account;

import lombok.Value;

import java.util.UUID;

@Value
public class AccountId {
    UUID accountId;

    public AccountId() {
        this.accountId = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return accountId.toString();
    }
}
