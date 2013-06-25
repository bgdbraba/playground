package models.playground;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.users.Child;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Activity extends Model {
	
	@Id
	@GeneratedValue
	public Long id;
	
	public String name;

	public Long beginDate;
	
	public Long endDate;
	
	public double cost;
	
	@ManyToMany
	public List<Child> children;
	
	@ManyToMany
	public List<Role> roles;
	
	@ManyToOne
	public Playground playground;
	
	public static Finder<Long, Activity> find = new Finder<Long, Activity>(Long.class, Activity.class);
	
	
	public static Activity create(){
		Activity activity = new Activity();
		
		activity.save();
		
		return activity;
	}
	
	public static void initialize(Long activityId){
		// NOG AF TE WERKEN
	}
}
