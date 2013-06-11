package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User{
	
	public void initialize(){
		
	}
	
	public static void create(Admin admin){
		admin.save();
	}
}
