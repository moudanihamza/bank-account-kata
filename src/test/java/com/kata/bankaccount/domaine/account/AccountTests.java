package com.kata.bankaccount.domaine.account;

import com.kata.bankaccount.domaine.account.exceptions.AccountInsufficientBalanceException;
import com.kata.bankaccount.domaine.account.exceptions.InvalidAmountException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static com.kata.bankaccount.TestHelper.getTestAccount;

@Slf4j
@DisplayName("Account unit tests")
public class AccountTests {

    @BeforeAll
    static void start() {
        log.info("********************  Account tests start  ********************");
    }

    @AfterAll
    static void done() {
        log.info("********************  Account tests done  ********************");
    }

    @Test
    @DisplayName("User should be able to deposit a valid amount")
    void user_should_be_able_to_deposit_a_valid_amount(){
        Account account = getTestAccount();
        account.deposit(BigDecimal.ONE);
        Assertions.assertEquals(account.getAmount(), BigDecimal.valueOf(11));
    }

    @Test
    @DisplayName("Deposit method should return invalid exception when amount is negative")
    void deposit_method_should_return_invalid_exception_when_amount_is_negative() {
        Account account = getTestAccount();
        InvalidAmountException exception = Assertions.assertThrows(InvalidAmountException.class, () -> account.deposit(BigDecimal.ONE.negate()));
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Amount Invalid");
    }

    @Test
    @DisplayName("User should be able to withdrawal a valid amount")
    void user_should_be_able_to_withdrawal_a_valid_amount() throws InvalidAmountException, AccountInsufficientBalanceException {
        Account account = getTestAccount();
        account.withdrawal(BigDecimal.ONE);
        Assertions.assertEquals(account.getAmount(), BigDecimal.valueOf(9));
    }

    @Test
    @DisplayName("Withdrawal method should return invalid exception when amount is negative")
    void withdrawal_method_should_return_invalid_exception_when_amount_is_negative() {
        Account account = getTestAccount();
        InvalidAmountException exception = Assertions.assertThrows(InvalidAmountException.class, () -> account.withdrawal(BigDecimal.ONE.negate()));
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Amount Invalid");
    }
    @Test
    @DisplayName("Withdrawal method should return Insufficient balance exception when balance is lower than the given amount")
    void withdrawal_method_should_return_insufficient_exception_when_balance_lower_than_the_given_amount() {
        Account account = getTestAccount();
        AccountInsufficientBalanceException exception = Assertions.assertThrows(AccountInsufficientBalanceException.class, () -> account.withdrawal(BigDecimal.valueOf(11)));
        Assertions.assertEquals(exception.getMessage(), "Illegal Operation: Insufficient balance");
    }


}
