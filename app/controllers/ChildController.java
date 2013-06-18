package controllers;

import models.users.Organizer;
import models.users.User;
import models.users.forms.OrganizerForm;
import conf.DateConverter;
import conf.MyMessages;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ChildController extends Controller{
	
	public static Result registerChild(){
		return TODO;
	}
	
	public static Result showChildren(){
		return TODO;
	}
	
	public static Result showDetails(String id){
		return TODO;
	}
	

	public static Result editChild(String id){
		return TODO;
	}

	public static Result deactivate(String childId) {
		return TODO;
	}

	public static Result activate(String childId) {
		return TODO;
	}

}
