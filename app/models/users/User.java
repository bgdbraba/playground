package models.users;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import models.users.enums.Gender;
import models.users.enums.UserType;
import models.users.forms.UserForm;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import conf.DateConverter;
import conf.Language;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="utype")
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
    public UserType utype;
	
	public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
	
	public static User authenticate(String id, String password){
		return find.where()
				.eq("id",id)
				.eq("password", password)
				.eq("active", true)
				.findUnique();
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
    	return utype == t;
    }
	
    public static boolean is(String id, UserType t) {
    	return find.where().eq("id", id).eq("utype", t.toString().toLowerCase()).findRowCount() > 0;
    }

	public UserForm toUserForm() {
		UserForm form = new UserForm();
		
		form.id = id;
		form.dateOfBirth = DateConverter.getDateAsString(dateOfBirth);
		form.firstName = firstName;
		form.lastName = lastName;
		form.gender = gender;
		form.email = email;
		form.phone = phone;
		form.language = language;
		form.password1 = password;
		form.password2 = password;
		
		return form;
	}
	
	public static void initializeUser(String id, String password, String dateOfBirth
										, String firstName, String lastName, Gender gender
										, Language language, String email, String phone){
		
		User user = User.find.byId(id);
		
		user.password = password;
		user.firstName = firstName;
		user.lastName = lastName;
		user.language = language;
		user.gender = gender;
		user.dateOfBirth = DateConverter.parseDate(dateOfBirth);
		user.phone = phone;
		user.email = email;
		
		user.update();		
	}
}
