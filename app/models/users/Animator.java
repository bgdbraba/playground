package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.playground.Playground;
import models.users.enums.Gender;
import models.users.information.Address;
import conf.Language;

@Entity
@DiscriminatorValue("animator")
public class Animator extends User{
	
	@OneToOne
	public Address address;
	
	public boolean hasFollowedCourse = false;
	
	public String accountNumber;
	
	public boolean administration = false;
	
	@ManyToOne
	public Playground playground;
	
	public static Finder<String, Animator> find = new Finder<String, Animator>(String.class, Animator.class);

	public static void create(Animator animator){
		animator.save();
	}
	
	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void update(String id, String password, Language language,
			String firstName, String lastName, String dateOfBirth,
			Gender gender, String email, String phone, String accountNumber, boolean hasFollowedCourse) {
		
		User.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
		Animator animator = Animator.find.byId(id);
		
		animator.accountNumber = accountNumber;
		animator.hasFollowedCourse = hasFollowedCourse;
		
		animator.update();
		
		// update animator stuff here
		
	}

	public static Animator create(String id) {
		
		Animator animator = new Animator();
		
		animator.id = id;
		
		animator.save();
		
		return animator;
	}

	public static void addPlayground(String animatorId, Long playgroundId){
		Playground playground = Playground.find.byId(playgroundId);
		Animator animator = Animator.find.byId(animatorId);
		
		animator.playground = playground;
		
		animator.update();
	}
	
	public static void addAddress(String animatorId, Long addressId){
		Animator  animator = Animator.find.byId(animatorId);
		Address address	= Address.find.byId(addressId);
		
		animator.address = address;
		
		animator.update();
	}
	
	public static void grantAdministration(String id){
		Animator animator = Animator.find.byId(id);
		
		animator.administration = true;
		
		animator.update();
	}

	public static void forbidAdministration(String id){
		Animator animator = Animator.find.byId(id);
		
		animator.administration = false;
		
		animator.update();
	}
	

}
