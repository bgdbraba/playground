package models.day;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.playground.Formula;
import models.playground.Playground;
import play.db.ebean.Model;
import conf.DateConverter;

@Entity
public class PlaygroundDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public Long date;
	
	@ManyToOne
	public Playground playground;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playgroundDay")
	public List<FormulaDay> formulaDays;
	
	public static Finder<Long, PlaygroundDay> find = new Finder<Long, PlaygroundDay>(Long.class, PlaygroundDay.class);
	
	public static PlaygroundDay create(){
		PlaygroundDay playgroundDay = new PlaygroundDay();
		
		playgroundDay.save();
		
		return playgroundDay;
	}
	
	public static void initialize(Long playgroundDayId, Long playgroundId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		playgroundDay.date = DateConverter.getCurrentDate();
		
		PlaygroundDay.addPlayground(playgroundDay.id, playgroundId);
		
		Playground playgrounded = Playground.find.byId(playgroundId);
		
		for(Formula formula : playgrounded.formulas){
			System.out.println("HAS FORMULAS");
			FormulaDay formulaDay = FormulaDay.create();
			FormulaDay.initialize(formulaDay.id);
			FormulaDay.addFormula(formulaDay.id, formula.id);
			FormulaDay.addPlaygroundDay(formulaDay.id, playgroundDayId);	
			
			PlaygroundDay.addFormulaDay(playgroundDayId, formulaDay.id);			
		}		
		
		playgroundDay.update();
	}
	
	
	
	
	
	public static void addFormulaDay(Long playgroundDayId, Long formulaDayId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		
		playgroundDay.formulaDays.add(formulaDay);
		
		playgroundDay.update();
	}
	
	public static void addPlayground(Long playgroundDayId, Long playgroundId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		Playground playground = Playground.find.byId(playgroundId);
		
		playgroundDay.playground = playground;
		
		playgroundDay.update();
	}
	
	// THE METHODS HEREAFTER ARE MADE BUT NOT MEANT TO BE USED
	public static void removeFormulaDay(Long playgroundDayId, Long formulaDayId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		
		playgroundDay.formulaDays.remove(formulaDay);
		
		playgroundDay.update();
	}
	
	public static void removePlayground(Long playgroundDayId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		playgroundDay.playground = null;
		
		playgroundDay.update();
	}

	public static boolean exists(Long playgroundId) {
		return PlaygroundDay.find.where().eq("playground", playgroundId).eq("date", DateConverter.getCurrentDate()) != null;
	}
	
	public static FormulaDay getFormulaDay(Long playgroundDayId, Long formulaId){
		
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		for(FormulaDay day : playgroundDay.formulaDays){
			System.out.println("ENTERSSSS");
			if(day.formula == Formula.find.byId(formulaId)){
				return day;
			}
		}
		
		return null;
	}
}
