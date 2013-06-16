package controllers;

import models.playground.forms.LinkPlaygroundForm;
import models.users.Organizer;
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
		return TODO;
	}
	
	public static Result registerOrganizer(){
		if (Secured.isAdmin()) {
			Form<OrganizerForm> filledForm = Form.form(OrganizerForm.class).bindFromRequest();

			long date = DateConverter.isCorrectDateOfBirth(filledForm.field("dateOfBirth").value());
			if (date == -1) {
				filledForm.reject("dateOfBirth",MyMessages.get("date.notadate"));
			} else if (date == -2) {
				filledForm.reject("dateOfBirth",MyMessages.get("date.toobig"));
			} else if (date == -3) {
				filledForm.reject("dateOfBirth", MyMessages.get("date.toosmall"));
			}

			if (filledForm.hasErrors()) {
				flash("fail", "register.organizer.fail");
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
	
	public static Result linkPlaygroundPage(String organizerId) {
		if (Secured.isAdmin()) {
			return TODO;
		} else {
			return forbidden();
		}
	}

	public static Result linkPlaygroundToOrganizer(String organizerId) {
		if (Secured.isAdmin()) {
			Form<LinkPlaygroundForm> filledForm = Form.form(LinkPlaygroundForm.class).bindFromRequest();
			if (filledForm.hasErrors()) {
				flash("fail", "");
				return badRequest(views.html.users.organizer.linkPlayground.render(Organizer.find.byId(organizerId), filledForm));
				
			} else if (Organizer.alreadyHasPlayground(organizerId,filledForm.get().playgroundId)) {
				flash("fail", "");
				return badRequest(views.html.users.organizer.linkPlayground.render(Organizer.find.byId(organizerId), filledForm));
				
			} else {
				LinkPlaygroundForm form = filledForm.get();
				form.organizerId = organizerId;
				form.submit();
				flash("success", "");
				
				return redirect(routes.OrganizerController.linkPlaygroundPage(organizerId));
			}
		} else {
			return forbidden();
		}
	}

	public static Result deactivate(String organizerId) {
		if (Secured.isAdmin()) {
			return TODO;
		} else {
			return forbidden();
		}
	}

	public static Result activate(String organizerId) {
		if (Secured.isAdmin()) {
			return TODO;
		} else {
			return forbidden();
		}
	}


}
