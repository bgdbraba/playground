package models.playground;

import models.playground.forms.SessionCardForm;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class SessionCard extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public int numberOfSessions;

	public BigDecimal cost;
		
	public boolean active;
	
	@OneToOne
	public Playground playground;
	
	public static Finder<Long, SessionCard> find = new Finder<Long, SessionCard>(Long.class, SessionCard.class);
	
	public static SessionCard create(){
		SessionCard sessionCard = new SessionCard();
		
		sessionCard.save();
		
		return sessionCard;
	}


	public static void initialize(Long id, int numberOfSessions, BigDecimal cost) {
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
	
	public static SessionCard getSessionCardByPlayground(Long playgroundId){
		return find.where().eq("playground.id", playgroundId).findUnique();
	}
	
	public static void addSessionCard(Long sessionCardId, Long playgroundId){
		Playground playground = Playground.find.byId(playgroundId);
		SessionCard sessionCard = SessionCard.find.byId(sessionCardId);
		
		sessionCard.playground = playground;
		
		sessionCard.update();
	}


	public SessionCardForm toForm() {
		SessionCardForm form = new SessionCardForm();
		
		form.id = id;
		form.numberOfSessions = numberOfSessions + "";
		form.playgroundId = playground.id;
		form.cost = cost + "";
		
		return form;
		
	}

}
