package ua.kusakabe.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter @Setter
public class Notification {

    private String senderAccountNumber;
    private String senderFirstName;
    private String senderLastName;
    private String senderEmail;
    private String recipientAccountNumber;
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientEmail;

    private BigDecimal transactionAmount;
    private String transactionCurrency;

}
//RECIPIENT
//Dear {firstName} {lastName}!
//You receive {transactionAmount} {transactionCurrency} on your {accountNumber} account.

//SENDER
//Dear {firstName} {lastName}!
//You successfully send {transactionAmount} {transactionCurrency} from your {accountNumber} account.