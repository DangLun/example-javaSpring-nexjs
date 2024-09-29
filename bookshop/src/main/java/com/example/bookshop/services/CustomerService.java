package com.example.bookshop.services;

import com.example.bookshop.models.Customer;
import com.example.bookshop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Thêm khách hàng
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Iterable<Customer> getAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> searchCustomers(String searchText, Pageable pageable) {
        // Giả sử bạn đang tìm kiếm theo tên khách hàng
        return customerRepository.findByCustomerNameContainingIgnoreCase(searchText, pageable);
    }

    public Optional<Customer> getCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    // Sửa khách hàng
    public Customer updateCustomer(Integer customerId, Customer customerDetails) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setCustomerName(customerDetails.getCustomerName());
        customer.setCustomerPhone(customerDetails.getCustomerPhone());
        customer.setCustomerEmail(customerDetails.getCustomerEmail());
        customer.setCustomerPicture(customerDetails.getCustomerPicture());

        return customerRepository.save(customer);
    }

    // Xóa khách hàng
    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }
}
