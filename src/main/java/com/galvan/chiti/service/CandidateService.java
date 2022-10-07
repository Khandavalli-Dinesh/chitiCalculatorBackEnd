package com.galvan.chiti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.entity.Candidate;

@Service
public interface CandidateService {
	
	public Candidate addCandidate(Candidate candidate) throws NoPrivilageException, Exception;
	public Candidate updateCandidate(Candidate candidate) throws NoPrivilageException, Exception;
	public Candidate removeCandidate(int id) throws NoPrivilageException, Exception;
	public Candidate findCandidateById(int id) throws NoPrivilageException, Exception;	
	public List<Candidate> getAllCandidate() throws NoPrivilageException, Exception;
	public Candidate findCandidateByName(String name) throws NoPrivilageException, Exception;

}
