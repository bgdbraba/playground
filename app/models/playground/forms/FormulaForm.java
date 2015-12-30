package models.playground.forms;

import models.playground.Formula;
import models.playground.Playground;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

import java.math.BigDecimal;

public class FormulaForm {

	public Long id;
	
	@Required
	public String name;
	
	@Required
	@Pattern(value="^[0-9]+.[0-9][0-9]$")
	public String cost;
	
	@Required
	@Pattern(value="^[0-9]+$")
	public String sessionCardCompensation;
	
	public Long playgroundId;
	
	public void submit(){
		Formula formula = Formula.create();
		
		id = formula.id;
		
		this.update();
	}

	public void update() {
		Formula.initialize(id, name, new BigDecimal(cost), Integer.parseInt(sessionCardCompensation));
		
		Formula.addPlayground(id, playgroundId);		
		
		Playground.addFormula(playgroundId, id);
	}

}
