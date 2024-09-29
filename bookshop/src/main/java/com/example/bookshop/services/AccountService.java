package com.example.bookshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.bookshop.models.Account;
import com.example.bookshop.models.Customer;
import com.example.bookshop.repositories.AccountRepository;
import com.example.bookshop.repositories.CustomerRepository;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public Iterable<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Integer accountId, Account accountDetails) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setAccountUsername(accountDetails.getAccountUsername());
            account.setAccountPassword(accountDetails.getAccountPassword());
            account.setAccountIsBan(accountDetails.getAccountIsBan());
            account.setRole(accountDetails.getRole());
            account.setCustomer(accountDetails.getCustomer());
            return accountRepository.save(account);
        } else {
            throw new RuntimeException("Account not found");
        }
    }

    public void deleteAccount(Integer accountId) {
        Optional<Account> account = this.accountRepository.findById(accountId);
        if (account.isPresent()) {
            Account exitsAccount = account.get();
            Customer customer = customerRepository.findById(exitsAccount.getCustomer().getCustomerId()).get();
            customerRepository.delete(customer);
            this.accountRepository.delete(exitsAccount);
        }
    }

    public Page<Account> searchAccounts(String username, Pageable pageable) {
        return accountRepository.findByAccountUsernameContaining(username, pageable);
    }
}
