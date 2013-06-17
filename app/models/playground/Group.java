package models.playground;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.users.Child;

import play.db.ebean.Model;


public class Group extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	public String name;
	
	public long beginAge;
	
	public long endAge;

	@ManyToOne
	public Playground playground;
	
	public static Finder<Long, Group> find = new Finder<Long, Group>(Long.class, Group.class);	
	
//	public static void addPlayground(Long groupId, Long playgroundId){
//		Group group = Group.find.byId(groupId);
//		Playground playground = Playground.find.byId(groupId);
//		
//		group.playground = playground;
//		
//		group.update();
//	}
	
	public static Group groupOfChild(String childId){
		Child child = Child.find.byId(childId);
		
		Group group = null;
		
		// AANVULLEN
		
		
		return group;
	}
}
