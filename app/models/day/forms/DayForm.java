package models.day.forms;

import models.day.ChildDay;
import models.day.FormulaDay;
import models.day.PlaygroundDay;
import models.playground.Formula;
import models.playground.Playground;
import models.users.Child;

import java.math.BigDecimal;
import java.util.List;

public class DayForm {
	
	public String childId;
	
	public List<String> formulas;

	public BigDecimal amountToPay;
	
	public void submit(){
		Child child = Child.find.byId(childId);
		
		/** KIND DAG */
		ChildDay childDay;
		
		if(!ChildDay.exists(childId)){
			childDay = ChildDay.create();
			ChildDay.initialize(childDay.id);
			ChildDay.addChild(childDay.id, childId);
		}else{
			childDay = ChildDay.getCurrentChildDay(childId);	
		}


		amountToPay = new BigDecimal("0.00");
		
		/** PLAYGROUND DAY */
		PlaygroundDay playgroundDay;
		
		if(!PlaygroundDay.exists(child.playground.id)){
			playgroundDay = PlaygroundDay.create();
			PlaygroundDay.initialize(playgroundDay.id, child.playground.id);
			Playground.addPlaygroundDay(child.playground.id, playgroundDay.id);		
			
		}else{
			playgroundDay = PlaygroundDay.findbyPlayground(child.playground.id);
			
		}
		
		/** GESELECTEERD FORMULES */
		if(formulas != null && formulas.size() != 0){
			// VOEG KIND TOE AAN DE FORMULEDAGEN
			for(String formulaId : formulas){
				
				Formula formula = Formula.find.byId(Long.parseLong(formulaId));
				
				
				/** FORMULA DAY */
				FormulaDay formulaDay;
				
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
				/** KIND HEEFT REEDS DEZE FORMULE --> ER GEBEURT NIETS */
				if(!ChildDay.hasFormula(childDay.id, formula.id)){
					
					
					if(!FormulaDay.hasChild(formulaDay.id,childId)){

						FormulaDay.addChild(formulaDay.id, childId);
						Child.addFormulaDay(childId, formulaDay.id);
					}
					
					ChildDay.addFormula(childDay.id, formula.id);
					
					if(Child.find.byId(child.id).numberOfSessions != 0 && formula.sessionCardCompensation != 0 && (Child.find.byId(child.id).numberOfSessions >= formula.sessionCardCompensation)){
						Child.decreaseNumberOfSessions(child.id, formula.sessionCardCompensation);
					}else{
						amountToPay = amountToPay.add(formula.cost);
					}
				}			
			}
		}
		
		
		if(!PlaygroundDay.hasChild(playgroundDay.id, childId)){
			PlaygroundDay.newChild(playgroundDay.id, childId);
			Child.addPlaygroundDay(childId, playgroundDay.id);
		}
		
		Child.addNotPayed(child.id, amountToPay);
		Child.onPlayground(childId);		
	}
}
