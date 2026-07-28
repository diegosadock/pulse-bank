package br.com.sadocktech.pulse.customer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCustomerId implements Serializable {

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "customer_id")
	private Long customerId;
}