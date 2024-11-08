package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Notification;
import ua.kusakabe.service.NotificationService;

@Service
public class TransactionConsumer {
    private final Logger LOGGER = LoggerFactory.getLogger(TransactionConsumer.class);
    private final NotificationService notificationService;

    @Autowired
    public TransactionConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notifications", groupId = "notificationGroup")
    public void validate(Notification notification) {
        LOGGER.info("Processing notification {}", notification);
        notificationService.processNotification(notification);
    }
}
