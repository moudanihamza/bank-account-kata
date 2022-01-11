package com.kata.bankaccount.application.history;


import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.history.AccountHistoryRepository;
import com.kata.bankaccount.domaine.history.AccountHistory;
import com.kata.bankaccount.domaine.history.AccountHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import java.util.Collections;
import java.util.List;
import static com.kata.bankaccount.TestHelper.getTestAccountHistory;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@DisplayName("Account history service tests")
public class AccountHistoryServiceImplTests {
    private final AccountHistoryRepository repository;
    private final AccountHistoryService historyService;

    public AccountHistoryServiceImplTests() {
        this.repository = mock(AccountHistoryRepository.class);
        this.historyService = new AccountHistoryServiceImpl(this.repository);
    }

    @BeforeAll
    static void init() {
        log.info("********************  Account history service tests start  ********************");
    }

    @AfterAll
    static void done() {
        log.info("********************  Account history service tests done  ********************");
    }

    @Test
    @DisplayName("Account history service should save  history object")
    void account_history_service_should_save_history_object() {
        AccountId accountId = new AccountId();
        AccountHistory history = getTestAccountHistory();
        doNothing().when(this.repository).save(eq(accountId), eq(history));
        this.historyService.save(accountId, history);
        verify(this.repository).save(eq(accountId), eq((history)));
    }

    @Test
    @DisplayName("Account history service should find histories of given accountId")
    void account_history_service_should_find_histories_of_given_accountId() {
        AccountId accountId = new AccountId();
        AccountHistory history = getTestAccountHistory();
        List<AccountHistory> expected = Collections.singletonList(history);
        when(this.repository.findByAccountId(eq(accountId))).thenReturn(expected);
        List<AccountHistory> actual = this.historyService.findByAccountId(accountId);
        Assertions.assertEquals(expected, actual);
        verify(repository).findByAccountId(eq(accountId));
    }

}
