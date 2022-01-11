package com.kata.bankaccount.application.account;

import com.kata.bankaccount.domaine.history.AccountHistoryService;
import com.kata.bankaccount.domaine.account.Account;
import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.account.AccountRepository;
import com.kata.bankaccount.domaine.account.AccountService;
import com.kata.bankaccount.domaine.account.exceptions.AccountInsufficientBalanceException;
import com.kata.bankaccount.domaine.account.exceptions.AccountNotFoundException;
import com.kata.bankaccount.domaine.account.exceptions.InvalidAmountException;
import com.kata.bankaccount.domaine.history.AccountHistory;
import com.kata.bankaccount.domaine.history.AccountOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * In our case (in memory database) we don't need transactional stereotype
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountHistoryService histories;

    @Override
    public void deposit(AccountId id, BigDecimal amount) throws AccountNotFoundException, InvalidAmountException {
        log.info("Operation: deposit start for account {} with amount {}", id, amount);
        Account account = this.accountRepository.findById(id);
        BigDecimal balance = account.getAmount();
        account.deposit(amount);
        log.info("Operation: deposit end for account {} with amount {}", id, amount);
        this.saveHistory(AccountOperation.DEPOSIT, id, account, balance);
    }

    @Override
    public void withdrawal(AccountId id, BigDecimal amount) throws AccountNotFoundException, AccountInsufficientBalanceException, InvalidAmountException {
        log.info("Operation: withdrawal start for account {} with amount {}", id, amount);
        Account account = this.accountRepository.findById(id);
        BigDecimal balance = account.getAmount();
        account.withdrawal(amount);
        log.info("Operation: withdrawal end for account {} with amount {}", id, amount);
        this.saveHistory(AccountOperation.WITHDRAWAL, id, account, balance);
    }

    private void saveHistory(AccountOperation operation, AccountId id, Account account, BigDecimal balance) {
        AccountHistory history = this.toAccountHistory(operation, balance, account.getAmount());
        this.histories.save(id, history);
    }

    private AccountHistory toAccountHistory(AccountOperation operation, BigDecimal balance, BigDecimal amount) {
        return AccountHistory.builder().amount(amount).balance(balance).operation(operation).creationDate(LocalDateTime.now(Clock.systemUTC())).build();
    }
}
