package models.playground;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import models.day.FormulaDay;
import play.db.ebean.Model;
import conf.MyMessages;

@Entity
public class Formula extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	public String name;
	
	public double cost;
	
	public int sessionCardCompensation;
	
	@ManyToOne
	public Playground playground;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="formula")
	public List<FormulaDay> formulaDays;

	public static Finder<Long, Formula> find = new Finder<Long, Formula>(Long.class, Formula.class);
	
	public static Formula create(){
		Formula formula = new Formula();
		
		formula.save();
		
		return formula;
	}
	
	public static void initialize(Long id, String name, double cost, int sessionCardCompensation){
		Formula formula = Formula.find.byId(id);
		
		formula.cost = cost;
		formula.name = name;
		formula.sessionCardCompensation = sessionCardCompensation;
		
		formula.update();
	}
	
	public static void addPlayground(Long formulaId, Long playgroundId){
		Playground playground = Playground.find.byId(playgroundId);
		Formula formula = Formula.find.byId(formulaId);
		
		formula.playground = playground;
		
		formula.update();
	}
	
	public static void addFormulaDay(Long formulaId, Long formulaDayId){
		FormulaDay formulaDay = FormulaDay.find.byId(formulaDayId);
		Formula formula = Formula.find.byId(formulaId);
		
		formula.formulaDays.add(formulaDay);
		
		formula.update();
	}
	
	public static List<Formula> getFormulasForPlayground(Long playgroundId){
		return find.where().eq("playground", Playground.find.byId(playgroundId)).findList();
	}
	
	public String toString(){
		return name + " (" + MyMessages.get("cost") + " " + cost + " ; " + MyMessages.get("sessionCardCompensation")+ " " + sessionCardCompensation + ")";
	}
	
	public static List<Formula> findAllForPlayground(Long playgroundId){
		return Formula.find.where().eq("playground", Playground.find.byId(playgroundId)).findList();
	}

}
