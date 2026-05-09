package com.parking.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.parking.api.dto.BayResponseDTO;
import com.parking.api.entity.Bay;
import com.parking.api.entity.Status;
import com.parking.api.exception.BusinessException;
import com.parking.api.exception.ResourceNotFoundException;
import com.parking.api.repository.BayRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BayService {

	private final BayRepository bayRepository;

	@Transactional
	public List<BayResponseDTO> createBays(Long howManyBays) {
		List<Bay> bays = new ArrayList<>();
		for (int i = 0; i < howManyBays; i++) {
			var bay = Bay.builder()
			.status(Status.FREE)
			.build();

			bays.add(bayRepository.save(bay));
		}
		return bays.stream()
				.map(BayResponseDTO::from)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public BayResponseDTO checkIn(Long bayId) {
		var bay = bayRepository.findById(bayId)
				.orElseThrow(() -> new ResourceNotFoundException("Bay not found"));
		
		if(bay.getStatus() == Status.OCCUPIED)
			throw new BusinessException("Bay's already occuped");
		
		bay.setStatus(Status.OCCUPIED);
		return BayResponseDTO.from(bay);
	}
	
	@Transactional
	public BayResponseDTO checkOut(Long bayId) {
		var bay = bayRepository.findById(bayId)
				.orElseThrow(() -> new ResourceNotFoundException("Bay not found"));
		
		if(bay.getStatus() == Status.FREE)
			throw new BusinessException("Bay's already free");
		
		bay.setStatus(Status.FREE);
		return BayResponseDTO.from(bay);
	}
	
	public List<BayResponseDTO> showAvailableBays(){
		return bayRepository.findAvailableBays(Status.FREE).stream()
				.map(BayResponseDTO::from)
				.collect(Collectors.toList());
	}
	
	public List<BayResponseDTO> showOccupiedBays(){
		return bayRepository.findAvailableBays(Status.OCCUPIED).stream()
				.map(BayResponseDTO::from)
				.collect(Collectors.toList());
	}

}
