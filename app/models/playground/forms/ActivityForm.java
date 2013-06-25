package models.playground.forms;

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
	
	

}
