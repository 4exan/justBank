package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.TransactionDto;
import ua.kusakabe.entity.Transaction;
import ua.kusakabe.service.TransactionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    public HttpStatus createTransaction(@RequestBody TransactionDto req) {
        return transactionService.createTransaction(req);
    }

    @GetMapping("/get-all/{accountNumber}")
    public ResponseEntity<TransactionDto> getAllTransaction(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getAllTransactions(accountNumber));
    }

    @GetMapping("/get/{UUID}")
    public String getTransactionById(@PathVariable UUID UUID) {
        return "TEST";
    }

}
