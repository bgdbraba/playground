package models.playground.forms;

import models.playground.Playground;
import models.playground.SessionCard;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

public class SessionCardForm {

	public Long id;
	
	@Required
	@Pattern(value="^[0-9]+.[0-9][0-9]$")
	public String cost;
	
	@Required
	@Pattern(value="^[0-9]+$")
	public String numberOfSessions;
	
	public Long playgroundId;
	
	public void submit(){
		SessionCard sessionCard = SessionCard.create();
		
		id = sessionCard.id;
		
		update();
	}
	
	public void update(){
		SessionCard.initialize(id, Integer.parseInt(numberOfSessions), Double.parseDouble(cost));
				
		SessionCard.addSessionCard(id, playgroundId);
	}
}
