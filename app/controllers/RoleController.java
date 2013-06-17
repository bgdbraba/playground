package controllers;

import models.playground.Playground;
import models.playground.forms.FormulaForm;
import models.playground.forms.RoleForm;
import models.users.Organizer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class RoleController extends Controller{
	
	public static Result registerRole(){
		if(Secured.isOrganizer()){
			// ingelogd speelplein vastleggen in form
			Form<RoleForm> filledForm = Form.form(RoleForm.class).bindFromRequest();
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;			
			
			if (filledForm.hasErrors()) {
				flash("fail", "register.formula.fail");
				return badRequest(views.html.playground.role.showRoles.render(playground.roles, filledForm));
			} else {
				flash("success", "register.formula.success");			
				
				RoleForm roleForm = filledForm.get();
				roleForm.playgroundId = playground.id;
				
				roleForm.submit();
				
				return redirect(routes.RoleController.showRoles());
			}
			
		}else{
			return forbidden();
		}
	}
	
	public static Result showRoles(){
		if(Secured.isOrganizer()){
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			return ok(views.html.playground.role.showRoles.render(playground.roles, Form.form(RoleForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result editRole(Long id){
		if(Secured.isOrganizer()){
			// enkel van ingelogd speelplein
			
			return TODO;
		}else{
			return forbidden();
		}
	}
	
	public static Result showDetails(Long id){
		if(Secured.isOrganizer()){
			// enkel van ingelogd speelplein
			
			return TODO;
		}else{
			return forbidden();
		}
	}

}
