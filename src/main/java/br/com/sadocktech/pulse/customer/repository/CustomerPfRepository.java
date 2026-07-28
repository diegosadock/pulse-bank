package br.com.sadocktech.pulse.customer.repository;

import br.com.sadocktech.pulse.customer.model.CustomerPf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerPfRepository
		extends JpaRepository<CustomerPf, Long> {

	Optional<CustomerPf> findByCustomer_IdCustomer(Long customerId);

	Optional<CustomerPf> findByCpf(String cpf);

	boolean existsByCpf(String cpf);
}