package com.kata.bankaccount.application.account;

import com.kata.bankaccount.domaine.history.AccountHistoryService;
import com.kata.bankaccount.domaine.account.Account;
import com.kata.bankaccount.domaine.account.AccountId;
import com.kata.bankaccount.domaine.account.AccountRepository;
import com.kata.bankaccount.domaine.account.exceptions.AccountInsufficientBalanceException;
import com.kata.bankaccount.domaine.account.exceptions.AccountNotFoundException;
import com.kata.bankaccount.domaine.account.exceptions.InvalidAmountException;
import com.kata.bankaccount.domaine.history.AccountHistory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static com.kata.bankaccount.TestHelper.getTestAccount;
import static org.mockito.Mockito.*;

@Slf4j
@DisplayName("Account service tests")
public class AccountServiceImplTests {
    private final AccountServiceImpl accountServiceImpl;
    private final AccountHistoryService histories;
    private final AccountRepository accountRepository;

    public AccountServiceImplTests() {
        accountRepository = mock(AccountRepository.class);
        histories = mock(AccountHistoryService.class);
        accountServiceImpl = new AccountServiceImpl(accountRepository, histories);
    }

    @BeforeAll
    static void init() {
        log.info("********************  Account tests start  ********************");
    }

    @AfterAll
    static void done() {
        log.info("********************  Account tests done  ********************");
    }

    @Test
    @DisplayName("User should be able to deposit a valid amount")
    void user_should_be_able_to_deposit_an_amount() throws AccountNotFoundException, InvalidAmountException {
        Account account = getTestAccount();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(account);
        doNothing().when(histories).save(isA(AccountId.class), isA(AccountHistory.class));
        accountServiceImpl.deposit(account.getAccountId(), BigDecimal.ONE);
        verify(accountRepository, times(1)).findById(account.getAccountId());
        verify(histories, times(1)).save(isA(AccountId.class), isA(AccountHistory.class));
        Assertions.assertEquals(account.getAmount(), BigDecimal.valueOf(11));
    }

    @Test
    @DisplayName("Deposit method should return not found exception when id doesn't exist")
    void deposit_method_should_return_not_found_exception_when_id_doesnt_exist() throws AccountNotFoundException {
        AccountId accountId = new AccountId();
        Mockito.when(accountRepository.findById(accountId)).thenThrow(new AccountNotFoundException());
        AccountNotFoundException exception = Assertions.assertThrows(AccountNotFoundException.class, () -> accountServiceImpl.deposit(accountId, BigDecimal.ONE));
        verify(accountRepository, times(1)).findById(accountId);
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Account Not Found");
    }

    @Test
    @DisplayName("Deposit method should return invalid exception when amount is negative")
    void deposit_method_should_return_invalid_exception_when_amount_is_negative() throws AccountNotFoundException {
        Account account = getTestAccount();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(account);
        InvalidAmountException exception = Assertions.assertThrows(InvalidAmountException.class, () -> accountServiceImpl.deposit(account.getAccountId(), BigDecimal.ONE.negate()));
        verify(accountRepository, times(1)).findById(account.getAccountId());
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Amount Invalid");
    }

    @Test
    @DisplayName("User should be able to withdrawal a valid amount")
    void user_should_be_able_to_withdrawal_an_amount() throws AccountNotFoundException, InvalidAmountException, AccountInsufficientBalanceException {
        Account account = getTestAccount();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(account);
        doNothing().when(histories).save(isA(AccountId.class), isA(AccountHistory.class));
        accountServiceImpl.withdrawal(account.getAccountId(), BigDecimal.ONE);
        verify(accountRepository, times(1)).findById(account.getAccountId());
        verify(histories, times(1)).save(isA(AccountId.class), isA(AccountHistory.class));
        Assertions.assertEquals(account.getAmount(), BigDecimal.valueOf(9));
    }

    @Test
    @DisplayName("Withdrawal method should return not found exception when id doesn't exist")
    void withdrawal_method_should_return_not_found_exception_when_id_doesnt_exist() throws AccountNotFoundException {
        AccountId accountId = new AccountId();
        Mockito.when(accountRepository.findById(accountId)).thenThrow(new AccountNotFoundException());
        AccountNotFoundException exception = Assertions.assertThrows(AccountNotFoundException.class, () -> accountServiceImpl.withdrawal(accountId, BigDecimal.ONE));
        verify(accountRepository, times(1)).findById(accountId);
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Account Not Found");

    }

    @Test
    @DisplayName("Withdrawal method should return invalid exception when amount is negative")
    void withdrawal_method_should_return_invalid_exception_when_amount_is_negative() throws AccountNotFoundException {
        Account account = getTestAccount();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(account);
        InvalidAmountException exception = Assertions.assertThrows(InvalidAmountException.class, () -> accountServiceImpl.withdrawal(account.getAccountId(), BigDecimal.ONE.negate()));
        verify(accountRepository, times(1)).findById(account.getAccountId());
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Amount Invalid");
    }

    @Test
    @DisplayName("Withdrawal method should return Insufficient balance exception when balance is lower than the given amount")
    void withdrawal_method_should_return_insufficient_exception_when_balance_lower_than_the_given_amount() throws AccountNotFoundException {
        Account account = getTestAccount();
        Mockito.when(accountRepository.findById(account.getAccountId())).thenReturn(account);
        AccountInsufficientBalanceException exception = Assertions.assertThrows(AccountInsufficientBalanceException.class, () -> accountServiceImpl.withdrawal(account.getAccountId(), BigDecimal.valueOf(11)));
        verify(accountRepository, times(1)).findById(account.getAccountId());
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Insufficient balance");
    }

}
