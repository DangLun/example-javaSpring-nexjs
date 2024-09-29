package com.example.bookshop.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookshop.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Page<Account> findByAccountUsernameContaining(String username, Pageable pageable);
}
