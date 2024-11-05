package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.AccountDto;
import ua.kusakabe.service.AccountService;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/get")
    public ResponseEntity<AccountDto> getAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        return ResponseEntity.ok(accountService.getCustomerAccount(header));
    }

    @PostMapping("/create")
    public HttpStatus createAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @RequestBody AccountDto req) {
        return accountService.createNewAccount(req, header);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteAccount(@RequestHeader(HttpHeaders.AUTHORIZATION) String header, @PathVariable String id) {
        return accountService.deleteAccount(header, id);
    }

}
