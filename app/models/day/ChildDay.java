package models.day;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.playground.Formula;
import models.users.Child;
import play.db.ebean.Model;
import conf.DateConverter;

@Entity	
public class ChildDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public long date;
	
	public double amountPayed;
	
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
		childDay.amountPayed = 0.00;
		
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
	
	public static void amountPayed(Long childDayId, double cost){
		ChildDay childDay = ChildDay.find.byId(childDayId);
		childDay.amountPayed = cost;
		
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
		ChildDay.find.ref(childDayId).delete();
	}
	
}
