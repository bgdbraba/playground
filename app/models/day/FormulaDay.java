package models.day;

import conf.DateConverter;
import models.playground.Formula;
import models.users.Child;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class FormulaDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public long date;
	
	@ManyToOne
	public Formula formula;
	
	@ManyToOne
	public PlaygroundDay playgroundDay;
	
	@ManyToMany(mappedBy="formulaDays")
	public List<Child> children;

	public static Finder<Long, FormulaDay> find = new Finder<Long, FormulaDay>(Long.class, FormulaDay.class);
	
	public static FormulaDay create(){
		FormulaDay formulaDay = new FormulaDay();
		
		formulaDay.save();
		
		return formulaDay;
	}
	
	public static void initialize(Long formulaDayId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		
		formulaDay.date = DateConverter.getCurrentDate();
		
		formulaDay.update();
	}
		
	public static void addFormula(Long formulaDayId, long formulaId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		Formula formula = Formula.find.byId(formulaId);
		
		formulaDay.formula = formula;
		
		formulaDay.update();
	}
	
	public static void addPlaygroundDay(Long formulaDayId, long playgroundDayId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		formulaDay.playgroundDay = playgroundDay;
		
		formulaDay.update();
	}
	
	public static FormulaDay findByFormulaId(Long formulaId, Long playgroundDayId){
		Formula formula = Formula.find.byId(formulaId);
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		return find.where().eq("formula", formula).eq("playgroundDay",playgroundDay).findList().get(0);
	}
	
	public static boolean existsForFormula(Long formulaId){
		return find.where().eq("formula", Formula.find.byId(formulaId)).eq("date",DateConverter.getCurrentDate()).findList().size() > 0;
	}
	
	public static FormulaDay currentFormulaDay(Long formulaId){
		if(existsForFormula(formulaId)){
			return find.where().eq("formula", Formula.find.byId(formulaId)).eq("date",DateConverter.getCurrentDate()).findUnique();
		}
		return null;
	}
	
	public static List<Child> getChildren(Long formulaDayId){
		return find.byId(formulaDayId).children;
	}
	
	public static boolean exists(Long playgroundDayId, Long formulaId){
		return find.where().eq("formula", Formula.find.byId(formulaId)).eq("playgroundDay",PlaygroundDay.find.byId(playgroundDayId)).findList().size() > 0;
	}
	
	public static FormulaDay findFormulaDay(Long playgroundDayId, Long formulaId){
		return find.where().eq("formula", Formula.find.byId(formulaId)).eq("playgroundDay",PlaygroundDay.find.byId(playgroundDayId)).findList().get(0);
	}
	
	public static boolean hasChild(Long formulaDayId, String childId) {
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		
		return formulaDay.children.contains(Child.find.byId(childId));
	}
	
	public static void addChild(Long formulaDayId, String childId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		Child child = Child.find.byId(childId);
		
		formulaDay.children.add(child);
		
		formulaDay.update();
		
		formulaDay.saveManyToManyAssociations("children");		
	}
	
	public static void remove(Long formulaDayId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		formulaDay.children = null;
		formulaDay.update();
		formulaDay.deleteManyToManyAssociations("children");		
		find.ref(formulaDayId).delete();
	}

	public static void removeChild(Long formulaDayId, String childId) {
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		Child child = Child.find.byId(childId);
		
		formulaDay.children.remove(child);
		
		formulaDay.update();
		
		formulaDay.saveManyToManyAssociations("children");	
	}
}
