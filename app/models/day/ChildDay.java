package models.day;

import conf.DateConverter;
import models.playground.Formula;
import models.users.Child;
import play.db.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity	
public class ChildDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public long date;

	public BigDecimal amountPayed;
	
	@ManyToOne
	public Child child;
	
	@ManyToMany
	public List<Formula> formulas;
	
	public static Finder<Long, ChildDay> find = new Finder<Long, ChildDay>(Long.class, ChildDay.class);
	
	public static ChildDay create(){
		ChildDay childDay = new ChildDay();
		
		childDay.save();
		
		return childDay;
	}
	
	public static void initialize(Long childDayId){
		ChildDay childDay = ChildDay.find.byId(childDayId);
		
		childDay.date = DateConverter.getCurrentDate();
		childDay.amountPayed = new BigDecimal("0.00");
		
		childDay.update();
	}
	
	public static void addChild(Long childDayId, String childId){
		ChildDay childDay = ChildDay.find.byId(childDayId);
		Child child = Child.find.byId(childId);
		
		childDay.child = child;
		
		childDay.update();
	}
	
	public static void addFormula(Long childDayId, Long formulaId){
		ChildDay childDay = ChildDay.find.byId(childDayId);
		Formula formula = Formula.find.byId(formulaId);
		
		childDay.formulas.add(formula);
		
		childDay.saveManyToManyAssociations("formulas");
	}

	public static void amountPayed(Long childDayId, BigDecimal cost) {
		ChildDay childDay = ChildDay.find.byId(childDayId);
		childDay.amountPayed = cost;
		
		childDay.update();		
	}

	public static void addAmountPayed(Long childDayId, BigDecimal cost) {
		ChildDay childDay = ChildDay.find.byId(childDayId);
		childDay.amountPayed = childDay.amountPayed.add(cost);
		
		childDay.update();		
	}
	
	public static List<ChildDay> getChildDaysForChild(String childId){
		return find.where().eq("child", Child.find.byId(childId)).findList();
	}
	
	public static boolean exists(String childId){
		return ChildDay.find.where().eq("child", Child.find.byId(childId)).eq("date", DateConverter.getCurrentDate()).findList().size() > 0;
	}
	
	public static ChildDay getCurrentChildDay(String childId){
		return ChildDay.find.where().eq("child", Child.find.byId(childId)).eq("date", DateConverter.getCurrentDate()).findList().get(0);
	}
	
	public static boolean hasFormula(Long childDayId, Long formulaId){
		ChildDay childDay = ChildDay.find.byId(childDayId);
		Formula formula = Formula.find.byId(formulaId);
		
		return childDay.formulas.contains(formula);
	}
	
	public static void remove(Long childDayId){
		ChildDay childDay = ChildDay.find.byId(childDayId);
		Child child = ChildDay.find.byId(childDayId).child;
		
		PlaygroundDay playgroundDay = PlaygroundDay.find.where().eq("date",childDay.date).findList().get(0);
		
		PlaygroundDay.subtractMoney(playgroundDay.id, childDay.amountPayed);
				
		for(int i = 0 ; i < childDay.formulas.size() ; i++){
			Formula formula = childDay.formulas.get(i);
			FormulaDay formulaDay = FormulaDay.find.where().eq("formula", Formula.find.byId(formula.id)).eq("date",childDay.date).findList().get(0);
			
			if(formulaDay != null){
				formulaDay.removeChild(formulaDay.id,childDay.child.id);
			}
			
		}
		
		PlaygroundDay.removeChild(playgroundDay.id, child.id);
		
		childDay.deleteManyToManyAssociations("formulas");
		ChildDay.find.ref(childDayId).delete();
	}
	
}
