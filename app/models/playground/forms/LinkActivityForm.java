package models.playground.forms;

import models.playground.Activity;
import models.users.Child;
import play.data.validation.Constraints.Required;

public class LinkActivityForm {

	public String childId;
	
	@Required
	public Long activityId;
	
	public void submit(){
		Child.addLinkToActivity(childId, activityId);
		Activity.addChild(activityId, childId);
	}
}
