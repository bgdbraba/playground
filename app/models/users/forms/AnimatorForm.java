package models.users.forms;

import conf.IdGenerator;
import conf.Language;
import models.users.Animator;
import models.users.enums.Gender;
import models.users.information.Address;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

public class AnimatorForm {
	
	public String id;
	
	@Required
	public String password1;
	
	public String password2;

	@Required
	public Language language;
	
	@Required
	public String firstName;
	
	@Required 
	public String lastName;
	
	@Required
	@Pattern(value="^[0-9][0-9]-[0-1][0-9]-[0-2][0-9][0-9][0-9]+$")
	public String dateOfBirth;
	
	@Required
	public Gender gender;

	@Required
	@Constraints.Email
	@Pattern(value="^[a-zA-Z0-9.!#$%&â€™*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$")
	public String email;

	@Required
	@Pattern(value="^([0-9]+[/ ])+[0-9]+$")
	public String phone;
	
	@Required
	public boolean hasFollowedCourse = false;
	
	@Required
	@Pattern(value="^([0-9]+[ ]*)+$")
	public String accountNumber;

	@Required
	public String street;
	
	@Required
	public String number;

	@Required
	@Pattern(value="^[0-9][0-9][0-9][0-9]$")
	public String zipCode;
	
	@Required
	public String city;
	
	public Long playgroundId;
	
	public Long addressId;
	
	public Animator submit() {
		id = IdGenerator.generateUnique(firstName, lastName);
		
		Address address = Address.create();
		
		addressId = address.id;
		
		Animator animator = Animator.create(id);
		
		update();
		
		return animator;
	}

	public void update(){
			Address.initialize(addressId, street, number, zipCode, city);
			
			Animator.update(id,password1,language,firstName,lastName, dateOfBirth,gender,email,phone, accountNumber, hasFollowedCourse);
	
			Animator.addAddress(id, addressId);
			Animator.addPlayground(id, playgroundId);
	}

}
