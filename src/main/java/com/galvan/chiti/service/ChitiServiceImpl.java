package com.galvan.chiti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserCheck;
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.entity.Chiti;
import com.galvan.chiti.entity.Month;
import com.galvan.chiti.repository.ChitiRepository;

@Service
public class ChitiServiceImpl implements ChitiService {

	@Autowired
	ChitiRepository chitiRepo;
	@Autowired
	CandidateService candidateService;
	@Autowired 
	MonthService monthService;
	
	private List<Candidate> modifyCandidates(List<Candidate> candidates){
		candidates = candidates.stream().filter(c-> {
			try {
				return !candidateService.findCandidateById(c.getId()).equals(null);
			} catch (Exception e) {
				return false;
			}
		}).collect(Collectors.toList());
		Candidate candidate= null;
		for(int i=0; i< candidates.size(); i++) {
			try {
				candidate = candidateService.findCandidateById(candidates.get(i).getId());
				candidates.set(i, candidate);
			} catch (Exception e) {
				
			}
			
		}
		return candidates;
	}

	private boolean checkChitiForCandidate(Chiti chiti, int candidateId) {
		if(UserCheck.role.equals("ADMIN")) {
			return true;
		}
		for(Candidate candidate: chiti.getCandidateList()) {
			if(candidate.getId() == candidateId) {
				return true;
			}
		}
		return false;
	}
	
//	private boolean checkChitiForCandidate(List<Chiti> chitis, int candidateId) {
//		int count = 0;
//		if(UserCheck.role.equals("ADMIN")) {
//			return true;
//		}
//		for(Chiti chiti: chitis ) {
//			if(checkChitiForCandidate(chiti,candidateId)) {
//				count++;
//			}
//		}
//		return count == chitis.size();
//	}
	
	@Override
	public Chiti findChitiById(int id) throws NoPrivilageException,Exception {
		if(!UserCheck.isLoggedIn) {
			throw new Exception("User not logged in!!!");
		}
		Optional<Chiti> chitiOptional = chitiRepo.findById(id);
		Chiti chiti = chitiOptional.get();
		if(chiti == null) {
			return null;
		}else if(!checkChitiForCandidate(chiti,UserCheck.candidateId)) {
			throw new NoPrivilageException();
		}else {
			return chiti;
		}
	}
	
	@Override
	public Chiti addChiti(Chiti chiti) throws NoPrivilageException,Exception{
		UserCheck.checkPrivilage();
		chiti.setCandidateList(modifyCandidates(chiti.getCandidateList()));
		chitiRepo.saveAndFlush(chiti);
		return chiti;		
	}

	@Override
	public Chiti updateChiti(Chiti chiti) throws NoPrivilageException,Exception {
		UserCheck.checkPrivilage();
		Optional<Chiti> oldChiti = chitiRepo.findById(chiti.getId());
		if(oldChiti.get() == null) {
			throw new Exception("chiti not found");
		}
		chiti.setCandidateList(modifyCandidates(chiti.getCandidateList()));
		chitiRepo.saveAndFlush(chiti);
		return chiti;
	}

	@Override
	public Chiti removeChiti(int id) throws NoPrivilageException,Exception {
		UserCheck.checkPrivilage();
		Optional<Chiti> oldChiti = chitiRepo.findById(id);
		if(oldChiti.get() == null) {
			throw new Exception("chiti not found");
		}
		oldChiti.get().setCandidateList(new ArrayList<Candidate>());
		chitiRepo.saveAndFlush(oldChiti.get());
		List<Month> months = monthService.getAllMonthByChiti(id);
		for(Month month: months) {
			monthService.removeMonth(month.getId());
		}
		chitiRepo.deleteById(id);
		return oldChiti.get();		
	}

	@Override
	public List<Chiti> getAllChiti() throws NoPrivilageException,Exception{
		UserCheck.checkPrivilage();
		List<Chiti> chitis = chitiRepo.findAll();
		return chitis;	
	}
	
	@Override
	public List<Chiti> getAllChitiByCandidateName(String name) throws NoPrivilageException,Exception{
		if(UserCheck.isLoggedIn) {
			boolean check = false;
			if(UserCheck.role.equals("ADMIN")) {
				check = true;
			}else {
				Candidate candidate = candidateService.findCandidateById(UserCheck.candidateId);
				if(candidate.getName().equals(name)) {
					check = true;
				}
			}
			if(check) {
				List<Chiti> chitis = chitiRepo.findByCandidateName(name);
				return chitis;
			}else {
				throw new NoPrivilageException();
			}
			
		}else {
			throw new Exception("user not logged in!!!");
		}
		
	}
	
	@Override
	public List<Chiti> getAllChitiByCandidateId(int id) throws NoPrivilageException, Exception{
		List<Chiti> chitis = chitiRepo.findByCandidateId(id);
		if(UserCheck.isLoggedIn) {
			if(UserCheck.role.equals("ADMIN")) {
				return chitis;
			}else {
				if(id == UserCheck.candidateId) {
					return chitis;
				}
				throw new NoPrivilageException();
			}
			
		}else {
			throw new Exception("User not logged in!!!");
		}
	}

}
