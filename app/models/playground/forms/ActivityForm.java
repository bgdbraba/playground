package models.playground.forms;

import conf.DateConverter;
import models.playground.Activity;
import models.playground.Playground;
import play.data.validation.Constraints.Required;

public class ActivityForm {
	
	public Long id;
	
	@Required
	public String name;
	
	@Required
	public String cost;
	
	@Required
	public String beginDate;
	
	@Required
	public String endDate;
	
	@Required
	public String beginTime;
	
	@Required
	public String endTime;
	
	public Long playgroundId;
	
	public void submit(){
		Activity activity = Activity.create();
		
		id = activity.id;
		
		update();
	}
	
	public void update(){
		Activity.initialize(id, name, Double.parseDouble(cost), DateConverter.parseDate(beginDate) + DateConverter.parseTime(beginTime), DateConverter.parseDate(endDate)+ DateConverter.parseTime(endTime));
		
		Activity.addPlayground(id, playgroundId);
		
		Playground.addActivity(playgroundId, id);
	}
	
	

}
