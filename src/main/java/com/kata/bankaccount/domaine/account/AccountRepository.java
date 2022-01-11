package com.kata.bankaccount.domaine.account;


public interface AccountRepository {
    Account findById(AccountId id);
}
