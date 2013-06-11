package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("organizer")
public class Organizer extends User{
	
	public static Finder<String, Organizer> find = new Finder<String, Organizer>(String.class, Organizer.class);

	public static void create(Organizer organizer){
		organizer.save();
	}
	
	public static void remove(String id){
		find.ref(id).delete();
	}
}
