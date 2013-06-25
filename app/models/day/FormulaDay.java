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

@Entity
public class FormulaDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public Long date;
	
	public Formula formula;
	
	@ManyToOne
	public PlaygroundDay playgroundDay;
	
	@ManyToMany
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
	
	public static void addChild(Long formulaDayId, String childId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		Child child = Child.find.byId(childId);
		
		formulaDay.children.add(child);
		
		formulaDay.update();
	}
	
	public static void removeChild(Long formulaDayId, String childId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		Child child = Child.find.byId(childId);
		
		formulaDay.children.remove(child);
		
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
	
	// THE METHODS HEREAFTER ARE MADE BUT NOT MEANT TO BE USED
	
	public static void removeFormula(Long formulaDayId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
				
		formulaDay.formula = null;
		
		formulaDay.update();
	}
	
	public static void removePlaygroundDay(Long formulaDayId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		
		formulaDay.playgroundDay = null;
		
		formulaDay.update();
	}
}