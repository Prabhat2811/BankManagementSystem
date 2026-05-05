package jsp.bankmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.ResponseStructure;
import jsp.bankmanagement.service.BankService;

@RestController
@RequestMapping("/api/bank")
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	//Create Bank
	@PostMapping
	public ResponseEntity<ResponseStructure<Bank>> createBank(@RequestBody Bank bank){
		return new ResponseEntity<ResponseStructure<Bank>>(bankService.createBank(bank),HttpStatus.CREATED);
	}
	
	//Fetch All Bank
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBank(){
		return new ResponseEntity<ResponseStructure<List<Bank>>>(bankService.getAllBank(),HttpStatus.FOUND);
	}
	
	//Fetch Bank By Id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bank>> getBankById(@PathVariable Integer id){
		return new ResponseEntity<ResponseStructure<Bank>>(bankService.getBankById(id), HttpStatus.FOUND);
	}
	
	//Delete Bank
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBank(@PathVariable Integer id){
		return new ResponseEntity<ResponseStructure<String>>(bankService.deleteBank(id),HttpStatus.OK);
	}
	
	//Fetch Bank in Pages
	@GetMapping("/{pageNumber}/{pageSize}")
	public ResponseEntity<ResponseStructure<Page<Bank>>> getBankInPages(@PathVariable int pageNumber, @PathVariable int pageSize){
		return new ResponseEntity<ResponseStructure<Page<Bank>>>(bankService.getBankByPagination(pageNumber, pageSize), HttpStatus.OK); 
	}
	
	//Fetch Bank By IFSC
	@GetMapping("/ifsc/{ifsc}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByIfse(@PathVariable String ifsc){
		return new ResponseEntity<ResponseStructure<Bank>>(bankService.getBankByIfse(ifsc), HttpStatus.FOUND);
	}
	
	//Fetch Bank By Address
	@GetMapping("/address")
	public ResponseEntity<ResponseStructure<Bank>> getBankByAddress(@RequestBody Address address){
		return new ResponseEntity<ResponseStructure<Bank>>(bankService.getBankByAddress(address), HttpStatus.FOUND);
	}
	
	//Fetch Bank By City
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Bank>>> getBankByCity(@PathVariable String city){
		return new ResponseEntity<ResponseStructure<List<Bank>>>(bankService.getBankByCity(city), HttpStatus.FOUND);
	}
	
	//Fetch Bank By Contact Number
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Bank>> getBankByContactNumber(@PathVariable Long contact){
		return new ResponseEntity<ResponseStructure<Bank>>(bankService.getBankByContactNumber(contact), HttpStatus.FOUND);
	}
}
