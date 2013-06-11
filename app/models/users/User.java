package models.users;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.Gender;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import conf.Language;


@Entity
public class User extends Model{

	@Id
	public String id;
	
	public String password;
	
	public String firstName;
	
	public String lastName;
	
	public Gender gender;
	
	public String phone;
	
	public String email;
	
	public Long dateOfBirth;
	
	public Language language;
	
	public boolean active;
	
	public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
	
	public static User authenticate(String id, String password){
		return find.where()
				.eq("id",id)
				.eq("password", password)
				.eq("active", true)
				.findUnique();
	}
	
	public static void create(User user){
		user.save();
	}
	
	public static void remove(String id){
		find.ref(id).delete();
	}
	
	public static List<User> findAll(){
		return find.all();
	}
	
	public static String getFullName(String id){
		return find.ref(id).firstName + " " + find.ref(id).lastName;
	}
	
	public static String getFirstName(String id){
		return find.ref(id).firstName;
	}
	
	public static String getLastName(String id){
		return find.ref(id).lastName;
	}

}
