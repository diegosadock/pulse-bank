package br.com.sadocktech.pulse.account.repository;

import br.com.sadocktech.pulse.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository
		extends JpaRepository<Account, Long> {

	List<Account> findAllByCustomer_IdCustomer(Long customerId);
}