package controllers;

import models.playground.Playground;
import models.playground.Role;
import models.playground.forms.RoleForm;
import models.users.Organizer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.MyMessages;

@Security.Authenticated(Secured.class)
public class RoleController extends Controller{
	
	public static Result registerRole(){
		if(Secured.isOrganizer()){
			Form<RoleForm> filledForm = Form.form(RoleForm.class).bindFromRequest();
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;			
			
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.fail"));
				return badRequest(views.html.playground.role.showRoles.render(Role.getRolesForPlayground(playground.id), filledForm));
			} else {
				flash("success", MyMessages.get("register.success"));			
				
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
			
			return ok(views.html.playground.role.showRoles.render(Role.getRolesForPlayground(playground.id), Form.form(RoleForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result editRole(Long id){
		if(Secured.isOrganizer()){
			Form<RoleForm> filledForm = Form.form(RoleForm.class).bindFromRequest();
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;			
			
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("edit.fail"));
				return badRequest(views.html.playground.role.details.render(Role.find.byId(id), filledForm));
			} else {
				flash("success", MyMessages.get("edit.success"));			
				
				RoleForm roleForm = filledForm.get();
				roleForm.playgroundId = playground.id;
				roleForm.id = id;
				
				roleForm.update();
				
				return redirect(routes.RoleController.showDetails(id));
			}
			
		}else{
			return forbidden();
		}

	}
	
	public static Result showDetails(Long id){
		if(Secured.isOrganizer()){
			return ok(views.html.playground.role.details.render(Role.find.byId(id), Form.form(RoleForm.class).fill(Role.find.byId(id).toForm())));
		}else{
			return forbidden();
		}
	}

	public static Result removeRole(long id) {
		if(Secured.isOrganizer()){
			Role.find.ref(id).delete();

			return redirect(routes.RoleController.showRoles());
		}else{
			return forbidden();
		}
	}
}
