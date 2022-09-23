package io.github.mateusznk.webankapp.domain.api;

public class PaymentBasicInfo {
    private final String senderAccount;
    private final String receiverAccount;
    private final Double amount;

    public PaymentBasicInfo(String senderAccount, String receiverAccount, Double amount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public Double getAmount() {
        return amount;
    }
}
