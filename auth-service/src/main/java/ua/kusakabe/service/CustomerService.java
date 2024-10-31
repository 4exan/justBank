package ua.kusakabe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.kusakabe.dto.AuthRR;
import ua.kusakabe.entity.Customer;
import ua.kusakabe.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registerNewCustomer(AuthRR req) {
        Customer newCustomer = createNewCustomer(req);
        saveCustomerToDataBase(newCustomer);
    }

    public AuthRR loginCustomer(AuthRR req) {
        String token = jwtService.generateToken(req);
        if(!token.isBlank()){
            return AuthRR.builder()
                    .statusCode(200)
                    .message("User logged in successfully!")
                    .token(token)
                    .build();
        } else {
            return AuthRR.builder()
                    .statusCode(400)
                    .message("User login failed!")
                    .build();
        }
    }

    public String validateToken(AuthRR req) {
        Customer tokenBearer = findTokenBearer(req.getToken());
        boolean isTokenValid = jwtService.validateToken(req.getToken(), tokenBearer.getUsername());
        if(isTokenValid){
            return "OK";
        } else {
            return "BAD";
        }
    }

    private Customer findTokenBearer(String token) {
        return customerRepository.findByPhone(jwtService.extractPhone(token)).orElseThrow(()->new RuntimeException("No such user in data base!"));
    }

    private void saveCustomerToDataBase(Customer newCustomer) {
        try{
            customerRepository.save(newCustomer);
            LOGGER.info("Customer with id {} has been saved", newCustomer.getId());
        }catch (RuntimeException e){
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    private Customer createNewCustomer(AuthRR req) {
        return Customer.builder()
                .firstname(req.getFirstname())
                .lastname(req.getLastname())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .address(req.getAddress())
                .role("USER")
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();
    }
}
