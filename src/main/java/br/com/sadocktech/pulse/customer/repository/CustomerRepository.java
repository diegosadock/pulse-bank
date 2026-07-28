package br.com.sadocktech.pulse.customer.repository;

import br.com.sadocktech.pulse.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository
		extends JpaRepository<Customer, Long> {
}