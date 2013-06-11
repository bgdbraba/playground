package controllers;

import models.users.User;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller{
	
	public static Result profile(){
		User user = User.find.byId(request().username());
		
		return ok(views.html.users.profile.render(user));
	}
	
	public static Result showUsers(){
		return TODO;
	}
}
