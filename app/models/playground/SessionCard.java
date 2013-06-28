package models.playground;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class SessionCard extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public int numberOfSessions;
	
	public double cost;
		
	public boolean active;
	
	public static Finder<Long, SessionCard> find = new Finder<Long, SessionCard>(Long.class, SessionCard.class);
	
	public static SessionCard create(){
		SessionCard sessionCard = new SessionCard();
		
		sessionCard.save();
		
		return sessionCard;
	}
	
	
	public static void initialize(Long id, int numberOfSessions, double cost){
		SessionCard sessionCard = SessionCard.find.byId(id);
		
		sessionCard.numberOfSessions = numberOfSessions;
		sessionCard.cost = cost;
		
		sessionCard.update();
	}
	
	public static void activate(Long id){
		SessionCard sessionCard = SessionCard.find.byId(id);
		
		sessionCard.active = true;
		
		sessionCard.update();
	}
	
	public static void deactivate(Long id){
		SessionCard sessionCard = SessionCard.find.byId(id);
		
		sessionCard.active = false;
		
		sessionCard.update();
	}

}
