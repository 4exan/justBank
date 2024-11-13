package ua.kusakabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kusakabe.dto.AccountDto;
import ua.kusakabe.entity.account.Account;
import ua.kusakabe.entity.account.CheckingAccount;
import ua.kusakabe.entity.account.Pocket;
import ua.kusakabe.util.AccountType;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByUserIdAndTypeNot(Long userId, AccountType type);
    Account findAccountByAccountNumber(String accountNumber);
    List<Account> findAllByUserIdAndType(Long userId, AccountType type);
}
