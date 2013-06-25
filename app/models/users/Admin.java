package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("admin")
public class Admin extends User{
	
	static{
		Admin admin = new Admin();
		admin.id = "bartel";
		admin.password = "bartel";
		admin.save();
	}
	
	public Admin(){}
	
	public void initialize(){}
	
	public static Admin create(String id){
		Admin admin = new Admin();
		admin.id = id;
		admin.save();
		
		return admin;
	}
}
