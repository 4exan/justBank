package ua.kusakabe.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "credit_account")
@Getter @Setter
public class CreditAccount extends Account {

    private int creditLimit;

    private CreditAccount(Builder builder) {
        super(builder);
        this.creditLimit = builder.creditLimit;
    }

    public CreditAccount() {

    }

    public static class Builder extends Account.Builder<Builder> {
        private int creditLimit;

        public Builder creditLimit(int creditLimit) {
            this.creditLimit = creditLimit;
            return this;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Account build() {
            return new CreditAccount(this);
        }
    }

}
