package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import models.playground.Playground;
import models.users.enums.Gender;
import models.users.forms.AnimatorForm;
import models.users.information.Address;
import conf.DateConverter;
import conf.Language;
import play.db.ebean.Model.Finder;

@Entity
@DiscriminatorValue("animator")
public class Animator extends BasicUser{
	
	@OneToOne
	public Address address;
	
	public boolean hasFollowedCourse = false;
	
	public String accountNumber;
	
	public boolean administration = false;
	
	@ManyToOne
	public Playground playground;
	
	public static Finder<String, Animator> find = new Finder<String, Animator>(String.class, Animator.class);

	public Animator(){}
	
	public static void create(Animator animator){
		animator.save();
	}
	
	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void update(String id, String password, Language language,
			String firstName, String lastName, String dateOfBirth,
			Gender gender, String email, String phone, String accountNumber, boolean hasFollowedCourse) {
		
		BasicUser.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
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

	public AnimatorForm toAnimatorForm() {
		AnimatorForm form = new AnimatorForm();
		
		form.id = id;
		form.accountNumber = accountNumber;
		form.firstName = firstName;
		form.lastName = lastName;
		form.language = language;
		form.addressId = address.id;
		form.city = address.city;
		form.dateOfBirth = DateConverter.getDateAsString(dateOfBirth);
		form.email = email;
		form.gender = gender;
		form.hasFollowedCourse = hasFollowedCourse;
		form.number = address.number;
		form.password1 = password;
		form.password2 = password;
		form.phone = phone;
		form.playgroundId = playground.id;
		form.street = address.street;
		form.zipCode = address.zipCode;
		
		return form;
	}

	public static boolean alreadyAdministrationInPlayground(Long playgroundId) {
		Playground playground = Playground.find.byId(playgroundId);
		
		int i = 0;
		
		while(i < playground.animators.size() && !playground.animators.get(i).administration){
			i++;
		}
		
		return i != playground.animators.size();
	}
	
	public static Animator hasAdministration(Long playgroundId){
		if(!alreadyAdministrationInPlayground(playgroundId)){
			return null;
		}else{
			Playground playground = Playground.find.byId(playgroundId);
			
			int i = 0;
			
			while(i < playground.animators.size() && !playground.animators.get(i).administration){
				i++;
			}
			
			return playground.animators.get(i);
		}
	}

}
