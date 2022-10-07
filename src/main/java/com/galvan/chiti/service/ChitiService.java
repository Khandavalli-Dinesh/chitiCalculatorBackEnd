package com.galvan.chiti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.entity.Chiti;

@Service
public interface ChitiService {
	
	public Chiti addChiti(Chiti chiti) throws NoPrivilageException,Exception;
	public Chiti updateChiti(Chiti chiti) throws NoPrivilageException,Exception;
	public Chiti removeChiti(int id) throws NoPrivilageException,Exception;
	public Chiti findChitiById(int id) throws NoPrivilageException,Exception;
	public List<Chiti> getAllChiti() throws NoPrivilageException,Exception;
	public List<Chiti> getAllChitiByCandidateName(String name) throws NoPrivilageException,Exception;
	List<Chiti> getAllChitiByCandidateId(int id) throws NoPrivilageException, Exception;

}
