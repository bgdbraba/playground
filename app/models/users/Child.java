package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import conf.Language;

import models.users.enums.Gender;
import models.users.information.Address;

@Entity
@DiscriminatorValue("child")
public class Child extends User{
	
	public String phoneWork;
	
	public String phoneAlt;
	
	public boolean receiveMail = true;
	
	public boolean photographable = true;
	
	public String remarks;
	
	@OneToOne
	public Address address;
	
	public String doctor;
	
	public int notPayed;
	
	public static Finder<String, Child> find = new Finder<String, Child>(String.class, Child.class);

	public static void create(Child child){
		child.save();
	}
	
	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void update(String id, String password, Language language,
			String firstName, String lastName, String dateOfBirth,
			Gender gender, String email, String phone) {
		
		User.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
		
		// other child options update
		
		Child child = Child.find.byId(id);
		
		child.notPayed = 0;
		
		// ..... so forth and so on
		
		
		
	}

	public static Child create(String id, String password,
			Language language, String firstName, String lastName,
			String dateOfBirth, Gender gender, String email, String phone) {
		
		Child child = new Child();
		
		child.id = id;
		
		child.save();
		
		Child.update(id, password, language, firstName, lastName, dateOfBirth, gender, email, phone);
		
		return Child.find.byId(id);
	}
}
