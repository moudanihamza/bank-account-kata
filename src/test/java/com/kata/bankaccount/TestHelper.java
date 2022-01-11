package com.kata.bankaccount;

import com.kata.bankaccount.domaine.account.Account;
import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.history.AccountHistory;
import com.kata.bankaccount.domaine.history.AccountOperation;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * A simple class that simplify object creation for tests
 */
public class TestHelper {

    /**
     * Account creation for tests
     *
     * @return Account
     */
    public static Account getTestAccount() {
        return Account.builder()
                .accountId(new AccountId())
                .amount(BigDecimal.TEN)
                .creationDate(LocalDateTime.now(Clock.systemUTC()))
                .build();
    }

    /**
     * Account history creation for tests
     * @return AccountHistory
     */
    public static AccountHistory getTestAccountHistory() {
        return AccountHistory.builder()
                .amount(BigDecimal.TEN)
                .operation(AccountOperation.DEPOSIT)
                .balance(BigDecimal.ONE)
                .creationDate(LocalDateTime.now())
                .build();
    }
}
