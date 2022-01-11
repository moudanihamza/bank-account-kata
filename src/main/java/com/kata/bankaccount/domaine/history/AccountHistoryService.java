package com.kata.bankaccount.domaine.history;

import com.kata.bankaccount.domaine.account.AccountId;
import java.util.List;

public interface AccountHistoryService {
    void save(AccountId id, AccountHistory accountHistory);

    List<AccountHistory> findByAccountId(AccountId id);
}
