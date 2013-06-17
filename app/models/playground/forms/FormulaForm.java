package models.playground.forms;

import models.playground.Formula;
import models.playground.Playground;
import play.data.validation.Constraints.Required;

public class FormulaForm {

	public Long id;
	
	@Required
	public String name;
	
	@Required
	public String cost;
	
	public Long playgroundId;
	
	public void submit(){
		Formula formula = Formula.create();
		
		id = formula.id;
		
		update();
	}

	public void update() {
		Formula.initialize(id, name, Double.parseDouble(cost));
		
		Formula.addPlayground(id, playgroundId);		
		
		Playground.addFormula(playgroundId, id);
	}

}
