package models.day;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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

	public static Finder<Long, PlaygroundDay> find = new Finder<Long, PlaygroundDay>(Long.class, PlaygroundDay.class);
}
