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
	
	public double moneyIncome;
	
	@ManyToOne
	public Playground playground;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playgroundDay")
	public List<FormulaDay> formulaDays;	
	
	@ManyToMany(mappedBy="playgroundDays")
	public List<Child> children;
	
	public static Finder<Long, PlaygroundDay> find = new Finder<Long, PlaygroundDay>(Long.class, PlaygroundDay.class);
	
	public static PlaygroundDay create(){
		PlaygroundDay playgroundDay = new PlaygroundDay();
		
		playgroundDay.save();
		
		return playgroundDay;
	}
	
	public static void initialize(Long playgroundDayId, Long playgroundId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		playgroundDay.date = DateConverter.getCurrentDate();
		
		playgroundDay.moneyIncome = 0.00;
		
		playgroundDay.update();
		
		PlaygroundDay.addPlayground(playgroundDay.id, playgroundId);	
		
	}
	
	public static void addFormulaDay(Long playgroundDayId, Long formulaDayId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		
		playgroundDay.formulaDays.add(formulaDay);
		
		playgroundDay.update();
	}
	
	public static void newChild(Long playgroundDayId, String childId){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		Child child = Child.find.byId(childId);
		
		playgroundDay.children.add(child);
		
		playgroundDay.saveManyToManyAssociations("children");
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
		return PlaygroundDay.find.where().eq("playground", Playground.find.byId(playgroundId)).eq("date", DateConverter.getCurrentDate()).findList().size() > 0;
	}
	
	public static PlaygroundDay findbyPlayground(Long playgroundId) {
		return PlaygroundDay.find.where().eq("playground", Playground.find.byId(playgroundId)).eq("date", DateConverter.getCurrentDate()).findList().get(0);
	}
	
	public static void addDate(Long playgroundDayId, Long date){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		playgroundDay.date = date;
		
		playgroundDay.update();
	}
	
	public static void addMoney(Long playgroundDayId, double money){
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		playgroundDay.moneyIncome += money;
		
		playgroundDay.update();
	}

	public static boolean hasChild(Long playgroundDayId, String childId) {
		PlaygroundDay playgroundDay = PlaygroundDay.find.byId(playgroundDayId);
		
		return playgroundDay.children.contains(Child.find.byId(childId));
	}
	
	public static void remove(Long playgroundDayId){
		PlaygroundDay day = PlaygroundDay.find.byId(playgroundDayId);
		day.children = null;
		day.update();
		day.deleteManyToManyAssociations("children");	
		find.ref(playgroundDayId).delete();
	}
	
}
