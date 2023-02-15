package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Medicine;
import com.example.demo.repository.MedicineRepository;

@RestController
public class MedicineController {
	@Autowired
	private MedicineRepository medicineRepository;
	
	@GetMapping("/med")
	public List<Medicine> getAllMedicine(){
		return medicineRepository.findAll();
	}
	
	@PostMapping("/med")
	public Medicine createMedicine(@RequestBody Medicine medicine){
		return medicineRepository.save(medicine);
	}
	
	@GetMapping("/med/{id}")
	public ResponseEntity<Medicine> getMedicneById(@PathVariable(value = "id") int medicineId) throws ResourceNotFoundException{
		Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() -> new ResourceNotFoundException("Student not found for this Id : "+medicineId));
		return ResponseEntity.ok().body(medicine);
	}
	
	@PutMapping("/med/{id}")
	public ResponseEntity<Medicine> updateMedicine(@PathVariable(value="id") int medicineId,@RequestBody Medicine medicineDetails) throws ResourceNotFoundException{
		Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() -> new ResourceNotFoundException("Student not found for this Id : "+medicineId));
		medicine.setName(medicineDetails.getName());
		medicine.setGrp(medicineDetails.getGrp());
		medicine.setPrice(medicineDetails.getPrice());
		medicineRepository.save(medicine);
		return ResponseEntity.ok().body(medicine);
	}
	
	@DeleteMapping("/med/{id}")
	public ResponseEntity<?> deleteMedicine(@PathVariable(value="id")int medicineId) throws ResourceNotFoundException{
		Medicine medicine = medicineRepository.findById(medicineId).orElseThrow(() -> new ResourceNotFoundException("Student not found for this Id : "+medicineId));
		medicineRepository.deleteById(medicineId);
		return ResponseEntity.ok().build();
	}
}
