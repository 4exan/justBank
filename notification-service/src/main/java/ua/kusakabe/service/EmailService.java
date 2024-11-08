package ua.kusakabe.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Notification;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    //RECIPIENT
    //Dear {firstName} {lastName}!
    //You receive {transactionAmount} {transactionCurrency} on your {accountNumber} account.

    //SENDER
    //Dear {firstName} {lastName}!
    //You successfully send {transactionAmount} {transactionCurrency} from your {accountNumber} account.

    public void sendTransactionNotificationToSender(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getSenderEmail());
        message.setSubject("Transaction Notification");
        message.setText("You successfully send " + notification.getTransactionAmount() + " " + notification.getTransactionCurrency() + " from your " + notification.getSenderAccountNumber() + " account.");
        mailSender.send(message);
    }

    public void sendTransactionNotificationToRecipient(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getRecipientEmail());
        message.setSubject("Transaction Notification");
        message.setText("You receive " + notification.getTransactionAmount() + " " + notification.getTransactionCurrency() + " on your " + notification.getSenderAccountNumber() + " account.");
        mailSender.send(message);
    }

}
