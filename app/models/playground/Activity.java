package models.playground;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.playground.forms.ActivityForm;
import models.users.Child;
import play.db.ebean.Model;
import conf.DateConverter;

@Entity
public class Activity extends Model {
	
	@Id
	@GeneratedValue
	public Long id;
	
	public String name;

	public Long beginDate;
	
	public Long beginTime;
	
	public Long endDate;
	
	public Long endTime;
	
	public double cost;
	
	public int numberOfChildren;
	
	@ManyToMany(mappedBy="activities")
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
	

	public static void addPlayground(Long id, Long playgroundId) {
		Activity activity = Activity.find.byId(id);
		Playground playground = Playground.find.byId(playgroundId);
		
		activity.playground = playground;
		
		activity.update();
	}


	public static void initialize(Long id, String name, double cost,
			long beginDate,long beginTime, long endDate, long endTime, int numberOfChildren) {
		Activity activity = Activity.find.byId(id);
		
		activity.beginDate = beginDate;
		activity.beginTime = beginTime;
		activity.endDate = endDate;
		activity.endTime = endTime;
		activity.name = name;
		activity.cost = cost;
		activity.numberOfChildren = numberOfChildren;
		
		activity.update();
	}
	
	public static List<Activity> getActivitiesForPlayground(Long playgroundId){
		return find.where().eq("playground", Playground.find.byId(playgroundId)).findList();
	}
	
	public ActivityForm toForm(){
		ActivityForm form = new ActivityForm();
		
		form.id = id;
		form.name = name;
		form.cost = cost + "";
		form.beginDate = DateConverter.getDateAsString(beginDate);
		form.endDate = DateConverter.getDateAsString(endDate);
		form.beginTime = DateConverter.getTimeAsString(beginTime);
		form.endTime = DateConverter.getTimeAsString(endTime);
		
		return form;
	}
	
	public static Map<String,String> options(Long playgroundId) {
		Map<String,String> options = new HashMap<String,String>();
		
		for(Activity activity : find.where().eq("playground", Playground.find.byId(playgroundId)).findList()) {
			options.put(activity.id.toString(), activity.name);
		}
		
		return options;
	}


	public static boolean full(Long activityId) {
		Activity activity = Activity.find.byId(activityId);
		
		
		return activity.children.size() >= activity.numberOfChildren;
	}
	
//	public static void addChild(Long activityId, String childId){
//		Activity activity = Activity.find.byId(activityId);
//		Child child = Child.find.byId(childId);
//		
//		activity.children.add(child);
//		
//		activity.saveManyToManyAssociations("children");
//	}
	
}
