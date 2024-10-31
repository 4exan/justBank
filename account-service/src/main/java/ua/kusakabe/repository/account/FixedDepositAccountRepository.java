package ua.kusakabe.repository.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kusakabe.entity.account.FixedDepositAccount;

@Repository
public interface FixedDepositAccountRepository extends JpaRepository<FixedDepositAccount, Long> {
    boolean existsByNumber(String number);
}
