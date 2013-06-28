package models.playground;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import models.day.PlaygroundDay;
import models.playground.forms.PlaygroundForm;
import models.users.Animator;
import models.users.Child;
import models.users.Organizer;
import models.users.information.Address;
import play.db.ebean.Model;

@Entity
public class Playground extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	public String name;
	
	public String phone;
	
	public String email;
	
	public String website;
	
	@OneToOne
	public Address address;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playground")
	public List<Organizer> organizers;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playground")
	public List<Formula> formulas;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playground")
	public List<Role> roles;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playground")
	public List<Animator> animators;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playground")
	public List<Child> children;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playground")
	public List<PlaygroundDay> playgroundDays;
		
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playground")
	public List<Activity> activities;
	
	@OneToOne(mappedBy="playground")
	public SessionCard sessionCard;
	
	public static Finder<Long, Playground> find = new Finder<Long, Playground>(Long.class, Playground.class);
	
	public static Playground create(){
		Playground playground = new Playground();
		
		playground.save();
		
		return playground;
	}
	
	public static void initialize(Long playgroundId, String name, String phone, String email, String website){
		Playground playground = Playground.find.byId(playgroundId);
		
		playground.name = name;
		playground.phone = phone;
		playground.email = email;
		playground.website = website;
		
		playground.update();
	}
	
	public static void addAddress(Long playgroundId, Long addressId){
		Playground playground = Playground.find.byId(playgroundId);
		Address address = Address.find.byId(addressId);
		
		playground.address = address;
		
		playground.update();
	}
	
	public static Map<String,String> options() {
		Map<String,String> options = new HashMap<String,String>();
		
		for(Playground playground : find.all()) {
			options.put(playground.id.toString(), playground.name + ", " + playground.address.city);
		}
		
		return options;
	}
	
	public static void addRole(Long playgroundId, Long roleId){
		Playground playground = Playground.find.byId(playgroundId);
		Role role = Role.find.byId(roleId);
		
		playground.roles.add(role);
		
		playground.update();
	}
	
	public static void addOrganizer(Long playgroundId, String organizerId){
		Playground playground = Playground.find.byId(playgroundId);
		Organizer organizer = Organizer.find.byId(organizerId);
		
		playground.organizers.add(organizer);
		
		playground.update();
	}
	
	public static void addFormula(Long playgroundId, Long formulaId){
		Playground playground = Playground.find.byId(playgroundId);
		Formula formula = Formula.find.byId(formulaId);
		
		playground.formulas.add(formula);
		
		playground.update();
	}
	
	public static void addAnimator(Long playgroundId, String animatorId){
		Playground playground = Playground.find.byId(playgroundId);
		Animator animator = Animator.find.byId(animatorId);
		
		playground.animators.add(animator);
		
		playground.update();
	}
	
	public static void addChild(Long playgroundId, String childId){
		Playground playground = Playground.find.byId(playgroundId);
		Child child = Child.find.byId(childId);
		
		playground.children.add(child);
		
		playground.update();
	}
	
	public static void addActivity(Long playgroundId, Long activityId){
		Playground playground = Playground.find.byId(playgroundId);
		Activity activity = Activity.find.byId(activityId);
		
		playground.activities.add(activity);
		
		playground.update();
	}
	
	public static void addPlaygroundDay(Long playgroundId, Long playgroundDayId){
		Playground playground = Playground.find.byId(playgroundId);
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		playground.playgroundDays.add(playgroundDay);
		
		playground.update();
	}
	
	public static List<Animator> getAnimatorOfPlayground(Long id){
		return Playground.find.byId(id).animators;
	}

	public PlaygroundForm toForm() {
		PlaygroundForm form = new PlaygroundForm();
		
		form.id = id;
		form.name = name;
		form.number = address.number;
		form.phone = phone;
		form.street = address.street;
		form.zipCode = address.zipCode;
		form.city = address.city;
		form.email = email;
		form.website = website;
		
		return form;
	}

}
