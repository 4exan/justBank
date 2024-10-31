package ua.kusakabe.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "savings_account")
@Getter @Setter
public class SavingsAccount extends Account {

    private String number;
    private double interestRate;
    private int transactionLimit;

    private SavingsAccount(Builder builder) {
        super(builder);
        this.number = builder.number;
        this.interestRate = builder.interestRate;
        this.transactionLimit = builder.transactionLimit;
    }

    public SavingsAccount() {

    }

    public static class Builder extends Account.Builder<Builder> {
        private String number;
        private double interestRate;
        private int transactionLimit;

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public Builder transactionLimit(int transactionLimit) {
            this.transactionLimit = transactionLimit;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Account build() {
            return new SavingsAccount(this);
        }
    }

}
