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
import com.galvan.chiti.entity.Candidate;
import com.galvan.chiti.service.CandidateService;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CandidateController {

	@Autowired
	CandidateService candidateService;
	
	@PostMapping("/candidate")
	public ResponseEntity<Candidate> addCandidate(@Valid @RequestBody Candidate candidate) throws NoPrivilageException, Exception{
		Candidate candidateNew = candidateService.addCandidate(candidate);
		return new ResponseEntity<Candidate>(candidateNew, HttpStatus.OK);
	}
	
	@GetMapping("/candidate/{id}")
	public ResponseEntity<Candidate> viewCandidate(@PathVariable("id") Integer id) throws NoPrivilageException, Exception{
		Candidate candidateNew = candidateService.findCandidateById(id);
		return new ResponseEntity<Candidate>(candidateNew, HttpStatus.OK);
	}
	
	@PutMapping("/candidate")
	public ResponseEntity<Candidate> updateCandidate(@Valid @RequestBody Candidate candidate) throws NoPrivilageException, Exception{
		Candidate candidateNew = candidateService.updateCandidate(candidate);
		return new ResponseEntity<Candidate>(candidateNew, HttpStatus.OK);
	}
	
	@DeleteMapping("/candidate/{id}")
	public ResponseEntity<Candidate> deleteCandidate(@PathVariable("id") Integer id) throws NoPrivilageException, Exception{
		Candidate candidateNew = candidateService.removeCandidate(id);
		return new ResponseEntity<Candidate>(candidateNew, HttpStatus.OK);
	}
	
	@GetMapping("/candidates")
	public ResponseEntity<List<Candidate>> getAllCandidate() throws NoPrivilageException, Exception{
		List<Candidate> chitis = candidateService.getAllCandidate();
		return new ResponseEntity<List<Candidate>>(chitis, HttpStatus.OK);
	}
}
