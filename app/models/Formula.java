package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.playground.Playground;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Formula extends Model{
	
	@Id@GeneratedValue
	public Long id;
	
	public String name;
	
	public double cost;
	
	@ManyToOne
	public Playground playground;

	public static Finder<Long, Formula> find = new Finder<Long, Formula>(Long.class, Formula.class);

}
