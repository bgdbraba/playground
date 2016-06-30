package models.users.forms;

import models.users.BasicUser;
import models.users.enums.Gender;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import conf.Language;

public class UserForm {
	
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

	@Constraints.Email
	@Pattern(value="^[a-zA-Z0-9.!#$%&â€™*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:.[a-zA-Z0-9-]+)*$")
	public String email;

	@Pattern(value="^([0-9]+[/ ])+[0-9]+$")
	public String phone;
	
	public void update(String userId){
		
		BasicUser.initializeUser(id, password1, dateOfBirth, firstName, lastName, gender, language, email,   phone);
	
	}
}
