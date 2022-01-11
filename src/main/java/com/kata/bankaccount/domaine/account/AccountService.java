package com.kata.bankaccount.domaine.account;

import java.math.BigDecimal;

public interface AccountService {
    void deposit(AccountId id, BigDecimal amount);

    void withdrawal(AccountId id, BigDecimal amount);
}
