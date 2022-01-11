package com.kata.bankaccount.domaine.history;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Value
@Builder
public class AccountHistory {
    BigDecimal amount;
    BigDecimal balance;
    LocalDateTime creationDate;
    AccountOperation operation;
}
