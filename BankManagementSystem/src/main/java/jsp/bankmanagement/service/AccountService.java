package jsp.bankmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jsp.bankmanagement.entity.Account;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.AccountType;
import jsp.bankmanagement.entity.dto.ResponseStructure;
import jsp.bankmanagement.exception.AccountBalanceExceedException;
import jsp.bankmanagement.exception.IdNotFoundException;
import jsp.bankmanagement.exception.ResourceNotFoundException;
import jsp.bankmanagement.repository.AccountRepository;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	
	public ResponseStructure<Account> createAccount(Account account){
		ResponseStructure<Account> res=new ResponseStructure<>();
		Optional<Bank> opt=accountRepository.findByBankId(account.getBank().getBankId());
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.CREATED.value());
			res.setMessage("Account Created");
			res.setData(accountRepository.save(account));
			return res;
		}
		else
			throw new ResourceNotFoundException("Bank Does Not Exist");
	}

	public ResponseStructure<List<Account>> getAllAccount() {
		ResponseStructure<List<Account>> res=new ResponseStructure<>();
		List<Account> accounts=accountRepository.findAll();
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage(accounts.size()+" Account Record Found");
			res.setData(accounts);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Found");
	}

	public ResponseStructure<Account> getAccountById(Integer id) {
		ResponseStructure<Account> res=new ResponseStructure<>();
		Optional<Account> opt=accountRepository.findById(id);
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Account Record Found");
			res.setData(opt.get());
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Found");
	}

	public ResponseStructure<String> deleteAccount(Integer id) {
		ResponseStructure<String> res=new ResponseStructure<>();
		Optional<Account> opt=accountRepository.findById(id);
		if(opt.isPresent()) {
			accountRepository.deleteById(id);
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Deleted");
			res.setData("Account Record Deleted");
			return res;
		}
		else
			throw new IdNotFoundException("Id Does not Exist");
	}

	public ResponseStructure<Account> depositAmount(Long accountNumber, Double amount) {
		ResponseStructure<Account> res=new ResponseStructure<>();
		Optional<Account> opt=accountRepository.findByAccountNumber(accountNumber);
		if(opt.isPresent()) {
			if(amount>0) {
					opt.get().setBalance(opt.get().getBalance()+amount);
					res.setStatusCode(HttpStatus.OK.value());
					res.setMessage("Amount Deposited");
					res.setData(accountRepository.save(opt.get()));
					return res;
			}
			else
				throw new ResourceNotFoundException("Transaction Failed");
		}
		else
			throw new ResourceNotFoundException("Account Does not Exist");
			
	}

	public ResponseStructure<Account> withdrawAmount(Long accountNumber, Double amount) {
		ResponseStructure<Account> res=new ResponseStructure<>();
		Optional<Account> opt=accountRepository.findByAccountNumber(accountNumber);
		if(opt.isPresent()) {
			if(opt.get().getBalance()>=amount && amount>0) {
				opt.get().setBalance(opt.get().getBalance()-amount);
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Amount Deposited");
				res.setData(accountRepository.save(opt.get()));
				return res;
			}
			else
				throw new AccountBalanceExceedException("Transaction Failed");
		}
		else
			throw new ResourceNotFoundException("Account Does not Exist");
	}

	public ResponseStructure<Account> transferAmount(Long sender, Long receiver, Double amount) {
		ResponseStructure<Account> res=new ResponseStructure<>();
		Optional<Account> opt1=accountRepository.findByAccountNumber(sender);
		Optional<Account> opt2=accountRepository.findByAccountNumber(receiver);
		if(opt1.isPresent() && opt2.isPresent() && opt1.get()!=opt2.get()) {
			if(opt1.get().getBalance()>=amount && amount>0) {
				opt1.get().setBalance(opt1.get().getBalance()-amount);
				opt2.get().setBalance(opt2.get().getBalance()+amount);
				accountRepository.save(opt2.get());
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Amount Transferrd");
				res.setData(accountRepository.save(opt1.get()));
				return res;
			}
			else
				throw new AccountBalanceExceedException("Insufficient Balance");
		}
		else
			throw new ResourceNotFoundException("Account Does not Exist || Invalid Receiver");
	}

	public ResponseStructure<List<Account>> getAccountByBank(Bank bank) {
		ResponseStructure<List<Account>> res=new ResponseStructure<>();
		List<Account> accounts=accountRepository.findByBank(bank);
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage(accounts.size()+" Account Record Found");
			res.setData(accounts);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Avaialable");
	}

	public ResponseStructure<List<Account>> getAccountByType(AccountType accountType) {
		ResponseStructure<List<Account>> res=new ResponseStructure<>();
		List<Account> accounts=accountRepository.findByAccountType(accountType);
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage(accounts.size()+" Account Record Found");
			res.setData(accounts);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Found");
	}

	public ResponseStructure<List<Account>> getAccountWithBalanceGreaterThan(Double balance) {
		ResponseStructure<List<Account>> res=new ResponseStructure<>();
		List<Account> accounts=accountRepository.findByBalanceGreaterThan(balance);
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage(accounts.size()+" Account Record Found");
			res.setData(accounts);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Found");
	}

	public ResponseStructure<List<Account>> getAccountsSorted(String fieldName) {
		ResponseStructure<List<Account>> res=new ResponseStructure<>();
		List<Account> accounts=accountRepository.findAll(Sort.by(fieldName).ascending());
		if(!accounts.isEmpty()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Accounts retrievd in Ascending Order");
			res.setData(accounts);
			return res;
		}
		else
			throw new ResourceNotFoundException("No record available");
	}
	
	
}

