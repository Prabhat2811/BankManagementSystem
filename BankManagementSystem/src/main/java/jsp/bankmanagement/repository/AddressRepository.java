package jsp.bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	//Get Address by Bank
	Optional<Address> findByBank(Bank bank);

	//Get Address By City
	List<Address> findByCity(String city);

	//Get Address By City And State
	List<Address> findByCityAndState(String city, String state);

	//Het Address By PinCode
	List<Address> findByPincode(Long pin);

}
