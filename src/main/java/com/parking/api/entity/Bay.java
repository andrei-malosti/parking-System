package com.parking.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bayNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status;
	
}
