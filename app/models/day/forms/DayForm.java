package models.day.forms;

import java.util.List;

import models.day.ChildDay;
import models.day.FormulaDay;
import models.day.PlaygroundDay;
import models.playground.Formula;
import models.playground.Playground;
import models.users.Child;

public class DayForm {
	
	public String childId;
	
	public List<String> formulas;
	
	public double amountToPay;
	
	public void submit(){
		Child child = Child.find.byId(childId);
		
		ChildDay childDay = ChildDay.create();
		ChildDay.initialize(childDay.id);
		
		ChildDay.addChild(childDay.id, childId);
		
		amountToPay = 0.00;
		
		PlaygroundDay playgroundDay = null;
		
		// ALS SPEELDAG MET DEZE DATUM NOG NIET BESTAAT, MAAK ER 1
		if(!PlaygroundDay.exists(child.playground.id)){
			
			playgroundDay = PlaygroundDay.create();
			PlaygroundDay.initialize(playgroundDay.id, child.playground.id);
						
		}else{
			playgroundDay = PlaygroundDay.find.byId(child.playground.id);
		}
		
		if(formulas != null && formulas.size() != 0){
			// VOEG KIND TOE AAN DE FORMULEDAGEN
			for(String formulaId : formulas){
				
				Formula formula = Formula.find.byId(Long.parseLong(formulaId));
				
				amountToPay += formula.cost;
				
				FormulaDay formulaDay = null;
				
				if(PlaygroundDay.getFormulaDay(playgroundDay.id, formula.id) != null){
					formulaDay = PlaygroundDay.getFormulaDay(playgroundDay.id, formula.id);		
					
					
					
					
				}else{
					formulaDay = FormulaDay.create();
					FormulaDay.initialize(formulaDay.id);

					FormulaDay.addFormula(formulaDay.id, formula.id);
					FormulaDay.addPlaygroundDay(formulaDay.id, playgroundDay.id);	
					
					PlaygroundDay.addFormulaDay(playgroundDay.id, formulaDay.id);
				}
				
				FormulaDay.addChild(formulaDay.id, childId);
				
				ChildDay.addFormula(childDay.id, formula.id);
				
			}
		}
		
		child.notPayed = this.amountToPay;
		child.onPlayground = true;
		child.update();
		
		Playground.addPresentChild(child.playground.id, childId);
	}

}
