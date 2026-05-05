package jsp.bankmanagement.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jsp.bankmanagement.entity.dto.AccountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountId;
	@Column(nullable = false, unique = true)
	private Long accountNumber;
	@Column(nullable = false)
	private String accountHolder;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountType accountType;
	private Double balance;
	
	@ManyToOne
	@JoinColumn(name = "bank_id")
	private Bank bank;
	
	
}
