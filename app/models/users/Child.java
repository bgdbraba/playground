package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

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
}
