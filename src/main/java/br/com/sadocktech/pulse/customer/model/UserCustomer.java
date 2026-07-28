package br.com.sadocktech.pulse.customer.model;

import br.com.sadocktech.pulse.customer.model.enums.CustomerRole;
import br.com.sadocktech.pulse.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;

@Entity
@Table(name = "tbl_user_customer")
@Getter
@Setter
@NoArgsConstructor
public class UserCustomer {

	@EmbeddedId
	private UserCustomerId id;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@MapsId("customerId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	@Column(
			name = "role",
			nullable = false,
			columnDefinition = "customer_role"
	)
	private CustomerRole role;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
}