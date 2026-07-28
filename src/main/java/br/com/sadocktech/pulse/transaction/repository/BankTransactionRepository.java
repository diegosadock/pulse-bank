package br.com.sadocktech.pulse.transaction.repository;

import br.com.sadocktech.pulse.transaction.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankTransactionRepository
		extends JpaRepository<BankTransaction, Long> {

	Optional<BankTransaction> findByEndToEndId(String endToEndId);
}