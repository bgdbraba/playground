package models.users;

import conf.DateConverter;
import conf.Language;
import models.playground.Playground;
import models.users.enums.Gender;
import models.users.forms.OrganizerForm;
import play.db.ebean.Model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("organizer")
public class Organizer extends BasicUser{
	
	public Organizer(){}
	
	@ManyToOne
	public Playground playground;

	public static Model.Finder<String, Organizer> find = new Model.Finder<String, Organizer>(String.class, Organizer.class);

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
		
		BasicUser.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
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

		form.dateOfBirth = DateConverter.getDateAsStringBelgium(dateOfBirth);
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
