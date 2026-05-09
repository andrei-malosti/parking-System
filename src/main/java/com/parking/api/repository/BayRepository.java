package com.parking.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parking.api.entity.Bay;
import com.parking.api.entity.Status;

@Repository
public interface BayRepository extends JpaRepository<Bay, Long>{
	
	@Query("SELECT b FROM Bay b WHERE b.status = :status")
	public List<Bay> findAvailableBays(@Param("status") Status status);

}
