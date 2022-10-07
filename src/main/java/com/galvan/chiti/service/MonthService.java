package com.galvan.chiti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.entity.Month;

@Service
public interface MonthService {
	
	public Month addMonth(Month month) throws NoPrivilageException,Exception;
	public Month updateMonth(Month month) throws NoPrivilageException,Exception;
	public Month removeMonth(int id) throws NoPrivilageException,Exception;
	public Month findMonthById(int id) throws NoPrivilageException,Exception;
	public List<Month> getAllMonth() throws NoPrivilageException, Exception;
	public List<Month> getAllMonthByChiti(int id) throws NoPrivilageException, Exception;

}
