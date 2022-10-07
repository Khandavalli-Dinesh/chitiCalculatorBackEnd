package com.galvan.chiti.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Chiti {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String name;
	@NotNull
	private int noOfMonths;
	@NotNull
	private int amount;
	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private LocalDate startDate;
	
	@ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private List<Candidate> candidateList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNoOfMonths() {
		return noOfMonths;
	}
	public void setNoOfMonths(int noOfMonths) {
		this.noOfMonths = noOfMonths;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return startDate;
	}
	public void setDate(LocalDate date) {
		this.startDate = date;
	}
	public List<Candidate> getCandidateList() {
		return candidateList;
	}
	public void setCandidateList(List<Candidate> candidateList) {
		this.candidateList = candidateList;
	}
	
	public Chiti(int id, String name, int noOfMonths, int amount, LocalDate date, List<Candidate> candidateList) {
		super();
		this.id = id;
		this.name = name;
		this.noOfMonths = noOfMonths;
		this.amount = amount;
		this.startDate = date;
		this.candidateList = candidateList;
	}
	public Chiti() {
		super();
	}
}
