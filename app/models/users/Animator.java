package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
	

}
