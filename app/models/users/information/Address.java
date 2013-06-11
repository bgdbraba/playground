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
	
	public Locale country;
	
	public String street;
	
	public String number;
	
	public String zipCode;
	
	public String city;
	
	public static void initialize(){
		
	}
	
	public static void create(Address address){
		address.save();
	}

}
