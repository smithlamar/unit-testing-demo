package com.lamarjs.unittestingdemo;

import com.lamarjs.unittestingdemo.model.AccountEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AccountingServiceTest {

    // The thing we want to test
    private AccountingService accountingService;
    private BigDecimal overdraftItemFee;

    @Before
    public void setUp() throws Exception {
        accountingService = new AccountingService();
        overdraftItemFee = BigDecimal.TEN;
        accountingService.setOverdraftItemFee(overdraftItemFee);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void returns_correct_balance_from_account() {
        List<AccountEntry> testAccount = new ArrayList<>();

        accountingService.credit(testAccount, BigDecimal.valueOf(100.20), "USD", "Inital deposit");
        accountingService.debit(testAccount, BigDecimal.valueOf(100.20), "USD", "Branch withdrawl");
        accountingService.credit(testAccount, BigDecimal.valueOf(1000000.99), "USD", "Direct deposit - CJUG");

        BigDecimal balance = accountingService.calculateBalance(testAccount);

        assertEquals(BigDecimal.valueOf(1000000.99), balance);
    }

    @Test
    public void penalty_charge_is_assessed_once_per_debit_on_overdraft_item() {
        List<AccountEntry> testAccount = new ArrayList<>();

        accountingService.credit(testAccount, BigDecimal.valueOf(100.20), "USD", "Initial deposit");
        accountingService.debit(testAccount, BigDecimal.valueOf(100.20), "USD", "Branch withdrawal");
        accountingService.credit(testAccount, BigDecimal.valueOf(1000000.99), "USD", "Direct deposit - CJUG");
        accountingService.debit(testAccount, BigDecimal.valueOf(1000000.99), "USD", "Debit - CJUG reversal");

        BigDecimal preOverdraftBalance = accountingService.calculateBalance(testAccount);

        accountingService.debit(testAccount, BigDecimal.ONE, "USD", "Debit - CJUG revenge");
        accountingService.debit(testAccount, BigDecimal.valueOf(3.27), "USD", "Debit - Kwik-E-Mart");

        BigDecimal postOverdraftBalance = accountingService.calculateBalance(testAccount);
        BigDecimal expectedBalance = overdraftItemFee.multiply(BigDecimal.valueOf(2))
                .negate()
                .subtract(preOverdraftBalance);

        assertEquals(expectedBalance, postOverdraftBalance);
    }
}