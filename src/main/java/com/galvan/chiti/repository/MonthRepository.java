package com.galvan.chiti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.galvan.chiti.entity.Month;

@Repository
public interface MonthRepository extends JpaRepository<Month, Integer> {
	
	@Query("Select m from Month m where m.chitiId.id =:cId")
	public List<Month> findMonthByChitiId(@Param("cId")int id);
}
