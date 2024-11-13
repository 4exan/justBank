package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.AuthRR;
import ua.kusakabe.service.CustomerService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, CustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
    }

    @PostMapping("/registration")
    public HttpStatus registerCustomer(@RequestBody AuthRR req){
        customerService.registerNewCustomer(req);
        return HttpStatus.CREATED;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRR> login(@RequestBody AuthRR req){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getPhone(), req.getPassword()));
        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(customerService.loginCustomer(req));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/validate")
    public String validateToken(@RequestBody AuthRR req){
        return customerService.validateToken(req);
    }
}
