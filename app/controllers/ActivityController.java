package controllers;

import models.playground.Playground;
import models.playground.forms.ActivityForm;
import models.playground.forms.FormulaForm;
import models.users.Animator;
import models.users.Organizer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class ActivityController extends Controller{
	public static Result registerActivity(){
		if(Secured.isAnimator() && Secured.hasAdministration()){
			// ingelogd speelplein vastleggen in form
			Form<ActivityForm> filledForm = Form.form(ActivityForm.class).bindFromRequest();
			Animator animator = Animator.find.byId(request().username());
			
			Playground playground = animator.playground;			
			
			if (filledForm.hasErrors()) {
				flash("fail", "register.formula.fail");
			
				return badRequest(views.html.playground.activity.showActivities.render(playground.activities, filledForm));
							
			} else {
				flash("success", "register.formula.success");			
							
				ActivityForm activityForm = filledForm.get();
				activityForm.playgroundId = playground.id;
							
				activityForm.submit();
							
				return redirect(routes.ActivityController.showActivities());
			}
						
			
		}else{
			return forbidden();
		}
	}
	
	public static Result showActivities(){
		if(Secured.isAnimator() && Secured.hasAdministration()){
			Animator animator = Animator.find.byId(request().username());
			
			Playground playground = animator.playground;	
			
			return ok(views.html.playground.activity.showActivities.render(playground.activities, Form.form(ActivityForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result editActivity(Long id){
		if(Secured.isAnimator() && Secured.hasAdministration()){
			// enkel van ingelogd speelplein
			
			return TODO;
		}else{
			return forbidden();
		}
	}
	
	public static Result showDetails(Long id){
		if(Secured.isAnimator() && Secured.hasAdministration()){
			// enkel van ingelogd speelplein
			
			return TODO;
		}else{
			return forbidden();
		}
	}
}
