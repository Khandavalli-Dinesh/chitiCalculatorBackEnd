package com.galvan.chiti.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galvan.chiti.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	Optional<Candidate> findByName(String name);

}
