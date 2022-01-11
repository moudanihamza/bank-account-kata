package com.kata.bankaccount.infrastructure.account;

import com.kata.bankaccount.domaine.account.Account;
import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.account.AccountRepository;
import com.kata.bankaccount.domaine.account.exceptions.AccountNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static com.kata.bankaccount.TestHelper.getTestAccount;
import static org.mockito.Mockito.*;

@Slf4j
@DisplayName("Account repository tests")
public class AccountRepositoryImplTests {
    private final AccountRepository accountRepository;

    public AccountRepositoryImplTests() {
        this.accountRepository = new AccountRepositoryImpl();
    }

    @BeforeAll
    static void init() {
        log.info("********************  Account repository tests start  ********************");
    }

    @AfterAll
    static void done() {
        log.info("********************  Account repository tests done  ********************");
    }

    @Test
    @DisplayName("Account repository should find an existing one")
    void account_repository_should_find_an_existing_one() {
        Account expected = getTestAccount();
        Map<AccountId, Account> accounts = mock(Map.class);
        when(accounts.get(expected.getAccountId())).thenReturn(expected);
        setAccounts(accounts);
        Account actual = this.accountRepository.findById(expected.getAccountId());
        verify(accounts, times(1)).get(expected.getAccountId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Account repository should return not found exception")
    void account_repository_should_return_not_found_exception() {
        AccountNotFoundException exception = Assertions.assertThrows(AccountNotFoundException.class, () -> this.accountRepository.findById(new AccountId()));
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Account Not Found");
    }

    /**
     * set mocked accounts
     * @param accounts mocked map to inject in the repository using reflexion
     */
    private void setAccounts(Map<AccountId, Account> accounts) {
        ReflectionTestUtils.setField(accountRepository, "accounts", accounts);
    }
}
