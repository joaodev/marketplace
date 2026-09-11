package com.joaodev.marketplace.ticketing.application;

import com.joaodev.marketplace.common.infrastructure.event.dto.CustomerCreated;
import com.joaodev.marketplace.ticketing.domain.Customer;
import com.joaodev.marketplace.ticketing.domain.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerUseCase {
    private final CustomerRepository customerRepository;

    public CreateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void execute(CustomerCreated event) {
        var customer = new Customer(event.id(), event.name());
        customerRepository.save(customer);
    }
}
