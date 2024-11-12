package ua.kusakabe.entity.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pocket")
@Getter @Setter
public class Pocket extends Account {

    private Date targetDate;
    private BigDecimal targetAmount;
    private boolean autoDepositEnabled;
    private BigDecimal autoDepositAmount;

    private Pocket(Pocket.Builder builder) {
        super(builder);
        this.targetDate = builder.targetDate;
        this.targetAmount = builder.targetAmount;
        this.autoDepositEnabled = builder.autoDepositEnabled;
        this.autoDepositAmount = builder.autoDepositAmount;
    }

    public Pocket() {

    }

    public static class Builder extends Account.Builder<Pocket.Builder> {
        private Date targetDate;
        private BigDecimal targetAmount;
        private boolean autoDepositEnabled;
        private BigDecimal autoDepositAmount;

        public Pocket.Builder targetDate(Date targetDate) {
            this.targetDate = targetDate;
            return this;
        }

        public Pocket.Builder targetAmount(BigDecimal targetAmount) {
            this.targetAmount = targetAmount;
            return this;
        }

        public Pocket.Builder autoDepositEnabled(boolean autoDepositEnabled) {
            this.autoDepositEnabled = autoDepositEnabled;
            return this;
        }

        public Pocket.Builder autoDepositAmount(BigDecimal autoDepositAmount) {
            this.autoDepositAmount = autoDepositAmount;
            return this;
        }

        @Override
        protected Pocket.Builder self() {
            return this;
        }

        @Override
        public Account build() {
            return new Pocket(this);
        }
    }

}
