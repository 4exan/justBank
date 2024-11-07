package ua.kusakabe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import ua.kusakabe.entity.Transaction;
import ua.kusakabe.util.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {
    private UUID id;
    private TransactionType transactionType;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
    private String currency;
    private TransactionStatus transactionStatus;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String description;
    private BigDecimal fee;

    List<Transaction> incomingTransactionList;
    List<Transaction> outgoingTransactionList;
    private TransactionStatistics transactionStatistics;
}
