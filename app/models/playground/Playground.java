package models.playground;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.users.information.Address;
import play.db.ebean.Model;

@Entity
public class Playground extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	@OneToOne
	public Address address;

}
