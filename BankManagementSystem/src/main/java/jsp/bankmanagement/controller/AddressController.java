package jsp.bankmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.ResponseStructure;
import jsp.bankmanagement.service.AddressService;

@RestController
@RequestMapping("/api/address")
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	//Fetch Address Record By Id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Address>> getAddressById(@PathVariable Integer id){
		return new ResponseEntity<ResponseStructure<Address>>(addressService.getAddressById(id),HttpStatus.FOUND);
	}
	
	//update Address
	@PutMapping
	public ResponseEntity<ResponseStructure<Address>> updateAddress(@RequestBody Address address){
		return new ResponseEntity<ResponseStructure<Address>>(addressService.updateAddress(address),HttpStatus.OK);
	}
	
	//Get Address by Bank
	@GetMapping("/bank")
	public ResponseEntity<ResponseStructure<Address>> getAddressByBank(@RequestBody Bank bank){
		return new ResponseEntity<ResponseStructure<Address>>(addressService.getAddressByBank(bank),HttpStatus.FOUND);
	}
	
	//Get Address By City
	@GetMapping("/city/{city}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCity(@PathVariable String city){
		return new ResponseEntity<ResponseStructure<List<Address>>>(addressService.getAddressByCity(city),HttpStatus.FOUND);
	}
	
	//Get Bank by City and State
	@GetMapping("/{city}/{state}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCityAndState(@PathVariable String city, @PathVariable String state){
		return new ResponseEntity<ResponseStructure<List<Address>>>(addressService.getAddressByCityAndState(city, state), HttpStatus.FOUND);
	}
	
	//Get Address By PinCode
	@GetMapping("/pincode/{pin}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByPincode(@PathVariable Long pin){
		return new ResponseEntity<ResponseStructure<List<Address>>>(addressService.getAddressByPincode(pin), HttpStatus.FOUND);
	}
}
