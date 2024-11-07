package ua.kusakabe.service;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.hibernate.TransactionException;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Transaction;
import ua.kusakabe.entity.ValidationForm;
import ua.kusakabe.entity.account.Account;
import ua.kusakabe.repository.AccountRepository;
import ua.kusakabe.repository.TransactionRepository;
import ua.kusakabe.util.AccountStatus;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    private String failureReason;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void validateTransaction(Transaction transaction) {
        Account senderAccount = findAccountByNumber(transaction.getSenderAccountNumber());
        Account receiverAccount = findAccountByNumber(transaction.getReceiverAccountNumber());
        ValidationForm validationForm = new ValidationForm(senderAccount, receiverAccount, transaction);
        //validateAccounts(validationForm);
        switch(validateAccounts(validationForm)){
            case TransactionStatus.COMMITTING -> executeTransaction(validationForm);
            case TransactionStatus.ROLLING_BACK -> rollbackTransaction(transaction);
        }
    }

    private void rollbackTransaction(Transaction transaction) {
        transaction.setTransactionStatus(TransactionStatus.ROLLED_BACK);
        transaction.setFailureReason(failureReason);
        transaction.setCompletedAt(new Date(System.currentTimeMillis()));
        saveTransaction(transaction);
        LOGGER.error("Transaction {} rolled back", transaction.getId());
    }

    private void executeTransaction(ValidationForm validationForm) {
        Transaction transaction = validationForm.getTransaction();
        Account senderAccount = validationForm.getSenderAccount();
        Account receiverAccount = validationForm.getReceiverAccount();
        senderAccount.setBalance(senderAccount.getBalance().subtract(transaction.getAmount()));
        senderAccount.setUpdatedAt(new Date(System.currentTimeMillis()));
        receiverAccount.setBalance(receiverAccount.getBalance().add(transaction.getAmount()));
        receiverAccount.setUpdatedAt(new Date(System.currentTimeMillis()));
        transaction.setTransactionStatus(TransactionStatus.COMMITTED);
        transaction.setCompletedAt(new Date(System.currentTimeMillis()));
        saveTransaction(transaction);
        saveSenderAccount(senderAccount);
        saveReceiverAccount(receiverAccount);
        LOGGER.info("Transaction {} successfully commited", transaction.getId());
    }

    private void saveReceiverAccount(Account receiverAccount) {
        try{
            accountRepository.save(receiverAccount);
        } catch(Exception e){
            throw new TransactionException(e.getMessage());
        }
    }

    private void saveSenderAccount(Account senderAccount) {
        try{
            accountRepository.save(senderAccount);
        } catch(Exception e){
            throw new TransactionException(e.getMessage());
        }
    }

    private void saveTransaction(Transaction transaction) {
        try{
            transactionRepository.save(transaction);
        } catch (Exception e){
            throw new TransactionException(e.getMessage());
        }
    }

    private TransactionStatus validateAccounts(ValidationForm validationForm) {
        boolean isCurrencyValid = validateCurrencies(validationForm);
        boolean isAccountsValid = validateAccountStatus(validationForm);
        boolean isBalanceValid = validateAccountBalance(validationForm);
        if(isCurrencyValid && isAccountsValid && isBalanceValid) {
            return TransactionStatus.COMMITTING;
        } else {
            return TransactionStatus.ROLLING_BACK;
        }
    }

    private boolean validateCurrencies(ValidationForm validationForm) {
        String transactionCurrency = validationForm.getTransaction().getCurrency();
        if(validationForm.getReceiverAccount().getCurrency().toString().equals(transactionCurrency) && validationForm.getSenderAccount().getCurrency().toString().equals(transactionCurrency)){
            return true;
        } else {
            failureReason = "Invalid account currencies";
            return false;
        }
    }

    private boolean validateAccountStatus(ValidationForm validationForm) {
        if(validationForm.getSenderAccount().getStatus().toString().equals(AccountStatus.ACTIVE.toString()) && validationForm.getReceiverAccount().getStatus().toString().equals(AccountStatus.ACTIVE.toString())){
            return true;
        } else {
            failureReason = "Invalid account status";
            return false;
        }
    }

    private boolean validateAccountBalance(ValidationForm validationForm) {
        BigDecimal senderBalance = validationForm.getSenderAccount().getBalance();
        BigDecimal transactionAmount = validationForm.getTransaction().getAmount();
        if(senderBalance.subtract(transactionAmount).compareTo(BigDecimal.ZERO) >= 0){
            return true;
        } else {
            failureReason = "Invalid account balance";
            return false;
        }
    }

    private Account findAccountByNumber(String accountNumber) {
        try {
            return accountRepository.findAccountByAccountNumber(accountNumber);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
