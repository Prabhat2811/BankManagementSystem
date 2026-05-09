package jsp.bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.bankmanagement.entity.Account;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	//Fetch account by accountNumber
	Optional<Account> findByAccountNumber(Long accountNumber);

	//Fetch account by bank
	List<Account> findByBank(Bank bank);

	//fetch type of Account
	List<Account> findByAccountType(AccountType accountType);

	//fetch Balance Grater Than
	List<Account> findByBalanceGreaterThan(Double balance);
	
	//to check in account service while saving an account that bank exists or not
	@Query("Select b from Bank b where b.bankId=?1")
	Optional<Bank> findByBankId(Integer bankId);
	
	//check account number Exists or not.
	boolean existsByAccountNumber(Long accountNumber);

}
