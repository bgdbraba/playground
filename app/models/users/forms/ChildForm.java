package models.users.forms;

import models.users.enums.Gender;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import conf.Language;

public class ChildForm {
	
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
	public String dateOfBirth;
	
	@Required
	public Gender gender;

	@Required
	@Constraints.Email
	@Pattern(value="^[a-zA-Z0-9.!#$%&â€™*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$")
	public String email;

	@Required
	@Pattern(value="^[0-9]+$")
	public String phone;
	
	@Required
	public String street;
	
	@Required
	public String number;

	@Required
	public String zipCode;
	
	@Required
	public String city;
	
	public String phoneWork;
	
	public String phoneAlt;
	
	public String doctor;
	
	public String remarks;
	
	public boolean receiveMail = true;
	
	public boolean photographable = true;
	
	public Long playgroundId;
	
	public Long addressId;

}
