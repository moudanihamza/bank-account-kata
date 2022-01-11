package com.kata.bankaccount.infrastructure.history;

import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.history.AccountHistoryRepository;
import com.kata.bankaccount.domaine.history.AccountHistory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kata.bankaccount.TestHelper.getTestAccountHistory;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@DisplayName("Account history repository tests")
public class AccountHistoryRepositoryImplTests {

    private final AccountHistoryRepository historyRepository;

    public AccountHistoryRepositoryImplTests() {
        this.historyRepository = new AccountHistoryRepositoryImpl();
    }

    @BeforeAll
    static void init() {
        log.info("********************  Account history repository tests start  ********************");
    }

    @AfterAll
    static void done() {
        log.info("********************  Account history repository tests done  ********************");
    }

    @Test
    @DisplayName("Account history repository should save  history object")
    void account_history_repository_should_save_history_object() {
        AccountId accountId = new AccountId();
        AccountHistory history = getTestAccountHistory();
        Map<AccountId, List<AccountHistory>> histories = mock(Map.class);
        List<AccountHistory> historyList = Collections.singletonList(history);
        when(histories.put(eq(accountId), eq(historyList))).thenReturn(historyList);
        setHistory(histories);
        this.historyRepository.save(accountId, history);
        verify(histories).put(eq(accountId), eq(Collections.singletonList(history)));
    }

    @Test
    @DisplayName("Account history repository should find histories of given accountId")
    void account_history_repository_should_find_histories_of_given_accountId() {
        AccountId accountId = new AccountId();
        AccountHistory expected = getTestAccountHistory();
        Map<AccountId, List<AccountHistory>> histories = mock(Map.class);
        List<AccountHistory> expectedHistoryList = Collections.singletonList(expected);
        when(histories.getOrDefault(eq(accountId), isA(List.class))).thenReturn(expectedHistoryList);
        setHistory(histories);
        List<AccountHistory> actual = this.historyRepository.findByAccountId(accountId);
        Assertions.assertEquals(expectedHistoryList, actual);
        verify(histories).getOrDefault(eq(accountId), isA(List.class));
    }

    /**
     * set mocked histories
     *
     * @param histories mocked map of accountId and list of histories to set using reflection
     */
    private void setHistory(Map<AccountId, List<AccountHistory>> histories) {
        ReflectionTestUtils.setField(historyRepository, "histories", histories);
    }
}
