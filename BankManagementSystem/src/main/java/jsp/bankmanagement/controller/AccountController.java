package jsp.bankmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.bankmanagement.entity.Account;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.AccountType;
import jsp.bankmanagement.entity.dto.ResponseStructure;
import jsp.bankmanagement.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Account>> createAccount(@RequestBody Account account){
		return new ResponseEntity<ResponseStructure<Account>>(accountService.createAccount(account), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Account>>> getAllAccount(){
		return new ResponseEntity<ResponseStructure<List<Account>>>(accountService.getAllAccount(),HttpStatus.FOUND);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Account>> getAccountById(@PathVariable Integer id){
		return new ResponseEntity<ResponseStructure<Account>>(accountService.getAccountById(id), HttpStatus.FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteAccount(@PathVariable Integer id){
		return new ResponseEntity<ResponseStructure<String>>(accountService.deleteAccount(id), HttpStatus.OK);
	}
	
	@PatchMapping("/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> depositAmount(@PathVariable Long accountNumber ,@PathVariable Double amount){
		return new ResponseEntity<ResponseStructure<Account>>(accountService.depositAmount(accountNumber, amount), HttpStatus.OK);
	}
	
	@PatchMapping("/withdraw/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> withdrawAmount(@PathVariable Long accountNumber ,@PathVariable Double amount){
		return new ResponseEntity<ResponseStructure<Account>>(accountService.withdrawAmount(accountNumber, amount), HttpStatus.OK);
	}
	
	@PatchMapping("/transfer/{sender}/{reciever}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> transferAmount(@PathVariable Long sender, @PathVariable Long reciever, @PathVariable Double amount){
		return new ResponseEntity<ResponseStructure<Account>>(accountService.transferAmount(sender, reciever, amount), HttpStatus.OK);
	}
	
	@GetMapping("/bank")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByBank(@RequestBody Bank bank){
		return new ResponseEntity<ResponseStructure<List<Account>>>(accountService.getAccountByBank(bank), HttpStatus.FOUND);
	}
	
	@GetMapping("/accounttype/{accountType}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByType(@PathVariable AccountType accountType){
		return new ResponseEntity<ResponseStructure<List<Account>>>(accountService.getAccountByType(accountType), HttpStatus.FOUND);
	}
	
	@GetMapping("/greater/{balance}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountWithBalanceGreaterThan(@PathVariable Double balance){
		return new ResponseEntity<ResponseStructure<List<Account>>>(accountService.getAccountWithBalanceGreaterThan(balance), HttpStatus.FOUND);
	}
	
	@GetMapping("/sort/{fieldName}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountsSorted(@PathVariable String fieldName){
		return new ResponseEntity<ResponseStructure<List<Account>>>(accountService.getAccountsSorted(fieldName), HttpStatus.OK);
	}
}
