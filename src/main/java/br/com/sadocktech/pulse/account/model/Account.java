package br.com.sadocktech.pulse.account.model;

import br.com.sadocktech.pulse.account.model.enums.AccountStatus;
import br.com.sadocktech.pulse.account.model.enums.AccountType;
import br.com.sadocktech.pulse.customer.model.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_account")
@Getter
@Setter
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_account")
	private Long idAccount;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(
			name = "available_balance",
			nullable = false,
			precision = 19,
			scale = 2
	)
	private BigDecimal availableBalance = BigDecimal.ZERO;

	@Column(
			name = "ledger_balance",
			nullable = false,
			precision = 19,
			scale = 2
	)
	private BigDecimal ledgerBalance = BigDecimal.ZERO;

	@Column(name = "bank_name", nullable = false, length = 255)
	private String bankName;

	@Column(name = "bank_code", nullable = false, length = 10)
	private String bankCode;

	@Column(name = "branch_number", nullable = false, length = 10)
	private String branchNumber;

	@Column(name = "branch_check_digit", length = 5)
	private String branchCheckDigit;

	@Column(name = "account_number", nullable = false, length = 20)
	private String accountNumber;

	@Column(name = "account_check_digit", length = 5)
	private String accountCheckDigit;

	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(
			name = "type",
			nullable = false,
			columnDefinition = "account_type"
	)
	private AccountType type;

	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(
			name = "status",
			nullable = false,
			columnDefinition = "account_status"
	)
	private AccountStatus status = AccountStatus.PENDING;
}