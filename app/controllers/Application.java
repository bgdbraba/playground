package controllers;

import models.users.User;
import play.*;
import play.i18n.Messages;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(views.html.main.index.render("new application is ready."));
    }  
    
    /**
	 * This class is used for the loginform.
	 */
	public static class Login {

		public String id;
		public String password;

		/**
		 * This method validates the filled in loginform.
		 * 
		 * @return null if validation is successful, else an errormessage
		 */
		public String validate() {
			if (User.authenticate(id, password) == null) {
				return Messages.get("login.fail");
			}
			return null;
		}

	}
    
  
}
