package ua.kusakabe.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "checking_account")
@Getter @Setter
public class CheckingAccount extends Account {

    private String number;
    private double overdraftLimit;

    protected CheckingAccount(Builder builder) {
        super(builder);
        this.number = builder.number;
        this.overdraftLimit = builder.overdraftLimit;
    }

    public CheckingAccount() {

    }

    public static class Builder extends Account.Builder<Builder> {
        private String number;
        private double overdraftLimit;

        public Builder overdraftLimit(double overdraftLimit) {
            this.overdraftLimit = overdraftLimit;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }


        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public CheckingAccount build() {
            return new CheckingAccount(this);
        }
    }
}
