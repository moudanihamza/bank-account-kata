package com.kata.bankaccount.infrastructure.account;

import com.kata.bankaccount.domaine.account.Account;
import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.account.AccountRepository;
import com.kata.bankaccount.domaine.account.exceptions.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final Map<AccountId, Account> accounts;

    public AccountRepositoryImpl() {
        this.accounts = new HashMap<>();
    }

    @Override
    public Account findById(AccountId id) {
        Account account = Optional.ofNullable(this.accounts.get(id)).orElseThrow(AccountNotFoundException::new);
        log.info("Account Founded {}", id);
        return account;
    }
}
