package ua.kusakabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kusakabe.entity.account.Account;

public interface PocketRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(String accountNumber);
}
