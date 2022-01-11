package com.kata.bankaccount.domaine.account;

import java.math.BigDecimal;

public interface AccountOperations {
    void deposit(BigDecimal amount);

    void withdrawal(BigDecimal amount);
}
