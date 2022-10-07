package com.galvan.chiti.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galvan.chiti.auxilliaries.NoPrivilageException;
import com.galvan.chiti.entity.Month;
import com.galvan.chiti.service.MonthService;

@RestController
@CrossOrigin
@RequestMapping(value="/")
public class MonthController {

	@Autowired
	MonthService monthService;
	
	@PostMapping(value="/month")
	public ResponseEntity<Month> addMonth(@Valid @RequestBody Month chiti) throws NoPrivilageException,Exception{
		Month chitiNew = monthService.addMonth(chiti);
		return new ResponseEntity<Month>(chitiNew, HttpStatus.OK);
	}
	
	@GetMapping(value="/month/{id}")
	public ResponseEntity<Month> viewMonth(@PathVariable("id") Integer id) throws NoPrivilageException,Exception{
		Month chitiNew = monthService.findMonthById(id);
		return new ResponseEntity<Month>(chitiNew, HttpStatus.OK);
	}
	
	@PutMapping(value="/month")
	public ResponseEntity<Month> updateMonth(@Valid @RequestBody Month chiti) throws NoPrivilageException,Exception{
		Month chitiNew = monthService.updateMonth(chiti);
		return new ResponseEntity<Month>(chitiNew, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/month/{id}")
	public ResponseEntity<Month> deleteMonth(@PathVariable("id") Integer id) throws NoPrivilageException,Exception{
		Month chitiNew = monthService.removeMonth(id);
		return new ResponseEntity<Month>(chitiNew, HttpStatus.OK);
	}
	
	@GetMapping(value="/months")
	public ResponseEntity<List<Month>> getAllMonth() throws NoPrivilageException, Exception{
		List<Month> months = monthService.getAllMonth();
		return new ResponseEntity<List<Month>>(months, HttpStatus.OK);
	}
	
	@GetMapping(value="/chiti/months/{id}")
	public ResponseEntity<List<Month>> getAllMonthByChiti(@PathVariable("id") int id) throws NoPrivilageException, Exception{
		List<Month> months = monthService.getAllMonthByChiti(id);
		return new ResponseEntity<List<Month>>(months, HttpStatus.OK);
	}
	
}
