package models.playground;

import conf.MyMessages;
import models.day.FormulaDay;
import models.playground.forms.FormulaForm;
import play.db.ebean.Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Formula extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	public String name;

	public BigDecimal cost;

	public boolean active;
	
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

	public static void initialize(Long id, String name, BigDecimal cost, int sessionCardCompensation) {
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

	public FormulaForm toForm() {
		FormulaForm form = new FormulaForm();
		
		form.id = id;
		form.cost = cost + "";
		form.name = name;
		form.sessionCardCompensation = this.sessionCardCompensation + "";
		form.playgroundId = playground.id;
		
		return form;
	}

    public static void remove(Long formulaId) {
        Formula formula = find.byId(formulaId);
        Playground playground1 = formula.playground;

        // remove formula from playground
        playground1.formulas.remove(formula);
        // remove formula
        formula.delete();
    }
}
