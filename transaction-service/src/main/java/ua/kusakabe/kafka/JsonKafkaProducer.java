package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Transaction;

@Service
public class JsonKafkaProducer {

    private final String TOPIC_NAME = "transactions";

    private final KafkaTemplate<String, Transaction> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    @Autowired
    public JsonKafkaProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Transaction transaction) {
        try{
            kafkaTemplate.send(TOPIC_NAME, transaction);
            LOGGER.info("Message sent to topic {}", TOPIC_NAME);
        } catch (RuntimeException e){
            LOGGER.error("Error while sending message", e);
        }
    }

}
