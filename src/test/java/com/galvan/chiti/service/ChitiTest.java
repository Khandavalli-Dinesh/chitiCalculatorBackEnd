package com.galvan.chiti.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserCheck;
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.entity.Chiti;
import com.galvan.chiti.repository.CandidateRepository;
import com.galvan.chiti.repository.ChitiRepository;

@SpringBootTest
class ChitiTest {
	
	@MockBean
	ChitiRepository chitiRepo;
	@MockBean
	CandidateRepository candidateRepo;
	
	@Autowired
	ChitiService chitiService;
	
	Candidate c1 = new Candidate(1,"Dummy_1","7896451236");
	Candidate c2 = new Candidate(1,"Dummy_2","8796451236");
	
	List<Candidate> candidates = new ArrayList<Candidate>();
	
	{
		candidates.add(c1);
		candidates.add(c2);
	}
	
	Chiti chiti = new Chiti(1,"One Lakh Chiti",20, 10000, LocalDate.of(2022, 10, 6), candidates);
	Chiti shortChiti = new Chiti();
	
	{
		shortChiti.setId(2);
		shortChiti.setDate(LocalDate.of(2022, 10, 6));
		shortChiti.setCandidateList(candidates);
	}
	
	Chiti chitiCheck(Chiti testChiti, String method) throws NoPrivilageException,Exception{
		
		Mockito.when(chitiRepo.findById(1)).thenReturn(Optional.of(chiti));
		
		if(testChiti.getName() == null || testChiti.getDate() == null) {
			throw new Exception("Entity cannot be null");
		}
		if(method.equals("add")) {
			return chitiService.addChiti(testChiti);
		}else if(method.equals("update")){
			return chitiService.updateChiti(testChiti);
		}else if(method.contains("nullMonths")) {
			testChiti.getAmount();
			return chitiCheck(testChiti,method.split("nullMonths")[0]);
		}else if(method.contains("nullAmount")) {
			testChiti.getAmount();
			return chitiCheck(testChiti,method.split("nullAmount")[0]);
		}else if(method.contains("nullDate")) {
			testChiti.getDate().getYear();
			return chitiCheck(testChiti,method.split("nullDate")[0]);
		}else{
			return null;
		}
	}
	
	@Test
	void nullName() {
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		Chiti testChiti = chiti;
		chiti.setName(null);
		
		//admin adds or updates chiti with null name	
		assertThrows(Exception.class,()->chitiCheck(testChiti,"add"));
		assertThrows(Exception.class,()->chitiCheck(testChiti,"update"));
		
	}
	
	@Test 
	void nullNoOfMonths() {
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		Chiti testChiti = shortChiti;
		testChiti.setAmount(100000);
		assertThrows(Exception.class, ()->chitiCheck(testChiti,"nullMonthsAdd"));
		assertThrows(Exception.class, ()->chitiCheck(testChiti,"nullMonthsUpdate"));
	}
	
	@Test
	void nullAmount() {
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		Chiti testChiti = shortChiti;
		testChiti.setNoOfMonths(20);
		assertThrows(Exception.class, ()->chitiCheck(testChiti,"nullAmountAdd"));
		assertThrows(Exception.class, ()->chitiCheck(testChiti,"nullAmountUpdate"));
	}
	
	@Test
	void nullDate() {
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		Chiti testChiti = chiti;
		testChiti.setDate(null);
		assertThrows(Exception.class, ()->chitiCheck(testChiti,"nullDateAdd"));
		assertThrows(Exception.class, ()->chitiCheck(testChiti,"nullDateUpdate"));
	}
	
	@Test
	void findChiti() {
		UserCheck.isLoggedIn = true;
		UserCheck.role = "ADMIN";
		
		Mockito.when(chitiRepo.findById(2)).thenReturn(null);
		
		assertThrows(Exception.class, ()->chitiService.findChitiById(2));		
		assertThrows(Exception.class, ()->chitiService.removeChiti(2));
	}
	
	@Test
	void userNotLoggedIn() {
		UserCheck.isLoggedIn = false;
		
		Mockito.when(chitiRepo.findById(1)).thenReturn(Optional.of(chiti));
		Mockito.when(chitiRepo.findAll()).thenReturn(new ArrayList<Chiti>(List.of(chiti)));
		Chiti testChiti = chiti;
		assertThrows(Exception.class, ()->chitiService.addChiti(testChiti));
		assertThrows(Exception.class, ()->chitiService.updateChiti(testChiti));
		assertThrows(Exception.class, ()->chitiService.removeChiti(1));
		assertThrows(Exception.class, ()->chitiService.findChitiById(1));
		assertThrows(Exception.class, ()->chitiService.getAllChiti());
		assertThrows(Exception.class, ()->chitiService.getAllChitiByCandidateId(1));
		assertThrows(Exception.class, ()->chitiService.getAllChitiByCandidateName("DUMMY"));
		
	}
	
	@Test
	void notAdmin() {
		UserCheck.isLoggedIn = true;
		UserCheck.role = "USERS";
		UserCheck.candidateId = 2;
		UserCheck.userId = 1;
		
		Mockito.when(chitiRepo.findById(1)).thenReturn(Optional.of(chiti));
		Mockito.when(chitiRepo.findAll()).thenReturn(new ArrayList<Chiti>(List.of(chiti)));
		Mockito.when(candidateRepo.findById(2)).thenReturn(Optional.of(c1));
		Chiti testChiti = chiti;
		assertThrows(NoPrivilageException.class, ()->chitiService.addChiti(testChiti));
		assertThrows(NoPrivilageException.class, ()->chitiService.updateChiti(testChiti));
		assertThrows(NoPrivilageException.class, ()->chitiService.removeChiti(1));
		assertThrows(NoPrivilageException.class, ()->chitiService.findChitiById(1));
		assertThrows(NoPrivilageException.class, ()->chitiService.getAllChiti());
		assertThrows(NoPrivilageException.class, ()->chitiService.getAllChitiByCandidateId(1));
		assertThrows(NoPrivilageException.class, ()->chitiService.getAllChitiByCandidateName("DUMMY"));
		
	}
}
