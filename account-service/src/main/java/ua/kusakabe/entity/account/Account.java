package ua.kusakabe.entity.account;

import jakarta.persistence.*;
import lombok.*;
import ua.kusakabe.util.AccountCurrency;
import ua.kusakabe.util.AccountStatus;
import ua.kusakabe.util.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    private String accountNumber;
    private long userId;
    private AccountType type;
    private AccountCurrency currency;
    private BigDecimal balance;
    private Date createdAt;
    private Date updatedAt;
    private AccountStatus status;

    private boolean active;
    private boolean twoFactorEnabled;

    public Account() {
    }

    protected Account(Builder<?> builder){
        this.accountId = builder.accountId;
        this.accountNumber = builder.accountNumber;
        this.userId = builder.userId;
        this.type = builder.type;
        this.currency = builder.currency;
        this.balance = builder.balance;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.status = builder.status;

        this.active = builder.active;
        this.twoFactorEnabled = builder.twoFactorEnabled;
    }

    public static abstract class Builder<B extends Builder<B>> {
        private long accountId;
        private String accountNumber;
        private long userId;
        private AccountType type;
        private AccountCurrency currency;
        private BigDecimal balance;
        private Date createdAt;
        private Date updatedAt;
        private AccountStatus status;

        private boolean active;
        private boolean twoFactorEnabled;

        public B accountId(long accountId) {
            this.accountId = accountId;
            return self();
        }

        public B accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return self();
        }

        public B userId(long userId) {
            this.userId = userId;
            return self();
        }

        public B type(AccountType type) {
            this.type = type;
            return self();
        }

        public B currency(AccountCurrency currency) {
            this.currency = currency;
            return self();
        }

        public B balance(BigDecimal balance) {
            this.balance = balance;
            return self();
        }

        public B createdAt(Date createdAt) {
            this.createdAt = createdAt;
            return self();
        }

        public B updatedAt(Date updatedAt) {
            this.updatedAt = updatedAt;
            return self();
        }

        public B status(AccountStatus status) {
            this.status = status;
            return self();
        }

        public B active(boolean active) {
            this.active = active;
            return self();
        }

        public B twoFactorEnabled(boolean twoFactorEnabled) {
            this.twoFactorEnabled = twoFactorEnabled;
            return self();
        }

        protected abstract B self();

        public abstract Account build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accountId);
    }
}
