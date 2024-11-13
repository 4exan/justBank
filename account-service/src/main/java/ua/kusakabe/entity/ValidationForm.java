package ua.kusakabe.entity;

import ua.kusakabe.entity.account.Account;

public class ValidationForm {
    private Account senderAccount;
    private Account receiverAccount;
    private Transaction transaction;

    public ValidationForm() {
    }

    public ValidationForm(Account senderAccount, Account receiverAccount, Transaction transaction) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.transaction = transaction;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
