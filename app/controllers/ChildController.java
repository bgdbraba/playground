package controllers;

import models.playground.Playground;
import models.users.Animator;
import models.users.Child;
import models.users.Organizer;
import models.users.forms.AnimatorForm;
import models.users.forms.ChildForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.DateConverter;
import conf.MyMessages;

@Security.Authenticated(Secured.class)
public class ChildController extends Controller{
	
	public static Result registerChild(){
		if (Secured.isAnimator() && Secured.hasAdministration()) {
			
			Animator animator = Animator.find.byId(request().username());
			
			Playground playground = animator.playground;	
			
			Form<ChildForm> filledForm = Form.form(ChildForm.class).bindFromRequest();
		
			if (filledForm.hasErrors()) {
				flash("fail", "register.child.fail");
			
				return badRequest(views.html.users.child.showChildren.render(playground.children, filledForm));
			
			} else {
	
				ChildForm childForm = filledForm.get();
				childForm.playgroundId = playground.id;				
				Child child = childForm.submit();
				
				flash("success", child.id);
				
				return redirect(routes.ChildController.showChildren());
			}
			
		} else {
			return forbidden();
		}
	}
	
	public static Result showChildren(){
		if(Secured.isAnimator() && Secured.hasAdministration()){
			Animator animator = Animator.find.byId(request().username());
			
			Playground playground = animator.playground;	
			
			return ok(views.html.users.child.showChildren.render(playground.children, Form.form(ChildForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result showDetails(String id){
		return TODO;
	}
	

	public static Result editChild(String id){
		return TODO;
	}

	public static Result deactivate(String childId) {
		return TODO;
	}

	public static Result activate(String childId) {
		return TODO;
	}

}
