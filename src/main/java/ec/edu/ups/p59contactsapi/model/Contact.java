package ec.edu.ups.p59contactsapi.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Contact
 *
 */
@Entity

public class Contact implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	private String fullName;
	private String number;
	private String address;
	
	public Contact() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", fullName=" + fullName + ", number=" + number + ", address=" + address + "]";
	}
	
}
