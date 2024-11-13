package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Notification;
import ua.kusakabe.entity.Transaction;

@Service
public class NotificationProducer {

    private final String TOPIC_NAME = "notifications";
    private final Logger LOGGER = LoggerFactory.getLogger(NotificationProducer.class);

    private final KafkaTemplate<String, Notification> kafkaTemplate;

    @Autowired
    public NotificationProducer(KafkaTemplate<String, Notification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Notification notification) {
        try{
            kafkaTemplate.send(TOPIC_NAME, notification);
            LOGGER.info("Notification sent to topic {}", TOPIC_NAME);
        } catch (Exception e) {
            LOGGER.error("Failed to sent notification to {}", TOPIC_NAME, e);
        }
    }

}
