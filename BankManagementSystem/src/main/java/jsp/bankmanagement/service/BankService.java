package jsp.bankmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.ResponseStructure;
import jsp.bankmanagement.exception.IdNotFoundException;
import jsp.bankmanagement.exception.LengthExceedException;
import jsp.bankmanagement.exception.ResourceNotFoundException;
import jsp.bankmanagement.repository.BankRepository;

@Service
public class BankService {
	@Autowired
	private BankRepository bankRepository;
	
	//Create Bank
	public ResponseStructure<Bank> createBank(Bank bank){
		ResponseStructure<Bank> res=new ResponseStructure<>();
		
		 if (bank.getAddress() == null) {
		        throw new ResourceNotFoundException("Address Must Be Passed To Save Bank Record");
		    }
		 
		if(String.valueOf(bank.getContact()).length()==10 && String.valueOf(bank.getAddress().getPincode()).length()==6) {
			if(bank.getAddress()!=null) {
				res.setStatusCode(HttpStatus.CREATED.value());
				res.setMessage("Bank Record Created");
				res.setData(bankRepository.save(bank));
				return res;
			}
			else
				throw new ResourceNotFoundException("Address Must Be Passed To Save Bank Record");
		}
		else
			throw new LengthExceedException("Invalid input: Contact number must be 10 digits and pincode must be 6 digits");
	}
	
	//Get All Bank
	public ResponseStructure<List<Bank>> getAllBank(){
		ResponseStructure<List<Bank>> res=new ResponseStructure<>();
		List<Bank> banks=bankRepository.findAll();
		if(!banks.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage(banks.size()+" Bank Record Found");
			res.setData(banks);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Bank Record Found");
	}
	
	//Get Bank By Id
	public ResponseStructure<Bank> getBankById(Integer id) {
		ResponseStructure<Bank> res= new ResponseStructure<>();
		Optional<Bank> opt=bankRepository.findById(id);
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank record Found");
			res.setData(opt.get());
			return res;
		}
		else
			throw new IdNotFoundException("Bank Id "+id+" does not Exist");
	}
	
	//Delete Bank
	public ResponseStructure<String> deleteBank(Integer id){
		ResponseStructure<String> res=new ResponseStructure<>();
		Optional<Bank> opt=bankRepository.findById(id);
		if(opt.isPresent()) {
			if(opt.get().getAccounts()!=null) {
				bankRepository.deleteById(id);
				res.setStatusCode(HttpStatus.OK.value());
				res.setMessage("Bank Record Deleted");
				res.setData("Deleted");
				return res;
			}
			else
				throw new ResourceNotFoundException("Account Must be needed to Delete Bank");
		}
		else
			throw new ResourceNotFoundException("No Bank Record Found To delete with Id "+id);
	}
	
	//Get Bank by Pagination
	public ResponseStructure<Page<Bank>> getBankByPagination(int pageNumber, int pageSize){
		ResponseStructure<Page<Bank>> res=new ResponseStructure<>();
		Page<Bank> pages=bankRepository.findAll(PageRequest.of(pageNumber, pageSize));
		if(!pages.isEmpty()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Bank Record Found In Pages");
			res.setData(pages);
			return res;
		}
		else
			throw new ResourceNotFoundException("No record Found"); 
	}
	
	//Get Bank By Ifsc
	public ResponseStructure<Bank> getBankByIfse(String ifsc) {
		ResponseStructure<Bank> res=new ResponseStructure<>();
		Optional<Bank> opt=bankRepository.findByIfscCode(ifsc);
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank Record Found with Ifsc "+ifsc);
			res.setData(opt.get());
			return res;
		}
		else
			throw new ResourceNotFoundException("No Bank Record Found with Ifsc "+ifsc);
	}
	
	//Get Bank By Address
	public ResponseStructure<Bank> getBankByAddress(Address address){
		ResponseStructure<Bank> res=new ResponseStructure<>();
		Optional<Bank> opt =bankRepository.findByAddress(address);
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank Record Found");
			res.setData(opt.get());
			return res;
		}
		else
			throw new ResourceNotFoundException("No Bank Record Available");
	}

	public ResponseStructure<List<Bank>> getBankByCity(String city) {
		ResponseStructure<List<Bank>> res=new ResponseStructure<>();
		List<Bank> banks=bankRepository.getBankByCity(city);
		if(!banks.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage(banks.size()+" Bank Found in "+city);
			res.setData(banks);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Bank Record Available in "+city);
	}

	public ResponseStructure<Bank> getBankByContactNumber(Long contact) {
		ResponseStructure<Bank> res= new ResponseStructure<>();
		Optional<Bank> opt=bankRepository.findByContact(contact);
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Bank Record Found");
			res.setData(opt.get());
			return res;
		}
		else
			throw new ResourceNotFoundException("No Bank Record Available");
	}
}
