package models.day;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import conf.DateConverter;

import models.playground.Formula;
import models.users.Child;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity	
public class ChildDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public long date;
	
	@ManyToOne
	public Child child;
	
	@ManyToMany // or MANYTOMANY
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
		
		childDay.update();		
	}
	
	public static boolean exists(String childId){
		return ChildDay.find.where().eq("child", Child.find.byId(childId)).eq("date", DateConverter.getCurrentDate()) != null;
	}

}
