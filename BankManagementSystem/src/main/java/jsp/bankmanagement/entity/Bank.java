package jsp.bankmanagement.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bankId;
	@Column(nullable=false)
	private String bankName;
	@Column(unique =true, nullable=false)
	private String ifscCode;
	@Column(nullable=false)
	private String branch;
	
	private Long contact;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Address address;
	
	@JsonIgnore
	@OneToMany(mappedBy = "bank")
	private List<Account> accounts;
	
	//Helper Method
	public void setAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.setBank(this);
        }
    }
	
	
	
	
}
