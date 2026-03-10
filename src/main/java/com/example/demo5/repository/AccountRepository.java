package com.example.demo5.repository;

import com.example.demo5.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("SELECT a FROM Account a WHERE a.loginName = :loginName")
    // Tự động sinh câu lệnh query tìm account theo login_name
    Optional<Account> findByLoginName(String loginName);
}