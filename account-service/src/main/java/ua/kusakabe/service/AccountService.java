package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.kusakabe.dto.AccountDto;
import ua.kusakabe.entity.Customer;
import ua.kusakabe.entity.account.*;
import ua.kusakabe.exception.UnsupportedAccountTypeException;
import ua.kusakabe.repository.AccountRepository;
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
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
    private final RestTemplate restTemplate;

    @Autowired
    public AccountService(AccountRepository accountRepository, CheckingAccountRepository checkingAccountRepository, CreditAccountRepository creditAccountRepository, FixedDepositAccountRepository fixedDepositAccountRepository, SavingsAccountRepository savingsAccountRepository, CustomerRepository customerRepository, JwtService jwtService, RestTemplate restTemplate) {
        this.accountRepository = accountRepository;
        this.checkingAccountRepository = checkingAccountRepository;
        this.creditAccountRepository = creditAccountRepository;
        this.fixedDepositAccountRepository = fixedDepositAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.restTemplate = restTemplate;
    }

    public AccountDto getCustomerAccount(String header) {
        long userId = getUserIdFromToken(header);
        List<Account> accountList = accountRepository.findAllByUserId(userId);
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

    private HttpStatus saveAccount(Account account) {
        try{
            accountRepository.save(account);
            LOGGER.info("Account: {} successfully saved!", account.getAccountId());
            return HttpStatus.CREATED;
        } catch (RuntimeException e) {
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
            default -> throw new UnsupportedAccountTypeException("Unsupported account type: " + req.getType());
        };
    }

    private Account createNewCheckingAccount(AccountDto req, long userId) {
        return new CheckingAccount.Builder()
                .userId(userId)
                .accountNumber(generateAccountNumber(req.getType()))
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
                .accountNumber(generateAccountNumber(req.getType()))
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
                .accountNumber(generateAccountNumber(req.getType()))
                .type(AccountType.CREDIT)
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
                .accountNumber(generateAccountNumber(req.getType()))
                .type(AccountType.CREDIT)
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
            default -> throw new UnsupportedAccountTypeException("Unsupported account type: " + accNumber);
        };
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
}
