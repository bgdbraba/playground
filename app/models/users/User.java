package models.users;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import models.users.enums.Gender;
import models.users.enums.UserType;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import conf.Language;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="userType")
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
	
	public boolean active = true;
	
	@Column(insertable=false,updatable=true)
    public UserType userType;
	
	public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
	
	public static User authenticate(String id, String password){
		return find.where()
				.eq("id",id)
				.eq("password", password)
				.eq("active", true)
				.findUnique();
	}

	public static void initialize(String id, String password, String firstName, 
									String lastName, Gender gender, String phone, 
									String email, Long dateOfBirth, Language language){
		User user = User.find.byId(id);
		
		user.password = password;
		user.firstName = firstName;
		user.lastName = lastName;
		user.gender = gender;
		user.phone = phone;
		user.email = email;
		user.dateOfBirth = dateOfBirth;
		user.language = language;
		
		user.update();
	}
	
	public static void activate(String id){
		User user = User.find.byId(id);
		
		user.active = false;
		
		user.update();
	}
	
	public static void deactivate(String id){
		User user = User.find.byId(id);
		
		user.active = true;
		
		user.update();
	}
	
	public boolean is(UserType t) {
    	return userType == t;
    }
}
