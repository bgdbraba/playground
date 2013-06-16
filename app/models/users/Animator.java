package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import conf.Language;

import models.users.enums.Gender;
import models.users.information.Address;

@Entity
@DiscriminatorValue("animator")
public class Animator extends User{
	
	@OneToOne
	public Address address;
	
	public boolean hasFollowedCourse;
	
	public String accountNumber;
	
	public static Finder<String, Animator> find = new Finder<String, Animator>(String.class, Animator.class);

	public static void create(Animator animator){
		animator.save();
	}
	
	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void addAddress(String animatorId, Long addressId){
		Animator  animator = Animator.find.byId(animatorId);
		Address address	= Address.find.byId(addressId);
		
		animator.address = address;
		
		animator.update();
	}

	public static void update(String id, String password, Language language,
			String firstName, String lastName, String dateOfBirth,
			Gender gender, String email, String phone) {
		
		User.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
		
		// update animator stuff here
		
	}

	public static Animator create(String id, String password,
			Language language, String firstName, String lastName,
			String dateOfBirth, Gender gender, String email, String phone) {
		
		Animator animator = new Animator();
		
		animator.id = id;
		
		animator.save();
		
		Animator.update(id, password, language, firstName, lastName, dateOfBirth, gender, email, phone);
		
		return Animator.find.byId(id);
	}
	
	

}
