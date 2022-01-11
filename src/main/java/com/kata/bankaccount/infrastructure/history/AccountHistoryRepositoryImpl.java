package com.kata.bankaccount.infrastructure.history;

import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.history.AccountHistory;
import com.kata.bankaccount.domaine.history.AccountHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class AccountHistoryRepositoryImpl implements AccountHistoryRepository {
    private final Map<AccountId, List<AccountHistory>> histories;

    public AccountHistoryRepositoryImpl() {
        this.histories = new HashMap<>();
    }

    @Override
    public void save(AccountId id, AccountHistory accountHistory) {
        List<AccountHistory> accountHistories = this.histories.get(id);
        if (Objects.isNull(accountHistories)) {
            accountHistories = new ArrayList<>();
            this.histories.put(id, accountHistories);
        }
        accountHistories.add(accountHistory);
        log.info("Account history created for account {}", id);
    }

    @Override
    public List<AccountHistory> findByAccountId(AccountId id) {
        List<AccountHistory> histories = this.histories.getOrDefault(id, new ArrayList<>());
        log.info("Account histories count {} founded for account {}", histories.size(), id);
        return histories;
    }
}
