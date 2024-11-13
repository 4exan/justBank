package ua.kusakabe.repository;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kusakabe.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAllBySenderAccountNumberAndTransactionStatus(String accountNumber, TransactionStatus status);
    List<Transaction> findAllByReceiverAccountNumberAndTransactionStatus(String accountNumber, TransactionStatus status);

    @Query("SELECT SUM(t.amount) FROM Transaction t " +
            "WHERE t.receiverAccountNumber = :accountNumber " +
            "AND t.transactionStatus = 2 " +
            "AND MONTH(t.completedAt) = MONTH(CURRENT_DATE) " +
            "AND YEAR(t.completedAt) = YEAR(CURRENT_DATE)")
    Optional<Double> sumReceivedAmountCurrentMonth(@Param("accountNumber") String accountNumber);

    @Query("SELECT SUM(t.amount) FROM Transaction t " +
            "WHERE t.receiverAccountNumber = :accountNumber " +
            "AND t.transactionStatus = 2 " +
            "AND MONTH(t.completedAt) = MONTH(CURRENT_DATE) - 1 " +
            "AND YEAR(t.completedAt) = YEAR(CURRENT_DATE)")
    Optional<Double> sumReceivedAmountPreviousMonth(@Param("accountNumber") String accountNumber);

    @Query("SELECT SUM(t.amount) FROM Transaction t " +
            "WHERE t.senderAccountNumber = :accountNumber " +
            "AND t.transactionStatus = 2 " +
            "AND MONTH(t.completedAt) = MONTH(CURRENT_DATE) " +
            "AND YEAR(t.completedAt) = YEAR(CURRENT_DATE)")
    Optional<Double> sumSentAmountCurrentMonth(@Param("accountNumber") String accountNumber);

    @Query("SELECT SUM(t.amount) FROM Transaction t " +
            "WHERE t.senderAccountNumber = :accountNumber " +
            "AND t.transactionStatus = 2 " +
            "AND MONTH(t.completedAt) = MONTH(CURRENT_DATE) - 1 " +
            "AND YEAR(t.completedAt) = YEAR(CURRENT_DATE)")
    Optional<Double> sumSentAmountPreviousMonth(@Param("accountNumber") String accountNumber);
}

