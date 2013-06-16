package models.playground;

import java.util.HashMap;
import java.util.Map;

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
	
	public static Finder<Long, Playground> find = new Finder<Long, Playground>(Long.class, Playground.class);
	
	public static Playground create(){
		Playground playground = new Playground();
		
		playground.save();
		
		return playground;
	}
	
	public static void initialize(Long playgroundId, String name){
		Playground playground = Playground.find.byId(playgroundId);
		
		playground.name = name;
		
		playground.update();
	}
	
	public static void addAddress(Long playgroundId, Long addressId){
		Playground playground = Playground.find.byId(playgroundId);
		Address address = Address.find.byId(addressId);
		
		playground.address = address;
		
		playground.update();
	}
	
	public static Map<String,String> options() {
		Map<String,String> options = new HashMap<String,String>();
		for(Playground playground : find.all()) {
			options.put(playground.id.toString(), playground.name + ", " + playground.address.city);
		}
		return options;
	}

}
