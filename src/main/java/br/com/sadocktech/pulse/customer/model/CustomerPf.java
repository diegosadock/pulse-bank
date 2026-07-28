package br.com.sadocktech.pulse.customer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_customer_pf")
@Getter
@Setter
@NoArgsConstructor
public class CustomerPf {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_customer_pf")
	private Long idCustomerPf;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
			name = "customer_id",
			nullable = false,
			unique = true
	)
	private Customer customer;

	@Column(name = "full_name", nullable = false, length = 255)
	private String fullName;

	@Column(nullable = false, length = 255)
	private String email;

	@Column(length = 20)
	private String phone;

	@Column(length = 30)
	private String rg;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	@Column(nullable = false, unique = true, length = 11)
	private String cpf;
}