package models.playground;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.users.information.Address;
import play.db.ebean.Model;

@Entity
public class Playground extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	public String name;
	
	@OneToOne
	public Address address;
	
	public static Playground create(){
		Playground playground = new Playground();
		
		playground.save();
		
		return playground;
	}

}
