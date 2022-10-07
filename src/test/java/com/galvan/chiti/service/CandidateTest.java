package com.galvan.chiti.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserCheck;
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.repository.CandidateRepository;

@SpringBootTest
class CandidateTest {
	
	@Autowired
	CandidateService candidateService;
	
	@MockBean
	CandidateRepository candidateRepo;

	Candidate candidate = new Candidate(1,"Dummy","9010238992");
	
	Candidate saveCandidate(Candidate candi , String method) throws Exception{
		
		Mockito.when(candidateRepo.findById(1)).thenReturn(Optional.of(candidate));
		
		if(candi.getName() == null || candi.getPhoneNumber() == null) {
			throw new Exception("value cannot be null");
		}
//			else if(candi.getPhoneNumber().length() != 10){
//			throw new Exception("phone number must be 10 digits");
//		}
		else if(method.equals("add")){
			return candidateService.addCandidate(candi);
		}else {
			return candidateService.updateCandidate(candi);
		}
	}
	
	@Test
	void nullName() {
		Candidate candi = candidate;
		candi.setName(null);
		
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		// admin adds/modifies a candidate with null name
		assertThrows(Exception.class,()->saveCandidate(candi,"add"));
		assertThrows(Exception.class,()->saveCandidate(candi,"update"));
		
	}
	
	@Test
	void invalidPhoneNumber() {
		Candidate candi = candidate;
		//phNo. is null
		candi.setPhoneNumber(null);
		
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		//adds/modifies a candidate with null phNo.
		assertThrows(Exception.class, ()->saveCandidate(candi,"add"));
		assertThrows(Exception.class,()->saveCandidate(candi,"update"));
		
		//phNo. is not 10 digits	
		candi.setPhoneNumber("54217845");
		
		//adds/modifies a candidate with  phNo. length not equals 10 digits
		assertThrows(Exception.class, ()->saveCandidate(candi,"add"));
		assertThrows(Exception.class,()->saveCandidate(candi,"update"));
		
	}
	
	@Test
	void noCandidate() {
		
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		Mockito.when(candidateRepo.findById(any(Integer.class))).thenReturn(null);
		Mockito.when(candidateRepo.findByName(any(String.class))).thenReturn(null);
		
		//admin tries to delete a non-existing user
		assertThrows(Exception.class, ()->candidateService.removeCandidate(1));
		
		//admin tries to find a non-existing user
		assertThrows(Exception.class, ()->candidateService.findCandidateById(1));
		assertThrows(Exception.class, ()->candidateService.findCandidateByName("DUMMY"));
		
	}
	
	@Test
	void userNotLoggedIn() {
		UserCheck.isLoggedIn = false;
		
		assertThrows(Exception.class, ()->candidateService.addCandidate(candidate));
		assertThrows(Exception.class, ()->candidateService.updateCandidate(candidate));
	}

	@Test
	void userNotADMIN() {
		UserCheck.isLoggedIn = true;
		UserCheck.role = "USERS";
		
		assertThrows(NoPrivilageException.class, ()->candidateService.addCandidate(candidate));
		assertThrows(NoPrivilageException.class, ()->candidateService.updateCandidate(candidate));
		assertThrows(NoPrivilageException.class, ()->candidateService.removeCandidate(1));
	}
}
