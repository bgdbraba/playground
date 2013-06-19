package models.users;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.ChildDay;
import models.playground.Playground;
import models.users.enums.Gender;
import models.users.forms.ChildForm;
import models.users.information.Address;
import models.users.information.ChildSessionCard;
import play.db.ebean.Model.Finder;
import conf.DateConverter;
import conf.Language;

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
	
	public double notPayed = 0.0;
	
	@ManyToOne
	public Playground playground;
	
	@OneToOne
	public ChildSessionCard card;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="child")
	public List<ChildDay> days;
	
	public static Finder<String, Child> find = new Finder<String, Child>(String.class, Child.class);
	

	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void update(String id, String password, Language language,
			String firstName, String lastName, String dateOfBirth,
			Gender gender, String email, String phone,
			String phoneWork, String phoneAlt,boolean receiveMail,
			boolean photographable, String remarks, String doctor) {
		
		User.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
		Child child = Child.find.byId(id);
		
		child.phoneAlt = phoneAlt;
		child.phoneWork = phoneWork;
		child.receiveMail = receiveMail;
		child.photographable = photographable;
		child.doctor = doctor;
		
		child.update();	
	}

	public static Child create(String id) {
		
		Child child = new Child();
		
		child.id = id;
		
		child.save();
		
		return child;
	}
	
	public static void addPlayground(String childId, Long playgroundId){
		Child child = Child.find.byId(childId);
		Playground playground = Playground.find.byId(playgroundId);
		
		child.playground = playground;
		
		child.update();
	}
	
	public static void addAddress(String childId, Long addressId){
		Child child = Child.find.byId(childId);
		Address address = Address.find.byId(addressId);
		
		child.address = address;
		
		child.update();
	}

	public ChildForm toChildForm() {
		ChildForm form = new ChildForm();
		
		form.id = id;
		form.firstName = firstName;
		form.lastName = lastName;
		form.language = language;
		form.addressId = address.id;
		form.city = address.city;
		form.dateOfBirth = DateConverter.getDateAsString(dateOfBirth);
		form.email = email;
		form.gender = gender;
		form.number = address.number;
		form.password1 = password;
		form.password2 = password;
		form.phone = phone;
		form.playgroundId = playground.id;
		form.street = address.street;
		form.zipCode = address.zipCode;
		form.doctor = doctor;
		form.remarks = remarks;
		form.phoneAlt = phoneAlt;
		form.phoneWork = phoneWork;
		form.password1 = password;
		form.password2 = password;
		form.photographable = photographable;
		form.receiveMail = receiveMail;
		
		return form;
	}
}
