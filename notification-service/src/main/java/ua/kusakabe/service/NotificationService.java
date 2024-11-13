package ua.kusakabe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Notification;

@Service
public class NotificationService {

    private final EmailService emailService;

    @Autowired
    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processNotification(Notification notification) {
        emailService.sendTransactionNotificationToSender(notification);
        emailService.sendTransactionNotificationToRecipient(notification);
    }
}
