package jsp.bankmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.ResponseStructure;
import jsp.bankmanagement.exception.IdNotFoundException;
import jsp.bankmanagement.exception.ResourceNotFoundException;
import jsp.bankmanagement.repository.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	public ResponseStructure<Address> getAddressById(Integer id) {
		ResponseStructure<Address> res=new ResponseStructure<>();
		Optional<Address> opt=addressRepository.findById(id);
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Address Record Found");
			res.setData(opt.get());
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Avaialable");
	}
	
	//Update a Record
	public ResponseStructure<Address> updateAddress(Address address) {
		if(address.getAddressId()==null) {
			throw new IdNotFoundException("Id Must Be Paased To Update Th Record");
		}
		ResponseStructure<Address> res=new ResponseStructure<>();
		Optional<Address> opt=addressRepository.findById(address.getAddressId());
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Updated");
			res.setData(addressRepository.save(opt.get()));
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Avaialable to Be updated");
	}

	//Get Address by Bank
	public ResponseStructure<Address> getAddressByBank(Bank bank) {
		ResponseStructure<Address> res=new ResponseStructure<>();
		Optional<Address> opt=addressRepository.findByBank(bank);
		if(opt.isPresent()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Address Record Found");
			res.setData(opt.get());
			return res;
		}
		else
			throw new ResourceNotFoundException("No record Available");
	}

	public ResponseStructure<List<Address>> getAddressByCity(String city) {
		ResponseStructure<List<Address>> res=new ResponseStructure<>();
		List<Address> addresses=addressRepository.findByCity(city);
		if(!addresses.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Record Found");
			res.setData(addresses);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Avialable");
	}

	public ResponseStructure<List<Address>> getAddressByCityAndState(String city, String state) {
		ResponseStructure<List<Address>> res=new ResponseStructure<>();
		List<Address> addresses=addressRepository.findByCityAndState(city, state);
		if(!addresses.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage("Address Record Found");
			res.setData(addresses);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Record Avaialable");
	}

	public ResponseStructure<List<Address>> getAddressByPincode(Long pin) {
		ResponseStructure<List<Address>> res=new ResponseStructure<>();
		List<Address> addresses =addressRepository.findByPincode(pin);
		if(!addresses.isEmpty()) {
			res.setStatusCode(HttpStatus.FOUND.value());
			res.setMessage(addresses.size()+" Address Record Found");
			res.setData(addresses);
			return res;
		}
		else
			throw new ResourceNotFoundException("No Address Record Available");
	}
	
	

}
