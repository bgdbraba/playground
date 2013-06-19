package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import models.users.Child;
import play.db.ebean.Model;

@Entity
public class ChildDay extends Model{
	
	@Id
	@GeneratedValue
	public Long id;
	
	@ManyToOne
	public Child child;

}
