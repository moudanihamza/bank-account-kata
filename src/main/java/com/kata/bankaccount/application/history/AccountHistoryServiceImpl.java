package com.kata.bankaccount.application.history;

import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.history.AccountHistoryRepository;
import com.kata.bankaccount.domaine.history.AccountHistory;
import com.kata.bankaccount.domaine.history.AccountHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * In our case (in memory database) we don't need transactional stereotype
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountHistoryServiceImpl implements AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepository;

    /**
     * save history of a given account
     * @param id AccountId
     * @param accountHistory AccountHistory
     */
    @Override
    public void save(AccountId id, AccountHistory accountHistory) {
        log.info("Operation: save history for account {} start", id);
        this.accountHistoryRepository.save(id, accountHistory);
        log.info("Operation: save history for account {} end", id);
    }

    /**
     * find All operations by accountId
     * @param id AccountId
     * @return a list of AccountHistory
     */
    @Override
    public List<AccountHistory> findByAccountId(AccountId id) {
        log.info("Operation: find histories for account {} start", id);
        List<AccountHistory> histories = this.accountHistoryRepository.findByAccountId(id);
        log.info("Operation: find histories count {} for account {} end", histories.size(), id);
        return histories;
    }
}
