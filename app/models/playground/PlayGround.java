package models.playground;

import java.net.URI;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.users.Address;
import models.users.Animator;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class PlayGround extends Model{

	@Id
	@GeneratedValue
	public Long id;
	
	public String name;
	
	@OneToOne
	public Address address;
	
	public URI website;
	
	public String email;
	
	public static Finder<String, PlayGround> find = new Finder<String, PlayGround>(String.class, PlayGround.class);
	
	public static void create(PlayGround playGround){
		playGround.save();
	}
	
	public static void remove(String id){
		find.ref(id).delete();
	}
	
}
