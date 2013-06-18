package models.users.information;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Address extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public String street;
	
	public String number;
	
	public String zipCode;
	
	public String city;
	
	public static Finder<Long, Address> find = new Finder<Long, Address>(Long.class, Address.class);
	
	public static void initialize(Long id, String street, String number, String zipCode, String city){
		Address address = Address.find.byId(id);

		address.street = street;
		address.number = number;
		address.zipCode = zipCode;
		address.city = city;
		
		address.update();
	}
	
	public static Address create(){
		Address address = new Address();
		
		address.save();
		
		return address;
	}
	
	public String getAddress(){
		return street + " " + number + " " + city;
	}

}
