package com.galvan.chiti.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	public enum Type{
		ADMIN, USERS
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotNull
	private String uname;
	@NotNull
	@Size(min=5)
	private String password;
	
	@OneToOne
	private Candidate candidate;
	
	@Enumerated(EnumType.STRING)
	private Type role;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Candidate getCandidateId() {
		return candidate;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidate = candidateId;
	}

	public Type getRole() {
		return role;
	}

	public void setRole(Type role) {
		this.role = role;
	}

	public User(int id, String uname, String password, Candidate candidateId, String  role) {
		super();
		this.id = id;
		this.uname = uname;
		this.password = password;
		this.candidate = candidateId;
		this.role = Type.valueOf(role);
	}

	public User() {
		super();
	}
	
	
	
}
