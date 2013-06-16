package controllers;



import models.Login;
import models.users.User;
import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import conf.LanguageSettings;
import conf.PasswordGenerator;

public class Application extends Controller {
  	
	public static Result index() {
        return ok(views.html.main.index.render());
    }
	
	public static Result login() {
		if (session().containsKey("id")) {
			return redirect(routes.UserController.profile());
		} else {
			return ok(views.html.main.login.render(Form.form(Login.class)));
		}
    }
	

	public static Result authenticate() {
		Form<Login> filledForm = Form.form(Login.class).bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.main.login.render(filledForm));
		} else {
			session().clear();
			Login credentials = filledForm.get();
			session("id", credentials.id);
			
			LanguageSettings.setLang(User.find.byId(credentials.id).language.toString().toLowerCase());
			// go to profile
			return redirect(routes.UserController.profile());
		}
	}
	
	public static Result logout() {
		session().clear();
		return redirect(routes.Application.index());
    }

	public static Result setLanguage(String langCode) {
		LanguageSettings.setLang(langCode);
		return ok();
	}  
	
	public static Result getGeneratedPassword() {
		return ok(PasswordGenerator.generate());
	}

	
	/**
	 * This method is used for setting up the correct javascriptroutes.
	 * 
	 * @return OK with an initiated javascript router
	 */
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
				routes.javascript.Application.setLanguage(),
				routes.javascript.Application.getGeneratedPassword()));
	}
}
