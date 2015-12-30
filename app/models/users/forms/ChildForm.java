package models.users.forms;

import conf.IdGenerator;
import conf.Language;
import models.users.Child;
import models.users.enums.Gender;
import models.users.information.Address;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

public class ChildForm {
	
	public String id;
	
	@Required
	public String password1;
	
	public String password2;

	@Required
	public Language language;
	
	@Required
	@Pattern(value = "^[A-Z].*$", message = "Moet met hoofdletter beginnen!")
	public String firstName;

	@Required
	@Pattern(value = "^[A-Z].*$", message = "Moet met hoofdletter beginnen!")
	public String lastName;
	
	@Required
	@Pattern(value="^[0-9][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]+$")
	public String dateOfBirth;
	
	@Required
	public Gender gender;

	@Constraints.Email
	@Pattern(value="^[a-zA-Z0-9.!#$%&â€™*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$")
	public String email;

	@Required
	@Pattern(value="^[0-9]+$")
	public String phone;
	
	@Required
	@Pattern(value = "^[A-Z].*$", message = "Moet met hoofdletter beginnen!")
	public String street;
	
	@Required
	public String number;

	@Required
	@Pattern(value="^[0-9][0-9][0-9][0-9]$")
	public String zipCode;
	
	@Required
	@Pattern(value = "^[A-Z].*$", message = "Moet met hoofdletter beginnen!")
	public String city;
	
	@Pattern(value="^[0-9]+$")
	public String phoneWork;
	
	@Pattern(value="^[0-9]+$")
	public String phoneAlt;
	
	public String doctor;
	
	public String remarks;
	
	public boolean receiveMail;
	
	public boolean photographable;
	
	public Long playgroundId;
	
	public Long addressId;
	
	
	public Child submit(){
		id = IdGenerator.generateUnique(firstName, lastName);
		
		Address address = Address.create();
		
		addressId = address.id;
		
		Child child = Child.create(id);
		
		update();
		
		return child;
		
	}
	
	public void update(){
		Address.initialize(addressId, street, number, zipCode, city);
				
		Child.update(id, password1, language, firstName, lastName, dateOfBirth, gender, email, phone, phoneWork, phoneAlt, receiveMail, photographable, remarks, doctor);
		
		Child.addAddress(id, addressId);
		Child.addPlayground(id, playgroundId);
	}
	
	

}
