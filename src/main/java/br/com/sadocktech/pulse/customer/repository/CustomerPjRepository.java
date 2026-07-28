package br.com.sadocktech.pulse.customer.repository;

import br.com.sadocktech.pulse.customer.model.CustomerPj;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerPjRepository
		extends JpaRepository<CustomerPj, Long> {

	Optional<CustomerPj> findByCustomer_IdCustomer(Long customerId);

	Optional<CustomerPj> findByCnpj(String cnpj);

	boolean existsByCnpj(String cnpj);
}