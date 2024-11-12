package ua.kusakabe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.kusakabe.entity.account.Account;
import ua.kusakabe.util.Currency;
import ua.kusakabe.util.AccountStatus;
import ua.kusakabe.util.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
public class AccountDto {

    private long accountId;
    private long userId;
    private String accountNumber;
    private AccountType type;
    private Currency currency;
    private BigDecimal balance;
    private Date createdAt;
    private Date updatedAt;
    private AccountStatus status;

    private double overdraftLimit;
    private double interestRate;
    private int transactionLimit;
    private int creditLimit;
    private Date balanceLockedTo;

    private boolean active;
    private boolean twoFactorEnabled;

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private Date targetDate;
    private BigDecimal targetAmount;
    private boolean autoDepositEnabled;
    private BigDecimal autoDepositAmount;

    private List<Account> accountList;
    private List<Account> pocketList;

}
