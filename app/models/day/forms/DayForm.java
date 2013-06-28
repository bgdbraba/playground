package models.day.forms;

import java.util.List;

import conf.DateConverter;

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
		
		/** CHILD DAY SHIT */
		ChildDay childDay = ChildDay.create();
		ChildDay.initialize(childDay.id);
		
		
		amountToPay = 0.00;
		
		/** PLAYGROUNDDAY SHIT AND FORMULADAY SHIT */
		PlaygroundDay playgroundDay;
		
		// ALS SPEELDAG MET DEZE DATUM NOG NIET BESTAAT, MAAK ER 1
		if(!PlaygroundDay.exists(child.playground.id)){
			
			playgroundDay = PlaygroundDay.create();
			PlaygroundDay.initialize(playgroundDay.id, child.playground.id);
			Playground.addPlaygroundDay(child.playground.id, playgroundDay.id);
			
			
			
		}else{
			
			playgroundDay = PlaygroundDay.findbyPlayground(child.playground.id);
		}
		
		if(formulas != null && formulas.size() != 0){
			// VOEG KIND TOE AAN DE FORMULEDAGEN
			for(String formulaId : formulas){
				
				Formula formula = Formula.find.byId(Long.parseLong(formulaId));
				
				if(child.numberOfSessions != 0 && formula.sessionCardCompensation != 0 && (child.numberOfSessions >= formula.sessionCardCompensation)){
					Child.decreaseNumberOfSessions(child.id, formula.sessionCardCompensation);
				}else{
					amountToPay += formula.cost;
				}
				
				FormulaDay formulaDay;
				/** FORMULADAY SHIT */
								
				if(FormulaDay.exists(playgroundDay.id, formula.id)){
					
					formulaDay = FormulaDay.findFormulaDay(playgroundDay.id, formula.id);		
					
				}else{
					formulaDay = FormulaDay.create();
					FormulaDay.initialize(formulaDay.id);
					// formule toevoegen
					FormulaDay.addFormula(formulaDay.id, formula.id);
					Formula.addFormulaDay(formula.id, formulaDay.id);
					// playgroundday toevoegen
					FormulaDay.addPlaygroundDay(formulaDay.id, playgroundDay.id);	
					PlaygroundDay.addFormulaDay(playgroundDay.id, formulaDay.id);
				}
				
				FormulaDay.addChild(formulaDay.id, childId);
				Child.addFormulaDay(childId, formulaDay.id);
							
				ChildDay.addFormula(childDay.id, formula.id);
			}
		}
		
		ChildDay.amountPayed(childDay.id, amountToPay);
		ChildDay.addChild(childDay.id, childId);
		
		Child.addNotPayed(child.id, amountToPay);
		Child.onPlayground(childId);		
	}
}
