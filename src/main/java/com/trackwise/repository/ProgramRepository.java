package com.trackwise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackwise.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
	Optional<Program> findByName(String name);
}
