package ua.kusakabe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import ua.kusakabe.util.TransactionType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
public class Transaction {

    @Id
    private UUID id;
    private TransactionType transactionType;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
    private String currency;
    private TransactionStatus transactionStatus;
    private Date createdAt;
    private Date completedAt;
    private String description;
    private BigDecimal fee;
    private String failureReason;

    public Transaction() {
    }

    public Transaction(UUID id, TransactionType transactionType, String senderAccountNumber, String receiverAccountNumber, BigDecimal amount, String currency, TransactionStatus transactionStatus, Date createdAt, Date completedAt, String description, BigDecimal fee, String failureReason) {
        this.id = id;
        this.transactionType = transactionType;
        this.senderAccountNumber = senderAccountNumber;
        this.receiverAccountNumber = receiverAccountNumber;
        this.amount = amount;
        this.currency = currency;
        this.transactionStatus = transactionStatus;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.description = description;
        this.fee = fee;
        this.failureReason = failureReason;
    }
}
