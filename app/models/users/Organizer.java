package models.users;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.playground.Playground;
import models.users.enums.Gender;
import models.users.forms.OrganizerForm;
import conf.DateConverter;
import conf.Language;
import play.db.ebean.Model.Finder;

@Entity
@DiscriminatorValue("organizer")
public class Organizer extends User{
	
	@ManyToOne
	public Playground playground;
	
	public static Finder<String, Organizer> find = new Finder<String, Organizer>(String.class, Organizer.class);

	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void addPlayground(String organizerId, Long playgroundId){
		Organizer organizer = Organizer.find.byId(organizerId);
		Playground playground = Playground.find.byId(playgroundId);
		
		organizer.playground = playground;
		
		organizer.update();
	}

	public static void update(String id, String password, Language language,
			String firstName, String lastName, String dateOfBirth,
			Gender gender, String email, String phone) {
		
		User.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
	}

	public static Organizer create(String id, String password,
			Language language, String firstName, String lastName,
			String dateOfBirth, Gender gender, String email, String phone) {
		
		Organizer organizer = new Organizer();
		
		organizer.id = id;
		
		organizer.save();
		
		Organizer.update(id, password, language, firstName, lastName, dateOfBirth, gender, email, phone);
		
		return Organizer.find.byId(id);
	}
	
	public static boolean alreadyHasPlayground(String organizerId, Long playgroundId) {
		return Organizer.find.byId(organizerId).playground != null;
	}

	public OrganizerForm toForm() {
		OrganizerForm form = new OrganizerForm();
		
		form.dateOfBirth = DateConverter.getDateAsString(dateOfBirth);
		form.email = email;
		form.firstName = firstName;
		form.lastName = lastName;
		form.gender = gender;
		form.id = id;
		form.language = language;
		form.password1 = password;
		form.password2 = password;
		form.phone = phone;
		
		return form;
	}
	
}
