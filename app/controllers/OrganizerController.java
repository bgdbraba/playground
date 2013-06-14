package controllers;

import models.users.Organizer;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class OrganizerController extends Controller{
	
	public static Result showOrganizers(){
		if (Secured.isAdmin()) {
			return TODO;
		} else {
			return forbidden();
		}
	}
	
	public static Result showDetails(String id){
		Organizer organizer = Organizer.find.byId(id);
		
		return TODO;
	}

}
