package br.com.sadocktech.pulse.customer.repository;

import br.com.sadocktech.pulse.customer.model.UserCustomer;
import br.com.sadocktech.pulse.customer.model.UserCustomerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCustomerRepository
		extends JpaRepository<UserCustomer, UserCustomerId> {

	List<UserCustomer> findAllByUser_IdUser(Long userId);

	Optional<UserCustomer> findByUser_IdUserAndCustomer_IdCustomer(
			Long userId,
			Long customerId
	);

	boolean existsByUser_IdUserAndCustomer_IdCustomer(
			Long userId,
			Long customerId
	);
}