package com.kata.bankaccount.domaine.account;

import com.kata.bankaccount.domaine.account.exceptions.AccountInsufficientBalanceException;
import com.kata.bankaccount.domaine.account.exceptions.InvalidAmountException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Builder
public class Account implements AccountOperations {
    private final AccountId accountId;
    private BigDecimal amount;
    private final LocalDateTime creationDate;

    /**
     * deposit operation
     *
     * @param amount BigDecimal should be positive
     * @throws InvalidAmountException if the amount is negative
     */
    @Override
    public void deposit(BigDecimal amount) {
        if (this.isNegative(amount)) {
            throw new InvalidAmountException();
        }
        this.amount = this.amount.add(amount);
        log.info("Operation: deposit amount {} to accountId {}", this.amount, this.accountId);
    }

    /**
     * withdrawal operation
     *
     * @param amount BigDecimal should be positive
     * @throws InvalidAmountException          if the amount is negative
     * @throws AccountInsufficientBalanceException if the fund not sufficient
     */
    @Override
    public void withdrawal(BigDecimal amount){
        if (isNegative(amount)) {
            throw new InvalidAmountException();
        }

        if (!hasSufficientFund(amount)) {
            throw new AccountInsufficientBalanceException();
        }

        this.amount = this.amount.subtract(amount);
        log.info("Operation: withdrawal amount {} to accountId {}", this.amount, this.accountId);
    }

    /**
     * check if the given amount is negative
     *
     * @param amount BigDecimal
     * @return true if the given amount is negative
     */
    private boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * @param amount BigDecimal
     * @return true if fund is sufficient
     */
    private boolean hasSufficientFund(final BigDecimal amount) {
        return this.getAmount().compareTo(amount) >= 0;
    }
}
