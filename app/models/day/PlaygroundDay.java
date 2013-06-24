package models.day;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.playground.Playground;
import models.users.Child;
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
	
	public static void initialize(Long playgroundDayId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		playgroundDay.date = DateConverter.getCurrentDate();
		
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
}
