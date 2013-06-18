package models.users.forms;

import models.users.Organizer;
import models.users.enums.Gender;
import play.data.validation.Constraints;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import conf.IdGenerator;
import conf.Language;

public class OrganizerForm {
	
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
	public Long playgroundId;
	
	public Organizer submit() {
		id = IdGenerator.generateUnique(firstName, lastName);
		
		Organizer organizer = Organizer.create(id,password1,language,firstName, lastName ,dateOfBirth, gender, email, phone);
		
		Organizer.addPlayground(id, playgroundId);
		
		return organizer;
	}

	public void update(){
		Organizer.update(id,password1,language,firstName,lastName, dateOfBirth,gender,email,phone);
		
		Organizer.addPlayground(id, playgroundId);
	}

}
