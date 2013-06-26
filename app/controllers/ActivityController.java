package controllers;

import conf.MyMessages;
import models.playground.Playground;
import models.playground.forms.ActivityForm;
import models.playground.forms.FormulaForm;
import models.users.Animator;
import models.users.BasicUser;
import models.users.Organizer;
import models.users.enums.UserType;
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
				flash("fail", MyMessages.get("register.activity.fail"));
			
				return badRequest(views.html.playground.activity.showActivities.render(playground.activities, filledForm));
							
			} else {
				flash("success", MyMessages.get("register.activity.success"));			
							
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
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			Playground playground;
			
			if(BasicUser.find.byId(request().username()).is(UserType.ANIMATOR)){
				Animator animator = Animator.find.byId(request().username());
				playground = animator.playground;
			}else{
				Organizer organizer = Organizer.find.byId(request().username());
				playground = organizer.playground;
			}
			
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
