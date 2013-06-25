package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("admin")
public class Admin extends User{
	
	
	public Admin(){}
	
	public void initialize(){}
	
	public static Admin create(String id){
		Admin admin = new Admin();
		admin.id = id;
		admin.save();
		
		return admin;
	}
}
