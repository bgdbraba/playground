package models.day.forms;

import java.util.List;

import models.day.PlaygroundDay;
import models.playground.Playground;
import models.users.Child;

public class DayForm {
	
	public String childId;
	
	public List<String> formulas;
	
	public double amountToPay;
	
	public void submit(){
		Child child = Child.find.byId(childId);
		
		PlaygroundDay playgroundDay = null;
		
		if(!PlaygroundDay.exists(child.playground.id)){
			playgroundDay = PlaygroundDay.create();
			PlaygroundDay.initialize(playgroundDay.id);
		}else{
			playgroundDay = PlaygroundDay.find.byId(child.playground.id);
		}
		
		Playground.addPresentChild(child.playground.id, childId);
	}

}
