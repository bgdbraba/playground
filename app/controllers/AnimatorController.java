package controllers;

import models.playground.Playground;
import models.users.Animator;
import models.users.Organizer;
import models.users.forms.AnimatorForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.DateConverter;
import conf.MyMessages;

@Security.Authenticated(Secured.class)
public class AnimatorController extends Controller{
	
	public static Result registerAnimator(){
		if (Secured.isOrganizer()) {
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			Form<AnimatorForm> filledForm = Form.form(AnimatorForm.class).bindFromRequest();
		
			long date = DateConverter.isCorrectDateOfBirth(filledForm.field("dateOfBirth").value());
			
			if (date == -1) {
				filledForm.reject("dateOfBirth",MyMessages.get("date.notadate"));
			} else if (date == -2) {
				filledForm.reject("dateOfBirth",MyMessages.get("date.toobig"));
			} else if (date == -3) {
				filledForm.reject("dateOfBirth", MyMessages.get("date.toosmall"));
			}

			if (filledForm.hasErrors()) {
				flash("fail", "register.animator.fail");
				return badRequest(views.html.users.animator.showAnimators.render(playground.animators, filledForm));
			} else {
	
				AnimatorForm animatorForm = filledForm.get();
				animatorForm.playgroundId = playground.id;				
				Animator animator = animatorForm.submit();
				
				flash("success", animator.id);
				
				return redirect(routes.AnimatorController.showAnimators());
			}
		} else {
			return forbidden();
		}
	}
	
	public static Result showDetails(String id){
		return TODO;
	}
	
	public static Result showAnimators(){
		if(Secured.isOrganizer()){
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			return ok(views.html.users.animator.showAnimators.render(playground.animators, Form.form(AnimatorForm.class)));
		}else{
			return forbidden();
		}
	}

}
