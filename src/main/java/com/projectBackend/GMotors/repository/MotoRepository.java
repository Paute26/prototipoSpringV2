package com.projectBackend.GMotors.repository;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectBackend.GMotors.model.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
	
}