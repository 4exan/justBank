package ua.kusakabe.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kusakabe.entity.account.CreditAccount;

@Repository
public interface CreditAccountRepository extends JpaRepository<CreditAccount, Long> {
    boolean existsByNumber(String number);
}
