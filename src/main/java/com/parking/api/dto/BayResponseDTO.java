package com.parking.api.dto;

import com.parking.api.entity.Bay;
import com.parking.api.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BayResponseDTO {

	private Long id;
	private Status status;
	
	public static BayResponseDTO from(Bay bay) {
		return BayResponseDTO.builder()
				.id(bay.getBayNumber())
				.status(bay.getStatus())
				.build();
	}
	
}
