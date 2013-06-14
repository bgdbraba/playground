package controllers;

import conf.MyMessages;
import models.playground.Playground;
import models.playground.forms.PlaygroundForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class PlaygroundController extends Controller{
	
	public static Result showPlaygrounds() {
		if (Secured.isAdmin()) {
			return ok(views.html.playground.showPlaygrounds.render(Playground.find.all(), Form.form(PlaygroundForm.class)));
		} else {
			return forbidden();
		}
	}
	
	public static Result registerPlayground() {
		if (Secured.isAdmin()) {
			Form<PlaygroundForm> filledForm = Form.form(PlaygroundForm.class).bindFromRequest();
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.playground.fail"));
			return badRequest(views.html.playground.showPlaygrounds.render(Playground.find.all(), filledForm));
			} else {
				flash("success", MyMessages.get("register.playground.success"));
				filledForm.get().create();
				
				return redirect(routes.PlaygroundController.showPlaygrounds());
			}
		
		} else {
			return forbidden();
		}
	}
	
	public static Result showDetails(Long id) {
		if (Secured.isAdmin()) {
			return TODO;
//			School school = School.find.byId(id);
//			return ok(views.html.schools.school.details.render(school,
//					form(School.class).fill(school)));
		} else {
			return forbidden();
		}
	}
	
	public static Result editPlayground(Long id) {
		if (Secured.isAdmin()) {
			return TODO;
//			Form<School> editedForm = form(School.class).bindFromRequest();
//			if (editedForm.hasErrors()) {
//				flash("fail", "");
//				return badRequest(views.html.schools.school.details.render(
//						School.find.byId(id), editedForm));
//			} else {
//				flash("success", "");
//				School editedSchool = editedForm.get();
//				editedSchool.schoolId = id;
//				editedSchool.update();
//				return redirect(routes.SchoolController.showDetails(id));
//			}
		} else {
			return forbidden();
		}
	}
	
	

}
