package com.lamarjs.unittestingdemo;

import com.lamarjs.unittestingdemo.model.AccountEntry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountingService {
    private BigDecimal overdraftItemFee = BigDecimal.ZERO;


    public BigDecimal calculateBalance(List<AccountEntry> testAccount) {
        return testAccount.stream().map(AccountEntry::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void debit(List<AccountEntry> account, BigDecimal value, String currency, String note, Boolean isFee) {
        account.add(new AccountEntry(value.negate(), currency, note, isFee));
        AccountEntry lastEntry = account.get(account.size() - 1);
        if (calculateBalance(account).doubleValue() < 0 && !lastEntry.isFee()) {
            debit(account, overdraftItemFee, "USD", "Overdraft Item Fee for item: " + lastEntry.getId(), true);
        }
    }

    public void debit(List<AccountEntry> account, BigDecimal value, String currency, String note) {
        debit(account, value, currency, note, false);
    }

    public void credit(List<AccountEntry> account, BigDecimal value, String currency, String note) {
        account.add(new AccountEntry(value, currency, note));
    }

    public BigDecimal getOverdraftItemFee() {
        return overdraftItemFee;
    }

    public void setOverdraftItemFee(BigDecimal overdraftItemFee) {
        this.overdraftItemFee = overdraftItemFee;
    }
}
