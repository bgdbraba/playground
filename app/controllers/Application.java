package controllers;



import models.Login;
import models.users.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.LanguageSettings;

@Security.Authenticated(Secured.class)
public class Application extends Controller {
  	
	public static Result index() {
        return redirect(routes.PagesController.home());
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
			Login credentials = filledForm.get();
			session().clear();
			session("id", credentials.id);
			
			LanguageSettings.setLang(User.find.byId(credentials.id).language.toString().toLowerCase());
			// go to profile
			return redirect(routes.UserController.profile());
		}
	}
	
	public static Result logout() {
		session().clear();
		return redirect(routes.PagesController.home());
    }

	public static Result setLanguage(String langCode) {
		LanguageSettings.setLang(langCode);
		return ok();
	}  
}
