package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.kusakabe.entity.Transaction;
import ua.kusakabe.service.TransactionService;

@Service
public class TransactionConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionConsumer.class);
    private final TransactionService transactionService;

    @Autowired
    public TransactionConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "transactions", groupId = "accountGroup")
    public void validate(Transaction transaction) {
        LOGGER.info("Validating transaction {}", transaction);
        transactionService.validateTransaction(transaction);
    }
}
