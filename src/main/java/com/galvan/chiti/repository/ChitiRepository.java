package com.galvan.chiti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.galvan.chiti.entity.Chiti;

@Repository
public interface ChitiRepository extends JpaRepository<Chiti, Integer> {
	
	@Query("Select c from Chiti c inner join c.candidateList o where o.name =:candidateName")
	List<Chiti> findByCandidateName(@Param("candidateName") String name);

	@Query("Select c from Chiti c inner join c.candidateList o where o.id =:candidateId")
	List<Chiti> findByCandidateId(@Param("candidateId") int id);

}
