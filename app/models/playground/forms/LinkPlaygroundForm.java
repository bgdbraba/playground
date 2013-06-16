package models.playground.forms;

import models.users.Organizer;
import play.data.validation.Constraints.Required;

public class LinkPlaygroundForm {
	
	public String organizerId;
	
	@Required
	public Long playgroundId;
	
	public void submit(){
		Organizer.addPlayground(organizerId, playgroundId);		
	}

}
