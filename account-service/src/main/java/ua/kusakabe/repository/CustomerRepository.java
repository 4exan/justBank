package ua.kusakabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.kusakabe.entity.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByPhone(String phone);
    @Query("select case when c.role='ADMIN' then true else false end from Customer c where c.phone=:phoneNumber")
    boolean isAdminByPhone(@Param("phoneNumber") String phone);
}
