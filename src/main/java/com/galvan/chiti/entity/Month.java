package com.galvan.chiti.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Month {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String candidateName;
	@NotNull
	private int bid;
	private int bonus;
	private int amountGiven;
	private int amountPaid;
	
	@ManyToOne(targetEntity=com.galvan.chiti.entity.Chiti.class ,cascade=CascadeType.DETACH)
	@JoinColumn(name="chiti_id", referencedColumnName= "ID")
	private Chiti chitiId; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCandidateName() {
		return candidateName;
	}
	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public int getAmountGiven() {
		return amountGiven;
	}
	public void setAmountGiven(int amountGiven) {
		this.amountGiven = amountGiven;
	}
	public int getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(int amountPaid) {
		this.amountPaid = amountPaid;
	}
	public Chiti getChitiId() {
		return chitiId;
	}
	public void setChitiId(Chiti chitiId) {
		this.chitiId = chitiId;
	}
	

	public Month(int id, String candidateName, int bid, int bonus, int amountGiven, int amountPaid, Chiti chitiId) {
		super();
		this.id = id;
		this.candidateName = candidateName;
		this.bid = bid;
		this.bonus = bonus;
		this.amountGiven = amountGiven;
		this.amountPaid = amountPaid;
		this.chitiId = chitiId;
	}
	
	public Month() {
		super();
	}
	
	
	
	
}
