package models.playground;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Formula extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	public String name;
	
	public double cost;
	
	public int sessionCardCompensation;
	
	@ManyToOne
	public Playground playground;

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
	
	public String toString(Long id){
		Formula formula = Formula.find.byId(id);
		
		return formula.name + " (" + formula.cost + ";" + formula.sessionCardCompensation + ")";
	}

}
