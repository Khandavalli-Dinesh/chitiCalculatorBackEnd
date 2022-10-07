package com.galvan.chiti.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.auxilliaries.UserCheck;
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.entity.Chiti;
import com.galvan.chiti.entity.Month;
import com.galvan.chiti.repository.MonthRepository;

@Service
public class MonthServiceImpl implements MonthService {
	
	@Autowired
	MonthRepository monthRepo;
	
	@Autowired @Lazy
	ChitiService chitiService;

	public Month calculateFields(Month month) throws Exception {
		Chiti chiti = chitiService.findChitiById(month.getChitiId().getId());
		if(chiti.equals(null)) {
			throw new Exception("No chiti found for specified id");
		}
		int comission = (int) (chiti.getAmount() * 0.04);
		int noOfMonths = chiti.getNoOfMonths();
		month.setChitiId(chiti);
		month.setBonus((month.getBid() - comission)/noOfMonths);
		month.setAmountPaid(chiti.getAmount()/noOfMonths - month.getBonus());
		month.setAmountGiven(chiti.getAmount() - month.getBid() - month.getAmountPaid());
		return month;
	}
	
	@Override
	public Month addMonth(Month month) throws NoPrivilageException,Exception {
		UserCheck.checkPrivilage();
		Month modifiedMonth = calculateFields(month);
		monthRepo.saveAndFlush(modifiedMonth);
		return modifiedMonth;
	}

	@Override
	public Month updateMonth(Month month) throws NoPrivilageException,Exception {
		UserCheck.checkPrivilage();
		Optional<Month> oldMonth = monthRepo.findById(month.getId());
		if(oldMonth.get() == null) {
			throw new Exception("month not found");
		}
		Month newMonth = calculateFields(month);
		monthRepo.saveAndFlush(newMonth);
		return newMonth;
	}

	@Override
	public Month removeMonth(int id) throws NoPrivilageException,Exception {
		UserCheck.checkPrivilage();
		Optional<Month> month = monthRepo.findById(id);
		if(month.get() == null) {
			throw new Exception("month not found");
		}
		month.get().setChitiId(null);
		monthRepo.deleteById(id);
		return month.get();
	}

	@Override
	public Month findMonthById(int id) throws NoPrivilageException,Exception {
		Month month = monthRepo.findById(id).get();
		if(month == null) {
			throw new Exception("Month doesnot exist");
		}
		if(UserCheck.isLoggedIn) {
			if(UserCheck.role.equals("ADMIN")) {
				
				return month;
			}else {
				for(Candidate candidate: month.getChitiId().getCandidateList()) {
					if(candidate.getId() == UserCheck.candidateId) {
						return month;
					}
				}
				throw new NoPrivilageException();
			}
		}
		throw new Exception("User not logged in!!!");
//		Optional<Month> month = monthRepo.findById(id);
//		if(month.get() == null) {
//			throw new Exception("month not found");
//		}
//		return month.get();
	}

	@Override
	public List<Month> getAllMonth() throws NoPrivilageException,Exception{
		UserCheck.checkPrivilage();
		List<Month> months = monthRepo.findAll();
		return months;
	}

	@Override
	public List<Month> getAllMonthByChiti(int id) throws NoPrivilageException,Exception {
		List<Month> months = monthRepo.findMonthByChitiId(id);
		Chiti chiti = chitiService.findChitiById(id);
		boolean candidateCheck = chiti.getCandidateList().stream().map((candidate)->candidate.getId()).collect(Collectors.toList()).contains(UserCheck.candidateId);
		if(UserCheck.isLoggedIn) {
			if(UserCheck.role.equals("ADMIN")) {
				return months;
			}else {
				if(candidateCheck) {
					return months;
				}else {
					throw new NoPrivilageException();
				}
			}
		}
		throw new Exception("No user is logged in!!!");
	}

}
