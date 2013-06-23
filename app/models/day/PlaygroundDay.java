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

@Entity
public class PlaygroundDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	public Long date;
	
	@ManyToMany
	public List<Child> child;
	
	@ManyToOne
	public Playground playground;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="playgroundDay")
	public List<FormulaDay> formulaDays;
	
	public static Finder<Long, PlaygroundDay> find = new Finder<Long, PlaygroundDay>(Long.class, PlaygroundDay.class);

}
