package models.users.information;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.playground.Playground;
import models.users.Child;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class ChildSessionCard extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	@OneToOne
	public Child child;
	
	public int leftOver;

	public static Finder<Long, ChildSessionCard> find = new Finder<Long, ChildSessionCard>(Long.class, ChildSessionCard.class);
	
	public void create(){
		ChildSessionCard childSessionCard = new ChildSessionCard();
		
		childSessionCard.save();
	}
	
	public void addChild(Long childSessionCardId, String childId){
		ChildSessionCard childSessionCard = ChildSessionCard.find.byId(childSessionCardId);
		Child child = Child.find.byId(childId);
		
		childSessionCard.child = child;
		
		childSessionCard.update();
	}
	
	public void reset(){
		
	}
	
	

}
