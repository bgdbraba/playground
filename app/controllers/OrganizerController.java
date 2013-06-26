package controllers;

import models.users.Organizer;
import models.users.BasicUser;
import models.users.forms.OrganizerForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.DateConverter;
import conf.MyMessages;

@Security.Authenticated(Secured.class)
public class OrganizerController extends Controller{
	
	public static Result showOrganizers(){
		if (Secured.isAdmin()) {
			return ok(views.html.users.organizer.showOrganizers.render(Organizer.find.all(),Form.form(OrganizerForm.class)));
		} else {
			return forbidden();
		}
	}
	
	public static Result showDetails(String id){
		if (Secured.isAdmin()) {
			Organizer organizer = Organizer.find.byId(id);
			OrganizerForm editForm = organizer.toForm();
			return ok(views.html.users.organizer.details.render(organizer,
					Form.form(OrganizerForm.class).fill(editForm)));
		} else {
			return forbidden();
		}
	}
	
	public static Result registerOrganizer(){
		if (Secured.isAdmin()) {
			Form<OrganizerForm> filledForm = Form.form(OrganizerForm.class).bindFromRequest();

			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.organizer.fail"));
				return badRequest(views.html.users.organizer.showOrganizers.render(Organizer.find.all(), filledForm));
			} else {
				Organizer organizer = filledForm.get().submit();
				flash("success", organizer.id);
				return redirect(routes.OrganizerController.showOrganizers());
			}
		} else {
			return forbidden();
		}
	}
	
	public static Result editOrganizer(String id){
		return TODO;
	}

	public static Result deactivate(String organizerId) {
		if (Secured.isAdmin()) {
			BasicUser.deactivate(organizerId);
			return redirect(routes.OrganizerController.showDetails(organizerId));
		} else {
			return forbidden();
		}
	}

	public static Result activate(String organizerId) {
		if (Secured.isAdmin()) {
			BasicUser.activate(organizerId);
			return redirect(routes.OrganizerController.showDetails(organizerId));
		} else {
			return forbidden();
		}
	}


}
