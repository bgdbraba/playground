package controllers;

import conf.MyMessages;
import models.users.Child;
import models.users.forms.ChildForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class PagesController extends Controller{ 
	
	public static Result about(){
		return ok(views.html.pages.about.render());
	}
	
	public static Result contact(){
		return ok(views.html.pages.contact.render());
	}

	public static Result register() {
		return ok(views.html.pages.register.render(Form.form(ChildForm.class)));
	}


	public static Result registerAndLinkChild() {
		Form<ChildForm> filledForm = Form.form(ChildForm.class).bindFromRequest();

			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.child.fail"));
				return badRequest(views.html.pages.register.render(filledForm));

			} else {

				Child child = filledForm.get().submit();
				flash("success","Geregistreerd met de gebruikersnaam: " + child.id);

				return redirect(routes.PagesController.register());
			}
	}
}
