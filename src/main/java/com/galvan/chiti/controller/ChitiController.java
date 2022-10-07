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
import com.galvan.chiti.entity.Chiti;
import com.galvan.chiti.service.ChitiService;


@RestController
@CrossOrigin
@RequestMapping("/")
public class ChitiController {

	@Autowired
	ChitiService chitiService;
	
	@PostMapping("/chiti")
	public ResponseEntity<Chiti> addChiti(@Valid @RequestBody Chiti chiti) throws NoPrivilageException,Exception{
		Chiti chitiNew = chitiService.addChiti(chiti);
		return new ResponseEntity<Chiti>(chitiNew, HttpStatus.OK);
	}
	
	@GetMapping("/chiti/{id}")
	public ResponseEntity<Chiti> viewChiti(@PathVariable("id") Integer id) throws NoPrivilageException,Exception{
		Chiti chitiNew = chitiService.findChitiById(id);
		return new ResponseEntity<Chiti>(chitiNew, HttpStatus.OK);
	}
	
	@PutMapping("/chiti")
	public ResponseEntity<Chiti> updateChiti(@Valid @RequestBody Chiti chiti) throws NoPrivilageException,Exception{
		Chiti chitiNew = chitiService.updateChiti(chiti);
		return new ResponseEntity<Chiti>(chitiNew, HttpStatus.OK);
	}
	
	@DeleteMapping("/chiti/{id}")
	public ResponseEntity<Chiti> deleteChiti(@PathVariable("id") Integer id) throws NoPrivilageException,Exception{
		Chiti chitiNew = chitiService.removeChiti(id);
		return new ResponseEntity<Chiti>(chitiNew, HttpStatus.OK);
	}
	
	@GetMapping("/chitis")
	public ResponseEntity<List<Chiti>> getAllChiti() throws NoPrivilageException,Exception{
		List<Chiti> chitis = chitiService.getAllChiti();
		return new ResponseEntity<List<Chiti>>(chitis, HttpStatus.OK);
	}
	
	@GetMapping("/chitis/{name}")
	public ResponseEntity<List<Chiti>> getAllChitiByName(@PathVariable("name") String name) throws NoPrivilageException,Exception{
		List<Chiti> chitis = chitiService.getAllChitiByCandidateName(name);
		return new ResponseEntity<List<Chiti>>(chitis, HttpStatus.OK);
	}
	
	@GetMapping("/chitis/candidate/{id}")
	public ResponseEntity<List<Chiti>> getAllChitiByCandidateId(@PathVariable("id") int id) throws NoPrivilageException,Exception{
		List<Chiti> chitis = chitiService.getAllChitiByCandidateId(id);
		return new ResponseEntity<List<Chiti>>(chitis,HttpStatus.OK);
	}
}
