package com.galvan.chiti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserCheck;
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.repository.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService {
	
	@Autowired
	CandidateRepository candidateRepo;

	private void checkUser(int id) throws NoPrivilageException, Exception{
		if(UserCheck.isLoggedIn) {
			if(!(UserCheck.candidateId == id || UserCheck.role.equals("ADMIN"))) {
				throw new NoPrivilageException();
			}
		}else {
			throw new Exception("No user is logged in!!!");
		}
	}
	
	@Override
	public Candidate addCandidate(Candidate candidate) throws NoPrivilageException, Exception{
		checkUser(candidate.getId());
		if(!candidate.getPhoneNumber().matches("\\d{10}")) {
			throw new Exception("Phone number must be of 10 digits");
		}
		candidateRepo.saveAndFlush(candidate);
		return candidate;
	}

	@Override
	public Candidate updateCandidate(Candidate candidate) throws NoPrivilageException,Exception {
		checkUser(candidate.getId());
		Optional<Candidate> oldCandidate = candidateRepo.findById(candidate.getId());
		if(oldCandidate.get() == null) {
			throw new Exception("candidate not found");
		}
		if(!candidate.getPhoneNumber().matches("\\d{10}")) {
			throw new Exception("Phone number must be of 10 digits");
		}
		candidateRepo.saveAndFlush(candidate);
		return candidate;
	}

	@Override
	public Candidate removeCandidate(int id) throws NoPrivilageException,Exception {
		checkUser(id);
		Optional<Candidate> candidate = candidateRepo.findById(id);
		if(candidate.get() == null) {
			throw new Exception("candidate not found");
		}
		candidateRepo.deleteById(id);
		return candidate.get();
	}

	@Override
	public Candidate findCandidateById(int id) throws NoPrivilageException,Exception {
		checkUser(id);
		Optional<Candidate> candidate= candidateRepo.findById(id);
		if(candidate.get()==null) {
			throw new Exception("candidate not found");
		}
		return candidate.get();
	}
	
	@Override
	public Candidate findCandidateByName(String name) throws NoPrivilageException,Exception{
		UserCheck.checkPrivilage();
		Optional<Candidate> candidate = candidateRepo.findByName(name);
		if(candidate.get() == null) {
			throw new Exception("candidate not found");
		}
		return candidate.get();
	}

	@Override
	public List<Candidate> getAllCandidate() throws NoPrivilageException, Exception{
		UserCheck.checkPrivilage();
		List<Candidate> candidates = candidateRepo.findAll();
		return candidates;
	}

}
