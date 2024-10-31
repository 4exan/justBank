package ua.kusakabe.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "fixed_deposit_account")
@Getter @Setter
public class FixedDepositAccount extends Account{

    private String number;
    private Date balanceLockedTo;
    private double interestRate;

    private FixedDepositAccount(Builder builder) {
        super(builder);
        this.number = builder.number;
        this.balanceLockedTo = builder.balanceLockedTo;
        this.interestRate = builder.interestRate;
    }

    public FixedDepositAccount() {

    }

    public static class Builder extends Account.Builder<Builder> {
        private String number;
        private Date balanceLockedTo;
        private double interestRate;

        public Builder number(String number){
            this.number = number;
            return this;
        }

        public Builder balanceLockedTo(Date balanceLockedTo) {
            this.balanceLockedTo = balanceLockedTo;
            return this;
        }

        public Builder interestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Account build() {
            return new FixedDepositAccount(this);
        }
    }

}