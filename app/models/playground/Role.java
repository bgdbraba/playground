package models.playground;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import conf.DateConverter;

import models.users.Child;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Role extends Model{

	@Id
	@GeneratedValue
	public Long id;
	
	public String name;
	
	public Long beginAge;
	
	public Long endAge;
	
	@ManyToOne
	public Playground playground;
	
	public static Finder<Long, Role> find = new Finder<Long, Role>(Long.class, Role.class);
	
	public static Role create(){
		Role role = new Role();
		role.save();
		
		return role;
	}
	
	public static void initialize(Long id, String name, Long beginAge, Long endAge){
		Role role = Role.find.byId(id);
		
		role.name = name;
		role.beginAge = beginAge;
		role.endAge = endAge;
		
		role.update();
	}
	
	public static void addPlayground(Long roleId, Long playgroundId){
		Role role = Role.find.byId(roleId);
		Playground playground = Playground.find.byId(playgroundId);
		
		role.playground = playground;
		
		role.update();
	}
	
	public static List<Role> getRolesForPlayground(Long playgroundId){
		return find.where().eq("playground", Playground.find.byId(playgroundId)).findList();
	}
	
	public static Role getRoleForChild(String childId){
		Child child = Child.find.byId(childId);
		Playground playground = child.playground;
		
		for(Role role : Role.getRolesForPlayground(playground.id)){
			if(role.beginAge <= DateConverter.getAge(child.dateOfBirth) && role.endAge > DateConverter.getAge(child.dateOfBirth)){
				return role;
			}
		}
		
		return null;
	}
}
