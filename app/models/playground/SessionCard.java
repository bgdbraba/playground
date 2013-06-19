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
	
	@OneToOne
	public Playground playground;
	
	public boolean active = true;
	
	public static Finder<Long, SessionCard> find = new Finder<Long, SessionCard>(Long.class, SessionCard.class);
	
	public static void create(){
		SessionCard sessionCard = new SessionCard();
		
		sessionCard.save();
	}
	
	
	public static void initialize(Long id, int numberOfSessions, double cost){
		SessionCard sessionCard = SessionCard.find.byId(id);
		
		sessionCard.numberOfSessions = numberOfSessions;
		sessionCard.cost = cost;
		
		sessionCard.update();
	}
	
	public static void addPlayground(Long sessionCardId, Long playgroundId){
		SessionCard sessionCard = SessionCard.find.byId(sessionCardId);
		Playground playground = Playground.find.byId(playgroundId);
		
		sessionCard.playground = playground;
		
		sessionCard.update();
		
	}

}
