package br.com.sadocktech.pulse.customer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_customer_pj")
@Getter
@Setter
@NoArgsConstructor
public class CustomerPj {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_customer_pj")
	private Long idCustomerPj;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(
			name = "customer_id",
			nullable = false,
			unique = true
	)
	private Customer customer;

	@Column(name = "legal_name", nullable = false, length = 255)
	private String legalName;

	@Column(name = "trade_name", length = 255)
	private String tradeName;

	@Column(nullable = false, unique = true, length = 14)
	private String cnpj;

	@Column(nullable = false, length = 255)
	private String email;

	@Column(length = 20)
	private String phone;
}