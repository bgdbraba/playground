package controllers;



import models.Login;
import models.playground.Playground;
import models.users.Animator;
import models.users.BasicUser;
import models.users.Child;
import models.users.Organizer;
import models.users.enums.UserType;
import play.Routes;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import conf.LanguageSettings;
import conf.PasswordGenerator;

public class Application extends Controller {
  	
	public static Result index() {
        return ok(views.html.pages.home.render());
    }
	
	public static Result login() {
		if (session().containsKey("id")) {
			return redirect(routes.UserController.profile());
		} else {
			return ok(views.html.login.render(Form.form(Login.class)));
		}
    }
	

	public static Result authenticate() {
		Form<Login> filledForm = Form.form(Login.class).bindFromRequest();
		if (filledForm.hasErrors()) {
			return badRequest(views.html.login.render(filledForm));
		} else {
			session().clear();
			Login credentials = filledForm.get();
			session("id", credentials.id);
			
			LanguageSettings.setLang(BasicUser.find.byId(credentials.id).language.toString().toLowerCase());
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

	public static Result scribeOut(String childId){
		Child child = Child.find.byId(childId);

		Child.offPlayground(childId);

		return ok();
//		if (child.notPayed.compareTo(BigDecimal.ZERO) != 0) { // Child is still in debt.
//			return redirect(routes.ChildController.payment(childId));
//		} else {
//			return redirect(routes.PlaygroundController.showToday());
//		}
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
				routes.javascript.Application.scribeOut(),
				routes.javascript.Application.getGeneratedPassword()));
	}


	public static Playground getPlayground(){
		Playground playground;

		if(BasicUser.find.byId(session("id")).is(UserType.ORGANIZER)){

			Organizer organizer = Organizer.find.byId(session("id"));

			playground = organizer.playground;

		}else if(BasicUser.find.byId(session("id")).is(UserType.ANIMATOR)){
			Animator animator = Animator.find.byId(session("id"));
			playground = animator.playground;
		} else{
			Child child = Child.find.byId(session("id"));
			playground = child.playground;
		}

		return playground;
	}
}
