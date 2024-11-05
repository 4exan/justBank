package ua.kusakabe.service;

import jakarta.transaction.Transactional;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.kusakabe.dto.TransactionDto;
import ua.kusakabe.entity.Transaction;
import ua.kusakabe.kafka.JsonKafkaProducer;
import ua.kusakabe.repository.TransactionRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final JsonKafkaProducer jsonKafkaProducer;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, JsonKafkaProducer jsonKafkaProducer) {
        this.transactionRepository = transactionRepository;
        this.jsonKafkaProducer = jsonKafkaProducer;
    }

    @Transactional
    public HttpStatus createTransaction(TransactionDto req) {
        Transaction transaction = configureTransaction(req);
        saveTransaction(transaction);
        sendTransaction(transaction);
        return HttpStatus.CREATED;
    }

    private void sendTransaction(Transaction transaction) {
        try{
            jsonKafkaProducer.sendMessage(transaction);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void saveTransaction(Transaction transaction) {
        try{
            transactionRepository.save(transaction);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Transaction could not be saved");
        }
    }

    private Transaction configureTransaction(TransactionDto req) {
        return Transaction.builder()
                .id(UUID.randomUUID())
                .senderAccountNumber(req.getSenderAccountNumber())
                .receiverAccountNumber(req.getReceiverAccountNumber())
                .amount(req.getAmount())
                .currency(req.getCurrency())
                .transactionStatus(TransactionStatus.ACTIVE)
                .createdAt(new Date(System.currentTimeMillis()))
                .transactionType(req.getTransactionType())
                .description(req.getDescription())
                .build();
    }

    public TransactionDto getAllTransactions(String accountNumber) {
        TransactionDto res = new TransactionDto();
        List<Transaction> transactionList = getAllByAccountNumber(accountNumber);
        res.setTransactionList(transactionList);
        return res;
    }

    private List<Transaction> getAllByAccountNumber(String accountNumber) {
        try{
            List<Transaction> transactionList = transactionRepository.findAllBySenderAccountNumber(accountNumber);
            if(!transactionList.isEmpty()){
                return transactionList;
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        }
        return null; //REMOVE !!!!!!!
    }
}
