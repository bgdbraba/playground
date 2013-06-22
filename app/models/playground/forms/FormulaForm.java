package models.playground.forms;

import models.playground.Formula;
import models.playground.Playground;
import play.data.validation.Constraints.Required;

public class FormulaForm {

	public Long id;
	
	@Required
	public String name;
	
	@Required
	public String cost = "0.00";
	
	@Required
	public String sessionCardCompensation = "0";
	
	public Long playgroundId;
	
	public void submit(){
		Formula formula = Formula.create();
		
		id = formula.id;
		
		update();
	}

	public void update() {
		Formula.initialize(id, name, Double.parseDouble(cost), Integer.parseInt(sessionCardCompensation));
		
		Formula.addPlayground(id, playgroundId);		
		
		Playground.addFormula(playgroundId, id);
	}

}
