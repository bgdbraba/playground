package controllers;

import models.users.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class UserController extends Controller{
	
	@Security.Authenticated(Secured.class)
	public static Result profile(){
		if(!session().containsKey("id")){
			return badRequest();
		}else{
			
			User user = User.findById(request().username());
			
			return ok(views.html.users.profile.render(user));
		}
	}
	
	public static Result showUsers(){
		return TODO;
	}
}
