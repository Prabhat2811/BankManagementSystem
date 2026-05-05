package jsp.bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.bankmanagement.entity.Address;
import jsp.bankmanagement.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer>{
	
	//Fetch Bank By Ifsc
	Optional<Bank> findByIfscCode(String ifse);
	
	//Fetch Bank By Address 
	Optional<Bank> findByAddress(Address address);

	//Fetch Bank By City
	@Query("Select b from Bank b where b.address.city=?1")
	List<Bank> getBankByCity(String city);

	//Fetch Bank By contact
	Optional<Bank> findByContact(Long contact);
	
	

}
