package ua.kusakabe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Customer;
import ua.kusakabe.entity.Notification;
import ua.kusakabe.entity.Transaction;
import ua.kusakabe.entity.account.Account;
import ua.kusakabe.kafka.NotificationProducer;
import ua.kusakabe.repository.AccountRepository;
import ua.kusakabe.repository.CustomerRepository;

@Service
public class NotificationService {

    private NotificationProducer notificationProducer;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    Notification notification;

    @Autowired
    public NotificationService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public void sendNotification(Transaction transaction) {
        buildNotification(transaction);
//        notificationProducer.sendMessage(notification);
        System.out.println("Imagine that notification is send...");
    }

    private void buildNotification(Transaction transaction) {
        Customer sender = getCustomerByAccountNumber(transaction.getSenderAccountNumber());
        Customer recipient = getCustomerByAccountNumber(transaction.getReceiverAccountNumber());

        notification = Notification.builder()
                .senderAccountNumber(transaction.getSenderAccountNumber())
                .senderFirstName(sender.getFirstname())
                .senderLastName(sender.getLastname())
                .senderEmail(sender.getEmail())
                .recipientAccountNumber(transaction.getReceiverAccountNumber())
                .recipientFirstName(recipient.getFirstname())
                .recipientLastName(recipient.getLastname())
                .recipientEmail(recipient.getEmail())
                .transactionAmount(transaction.getAmount())
                .transactionCurrency(transaction.getCurrency())
                .build();
    }

    private Customer getCustomerByAccountNumber(String accountNumber) {
        try {
            Account account = accountRepository.findAccountByAccountNumber(accountNumber);
            return customerRepository.findById(account.getUserId());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
