package models.playground.forms;

import java.util.Locale;

import models.playground.Playground;
import models.users.information.Address;

import play.data.validation.Constraints.Required;

public class PlaygroundForm {
	
	public Long id;
	
	@Required
	public String name;
	
	@Required
	public String street;
	
	@Required
	public String number;

	@Required
	public String zipCode;
	
	@Required
	public String city;
	
	public void update(Long id){
		Playground playground = Playground.find.byId(id);
		
		Address.initialize(playground.address.id, street, number, zipCode, city);
		
		Playground.initialize(playground.id, name);	
	}
	
	public void create(){
		Playground playground = Playground.create();
		Address address = Address.create();
		
		Playground.addAddress(playground.id, address.id);
		
		update(playground.id);
	}
	
	
}
