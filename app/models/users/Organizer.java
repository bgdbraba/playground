package models.users;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import conf.Language;

import models.playground.Playground;
import models.users.enums.Gender;

@Entity
@DiscriminatorValue("organizer")
public class Organizer extends User{
	
	@ManyToMany
	public List<Playground> playgrounds;
	
	public static Finder<String, Organizer> find = new Finder<String, Organizer>(String.class, Organizer.class);

	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void addPlayground(String organizerId, Long playgroundId){
		Organizer organizer = Organizer.find.byId(organizerId);
		Playground playground = Playground.find.byId(playgroundId);
		
		organizer.playgrounds.add(playground);
		organizer.saveManyToManyAssociations("playgrounds");
	}
	
	public static List<Playground> getPlaygroundsOfOrganizer(String id){
		return find.byId(id).playgrounds;
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
		boolean result = false;
		
		Organizer organizer = Organizer.find.byId(organizerId);
		
		for(Playground playground : organizer.playgrounds){
			if(playgroundId == playground.id){
				result = true;
			}
		}
		
		return result;
	}
	
}
