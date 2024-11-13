package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.kusakabe.dto.AccountDto;
import ua.kusakabe.entity.Customer;
import ua.kusakabe.entity.account.*;
import ua.kusakabe.exception.UnsupportedAccountTypeException;
import ua.kusakabe.repository.AccountRepository;
import ua.kusakabe.repository.PocketRepository;
import ua.kusakabe.repository.account.CheckingAccountRepository;
import ua.kusakabe.repository.CustomerRepository;
import ua.kusakabe.repository.account.CreditAccountRepository;
import ua.kusakabe.repository.account.FixedDepositAccountRepository;
import ua.kusakabe.repository.account.SavingsAccountRepository;
import ua.kusakabe.util.AccountStatus;
import ua.kusakabe.util.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CheckingAccountRepository checkingAccountRepository;
    private final CreditAccountRepository creditAccountRepository;
    private final FixedDepositAccountRepository fixedDepositAccountRepository;
    private final SavingsAccountRepository savingsAccountRepository;
    private final PocketRepository pocketRepository;
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(AccountRepository accountRepository, CheckingAccountRepository checkingAccountRepository, CreditAccountRepository creditAccountRepository, FixedDepositAccountRepository fixedDepositAccountRepository, SavingsAccountRepository savingsAccountRepository, PocketRepository pocketRepository, CustomerRepository customerRepository, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.creditAccountRepository = creditAccountRepository;
        this.fixedDepositAccountRepository = fixedDepositAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
        this.pocketRepository = pocketRepository;
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
    }

    @Cacheable("account")
    public AccountDto getCustomerAccount(String header) {
        long userId = getUserIdFromToken(header);
        List<Account> accountList = accountRepository.findAllByUserIdAndTypeNot(userId, AccountType.POCKET);
        if(!accountList.isEmpty()){
            AccountDto res = new AccountDto();
            res.setAccountList(accountList);
            return res;
        }else {
            throw new RuntimeException("No account found for user id " + userId);
        }
    }

    @CachePut("data")
    public AccountDto update(String header) {
        long userId = getUserIdFromToken(header);
        List<Account> accountList = accountRepository.findAllByUserIdAndTypeNot(userId, AccountType.POCKET);
        if(!accountList.isEmpty()){
            AccountDto res = new AccountDto();
            res.setAccountList(accountList);
            return res;
        }else {
            throw new RuntimeException("No account found for user id " + userId);
        }
    }

    private long getUserIdFromToken(String header) {
        String phone = jwtService.extractPhone(header.substring(7));
        Customer customer = customerRepository.findCustomerByPhone(phone).orElseThrow(()->new RuntimeException("No such user in data base!"));
        return customer.getId();
    }

    public HttpStatus createNewAccount(AccountDto req, String header) {
        long userId = getUserIdFromToken(header);
        Account account = createAccount(req, userId);
        return saveAccount(account);
    }

    private HttpStatus savePocket(Account account) {
        try{
            accountRepository.save(account);
            LOGGER.info("Pocket: {} saved successfully!", account.getAccountId());
            return HttpStatus.CREATED;
        }catch (Exception e){
            LOGGER.error("Pocket saving failed!", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private HttpStatus saveAccount(Account account) {
        try{
            accountRepository.save(account);
            LOGGER.info("Account: {} successfully saved!", account.getAccountId());
            return HttpStatus.CREATED;
        } catch (Exception e) {
            LOGGER.error("Account saving failed!", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Account createAccount(AccountDto req, long userId) {
        return switch(req.getType()){
            case CHECKING -> createNewCheckingAccount(req, userId);
            case CREDIT -> createNewCreditAccount(req, userId);
            case SAVINGS -> createNewSavingsAccount(req, userId);
            case FIXED_DEPOSIT -> createNewFixedDepositAccount(req, userId);
            case POCKET -> createNewPocket(req, userId);
            default -> throw new UnsupportedAccountTypeException("Unsupported account type: " + req.getType());
        };
    }

    private Account createNewPocket(AccountDto req, long userId) {
        return new Pocket.Builder()
                .userId(userId)
                .accountNumber(generateAccountNumber(AccountType.POCKET))
                .type(AccountType.POCKET)
                .currency(req.getCurrency())
                .name(req.getName())
                .balance(BigDecimal.ZERO)
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .status(AccountStatus.ACTIVE)
                .targetDate(req.getTargetDate())
                .targetAmount(req.getTargetAmount())
                .autoDepositEnabled(req.isAutoDepositEnabled())
                .autoDepositAmount(req.getAutoDepositAmount())
                .build();
    }

    private Account createNewCheckingAccount(AccountDto req, long userId) {
        return new CheckingAccount.Builder()
                .userId(userId)
                .accountNumber(generateAccountNumber(AccountType.CHECKING))
                .type(AccountType.CHECKING)
                .currency(req.getCurrency())
                .balance(BigDecimal.ZERO)
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .status(AccountStatus.ACTIVE)
                .overdraftLimit(req.getOverdraftLimit())
                .build();
    }

    private Account createNewCreditAccount(AccountDto req, long userId) {
        return new CreditAccount.Builder()
                .userId(userId)
                .accountNumber(generateAccountNumber(AccountType.CREDIT))
                .type(AccountType.CREDIT)
                .currency(req.getCurrency())
                .balance(BigDecimal.ZERO)
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .status(AccountStatus.ACTIVE)
                .creditLimit(req.getCreditLimit())
                .build();
    }

    private Account createNewSavingsAccount(AccountDto req, long userId) {
        return new SavingsAccount.Builder()
                .userId(userId)
                .accountNumber(generateAccountNumber(AccountType.SAVINGS))
                .type(AccountType.SAVINGS)
                .currency(req.getCurrency())
                .balance(BigDecimal.ZERO)
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .status(AccountStatus.ACTIVE)
                .interestRate(req.getInterestRate())
                .transactionLimit(req.getTransactionLimit())
                .build();
    }

    private Account createNewFixedDepositAccount(AccountDto req, long userId) {
        return new FixedDepositAccount.Builder()
                .userId(userId)
                .accountNumber(generateAccountNumber(AccountType.FIXED_DEPOSIT))
                .type(AccountType.FIXED_DEPOSIT)
                .currency(req.getCurrency())
                .balance(BigDecimal.ZERO)
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .status(AccountStatus.ACTIVE)
                .interestRate(req.getInterestRate())
                .balanceLockedTo(req.getBalanceLockedTo())
                .interestRate(req.getInterestRate())
                .build();
    }

    private String generateAccountNumber(AccountType type) {
        boolean isNumberAvailable = false;
        String accNumber = "";
        while(!isNumberAvailable) {
            int number = new Random().nextInt(100000000);
            String identifier = switch (type) {
                case CHECKING -> "CHK";
                case CREDIT -> "CRD";
                case SAVINGS -> "SAV";
                case FIXED_DEPOSIT -> "FDS";
                case POCKET -> "PKT";
                default -> throw new UnsupportedAccountTypeException("Unsupported account type: " + type);
            };
            accNumber = identifier + number;
            isNumberAvailable = checkIfNumberIsAvailable(identifier, accNumber);
        }
        return accNumber;
    }

    private boolean checkIfNumberIsAvailable(String identifier, String accNumber) {
        return switch(identifier){
            case "CHK" -> isCheckingAccountAvailable(accNumber);
            case "CRD" -> isCreditAccountAvailable(accNumber);
            case "SAV" -> isSavingsAccountAvailable(accNumber);
            case "FDS" -> isFixedDepositAccountAvailable(accNumber);
            case "PKT" -> isPocketAvailable(accNumber);
            default -> throw new UnsupportedAccountTypeException("Unsupported account type: " + accNumber);
        };
    }

    private boolean isPocketAvailable(String accNumber) {
        try{
            return !pocketRepository.existsByAccountNumber(accNumber);
        }catch(RuntimeException e){
            throw new RuntimeException("Checking account failed", e);
        }
    }

    private boolean isCheckingAccountAvailable(String accNumber) {
        try{
            return !checkingAccountRepository.existsByAccountNumber(accNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException("Checking account failed!", e);
        }
    }

    private boolean isCreditAccountAvailable(String accNumber) {
        try{
            return !creditAccountRepository.existsByAccountNumber(accNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException("Checking account failed!", e);
        }
    }

    private boolean isSavingsAccountAvailable(String accNumber) {
        try{
            return !savingsAccountRepository.existsByAccountNumber(accNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException("Checking account failed!", e);
        }
    }

    private boolean isFixedDepositAccountAvailable(String accNumber) {
        try{
            return !fixedDepositAccountRepository.existsByAccountNumber(accNumber);
        } catch (RuntimeException e) {
            throw new RuntimeException("Checking account failed!", e);
        }
    }

    public HttpStatus suspendAccount(String header, String accountNumber) {
        boolean isAdmin = getUserRole(header);
        if(!isAdmin){
            return HttpStatus.FORBIDDEN;
        }
        Account account = accountRepository.findAccountByAccountNumber(accountNumber);
        account.setStatus(AccountStatus.SUSPENDED);
        account.setUpdatedAt(new Date(System.currentTimeMillis()));
        saveAccount(account);
        return HttpStatus.OK;
    }

    private boolean getUserRole(String header) {
        String phone = jwtService.extractPhone(header.substring(7));
        return customerRepository.isAdminByPhone(phone);
    }

    @Cacheable("pocket")
    public AccountDto getPockets(String header) {
        long userId = getUserIdFromToken(header);
        List<Account> pocketList = accountRepository.findAllByUserIdAndType(userId, AccountType.POCKET);
        if(!pocketList.isEmpty()){
            AccountDto res = new AccountDto();
            res.setPocketList(pocketList);
            return res;
        }else {
            throw new RuntimeException("No pocket found for user id " + userId);
        }
    }
}
