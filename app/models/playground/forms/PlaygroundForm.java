package models.playground.forms;

import java.util.Locale;

import models.playground.Playground;
import models.users.information.Address;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

public class PlaygroundForm {
	
	public Long id;
	
	@Required
	public String name;
	
	@Required
	@Pattern(value="^[0-9]+$")
	public String phone;
	
	@Required
	public String street;
	
	@Required
	public String number;

	@Required
	@Pattern(value="^[0-9][0-9][0-9][0-9]$")
	public String zipCode;
	
	@Required
	public String city;
	
	@Constraints.Email
	@Pattern(value="^[a-zA-Z0-9.!#$%&â€™*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$")
	public String email;
	
	public String website;
	
	public void update(Long id){
		Playground playground = Playground.find.byId(id);
		
		Address.initialize(playground.address.id, street, number, zipCode, city);
		
		Playground.addAddress(playground.id, playground.address.id);
		
		Playground.initialize(playground.id, name,phone, email, website);	
	}
	
	public void create(){
		Playground playground = Playground.create();
		Address address = Address.create();
		
		Playground.addAddress(playground.id, address.id);
		
		update(playground.id);
	}	
}
