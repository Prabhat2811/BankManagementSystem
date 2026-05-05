package jsp.bankmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.bankmanagement.entity.Account;
import jsp.bankmanagement.entity.Bank;
import jsp.bankmanagement.entity.dto.AccountType;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	Optional<Account> findByAccountNumber(Long accountNumber);

	List<Account> findByBank(Bank bank);

	List<Account> findByAccountType(AccountType accountType);

	List<Account> findByBalanceGreaterThan(Double balance);
	
	@Query("Select b from Bank b where b.bankId=?1")
	Optional<Bank> findByBankId(Integer bankId);

}
