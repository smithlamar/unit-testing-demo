package com.lamarjs.unittestingdemo.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class AccountEntry {

    private final String id;
    private final Instant timestamp;
    private final BigDecimal value;
    private String currency;
    private String note;
    private final Boolean isFee;

    public AccountEntry(BigDecimal value, String currency, String note, Boolean isFee) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = Instant.now();
        this.value = value;
        this.currency = currency;
        this.note = note;
        this.isFee = isFee;
    }

    public AccountEntry(BigDecimal value, String currency, String note) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = Instant.now();
        this.value = value;
        this.currency = currency;
        this.note = note;
        this.isFee = false;
    }

    public String getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean isFee() {
        return isFee;
    }

    @Override
    public String toString() {
        return "AccountEntry{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", value=" + value +
                ", currency='" + currency + '\'' +
                ", note='" + note + '\'' +
                ", isFee=" + isFee +
                '}';
    }
}
