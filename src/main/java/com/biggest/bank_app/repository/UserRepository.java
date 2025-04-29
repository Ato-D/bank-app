package com.biggest.bank_app.repository;

import com.biggest.bank_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);

    Boolean existsByAccountNumber(String accountNumber);


    @Query("""

            SELECT u FROM User u
            WHERE u.accountNumber = :accountNumber
""")
    User findByAccountNumber(@Param("accountNumber") String accountNumber);



}
