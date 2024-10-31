package ua.kusakabe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Getter @Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "firstname")
    @Size(min = 3, max = 30)
    private String firstname;
    @Column(name = "lastname")
    @Size(min = 2, max = 50)
    private String lastname;
    @Column(name = "email", unique = true)
    @Email
    @Size(min = 5, max = 50)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone", unique = true)
    @Size(min = 10, max = 10)
    private String phone;
    @Column(name = "address")
    @Size(min = 5, max = 50)
    private String address;
    private String role;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

}
