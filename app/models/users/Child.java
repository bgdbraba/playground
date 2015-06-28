package models.users;

import com.avaje.ebean.Page;
import conf.DateConverter;
import conf.Language;
import models.day.ChildDay;
import models.day.FormulaDay;
import models.day.PlaygroundDay;
import models.playground.Activity;
import models.playground.Playground;
import models.playground.SessionCard;
import models.users.enums.Gender;
import models.users.forms.ChildForm;
import models.users.information.Address;
import models.users.information.ChildSessionCard;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@DiscriminatorValue("child")
public class Child extends BasicUser{
	
	public String phoneWork;
	
	public String phoneAlt;
	
	public boolean receiveMail;
	
	public boolean photographable;
	
	public String remarks;
	
	@OneToOne
	public Address address;
	
	public String doctor;
	
	public double notPayed = 0.00;
	
	@ManyToOne
	public Playground playground;
	
	public boolean onPlayground;
	
	public int numberOfSessions;
	
	@OneToOne
	public ChildSessionCard card;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="child")
	public List<ChildDay> days;
	
	public static Finder<String, Child> find = new Finder<>(String.class, Child.class);
	
	@ManyToMany
	public List<Activity> activities;
	
	@ManyToMany
	public List<PlaygroundDay> playgroundDays;

	@ManyToMany
	public List<FormulaDay> formulaDays;
	
	public Child(){}

	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static void update(String id, String password, Language language,
			String firstName, String lastName, String dateOfBirth,
			Gender gender, String email, String phone,
			String phoneWork, String phoneAlt,boolean receiveMail,
			boolean photographable, String remarks, String doctor) {
		
		BasicUser.initializeUser(id, password, dateOfBirth, firstName, lastName, gender, language, email, phone);
		
		Child child = Child.find.byId(id);
		
		child.phoneAlt = phoneAlt;
		child.phoneWork = phoneWork;
		child.receiveMail = receiveMail;
		child.photographable = photographable;
		child.doctor = doctor;
        child.remarks = remarks;
		
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
	
	public static void addChildDay(String childId, Long childDayId){
		Child child = Child.find.byId(childId);
		ChildDay childDay = ChildDay.find.byId(childDayId);
		
		child.days.add(childDay);
		
		child.update();
	}
	
	public static void addChildSessionCard(String childId, Long childSessionCardId){
		Child child = Child.find.byId(childId);
        ChildSessionCard childSessionCard = ChildSessionCard.find.byId(childSessionCardId);

        child.card = childSessionCard;

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
	
	public static double totalPayed(String childId){
		Child child = Child.find.byId(childId);
		
		double payed = 0.00;
		
		for(ChildDay day : child.days){
			payed += day.amountPayed;
		}
		
		return payed;
	}
	
	public static void addNotPayed(String childId, double cost){
		Child child = Child.find.byId(childId);
		child.notPayed += cost;
		
		child.update();
	}
	
	public static void payed(String childId){
		Child child = Child.find.byId(childId);
		child.notPayed = 0.00;
		
		child.update();
	}
	
	public static void onPlayground(String childId){
		Child child = Child.find.byId(childId);
		child.onPlayground = true;
		
		child.update();
	}
	
	public static void offPlayground(String childId){
		Child child = Child.find.byId(childId);
		child.onPlayground = false;
		
		child.update();
	}
	
	public static void addLinkToActivity(String childId, Long activityId){
		Child child = Child.find.byId(childId);
		Activity activity = Activity.find.byId(activityId);
		
		child.activities.add(activity);
		
		child.saveManyToManyAssociations("activities");
	}
	
	public static void removeLinkFromActivity(String childId, Long activityId){
		Child child = Child.find.byId(childId);
		Activity activity = Activity.find.byId(activityId);
		
		child.activities.remove(activity);
		
		child.saveManyToManyAssociations("activities");
	}
	
	
	public static List<Child> getChildrenForPlayground(Long playgroundId){
		return find.where().eq("playground", Playground.find.byId(playgroundId)).findList();
	}
	
	public static boolean alreadyHasActivity(String childId, Long activityId){
		boolean result = false;
		
		Child child = Child.find.byId(childId);
		
		for(Activity activity : child.activities){
			if(activityId == activity.id){
				result = true;
			}
		}
		
		return result;
	}
	
	public static List<Child> childrenOnPlayground(Long playgroundId){
		return find.where().eq("playground", Playground.find.byId(playgroundId)).eq("onPlayground", true).findList();
	}
	
	public static void renewSessionCard(String childId){
		Child child = Child.find.byId(childId);
		Playground playground = child.playground;
		
		if(playground.sessionCard.active){
			child.numberOfSessions += SessionCard.getSessionCardByPlayground(playground.id).numberOfSessions;
		}else{
			child.numberOfSessions = 0;
		}		
		
		child.update();
	}
	
	public static void setSessionCardToZero(String childId){
		Child child = Child.find.byId(childId);
		Playground playground = child.playground;
		
		child.numberOfSessions = 0;
				
		child.update();
	}
	
	public static void increaseSessionCard(String childId){
		Child child = Child.find.byId(childId);
		
		child.numberOfSessions++;
				
		child.update();
	}
	
	public static void decreaseSessionCard(String childId){
		Child child = Child.find.byId(childId);
		
		child.numberOfSessions--;
				
		child.update();
	}
	
	public static void decreaseNumberOfSessions(String childId, int sessions){
		Child child = Child.find.byId(childId);
		
		child.numberOfSessions -= sessions;
		
		child.update();
	}
	
	public static void increaseNumberOfSessions(String childId, int sessions){
		Child child = Child.find.byId(childId);
		
		child.numberOfSessions += sessions;
		
		child.update();
	}
	
	
	public static void addFormulaDay(String childId, Long formulaDayId){
		Child child = Child.find.byId(childId);
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		
		if(!child.formulaDays.contains(formulaDay)){
			child.formulaDays.add(formulaDay);
			
			child.saveManyToManyAssociations("formulaDays");
		}
		
	}
	
	public static void addPlaygroundDay(String childId, Long playgroundDayId){
		Child child = Child.find.byId(childId);
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		if(!child.playgroundDays.contains(playgroundDay)){
			child.playgroundDays.add(playgroundDay);
		
			child.saveManyToManyAssociations("playgroundDays");
		}
	}
	
	public static Page<Child> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("firstName", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("playground")
                .findPagingList(pageSize)
                .getPage(page);
    }

	public static void removeNotPayed(String childId, double cost) {
		Child child = Child.find.byId(childId);
		child.notPayed -= cost;
		
		child.update();		
	}
	
}
