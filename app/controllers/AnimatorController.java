package controllers;

import conf.MyMessages;
import models.playground.Playground;
import models.users.Animator;
import models.users.Organizer;
import models.users.BasicUser;
import models.users.forms.AnimatorForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class AnimatorController extends Controller{
	
	public static Result registerAnimator(){
		if (Secured.isOrganizer()) {
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			Form<AnimatorForm> filledForm = Form.form(AnimatorForm.class).bindFromRequest();
		
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.animator.fail"));
				return badRequest(views.html.users.animator.showAnimators.render(Animator.getAnimatorsForPlayground(playground.id), filledForm));
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
		if (Secured.isOrganizer() && Secured.samePlayground(id) ) {
			Animator animator = Animator.find.byId(id);
			AnimatorForm editForm = animator.toAnimatorForm();
			return ok(views.html.users.animator.details.render(animator,Form.form(AnimatorForm.class).fill(editForm)));
		
		} else {
			return forbidden();
		}
	}
	
	public static Result showAnimators(){
		if(Secured.isOrganizer()){
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			return ok(views.html.users.animator.showAnimators.render(Animator.getAnimatorsForPlayground(playground.id), Form.form(AnimatorForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result giveAdministration(String animatorId){
		if (Secured.isOrganizer() && Secured.samePlayground(animatorId)) {
			if(Animator.alreadyAdministrationInPlayground(Animator.find.byId(animatorId).playground.id)){
				return redirect(routes.AnimatorController.showDetails(animatorId));
			}else{
				Animator.grantAdministration(animatorId);
				return redirect(routes.AnimatorController.showDetails(animatorId));
			}
		} else {
			return forbidden();
		}
	}
	
	public static Result takeAwayAdministration(String animatorId){
		if (Secured.isOrganizer() && Secured.samePlayground(animatorId)) {
			Animator.forbidAdministration(animatorId);
			return redirect(routes.AnimatorController.showDetails(animatorId));
		} else {
			return forbidden();
		}
	}
	
	public static Result deactivate(String animatorId) {
		if (Secured.isOrganizer() && Secured.samePlayground(animatorId)) {
			BasicUser.deactivate(animatorId);
			
			return redirect(routes.AnimatorController.showDetails(animatorId));
		} else {
			return forbidden();
		}
	}

	public static Result activate(String animatorId) {
		if (Secured.isOrganizer() && Secured.samePlayground(animatorId)) {
			BasicUser.activate(animatorId);
			
			return redirect(routes.AnimatorController.showDetails(animatorId));
		} else {
			return forbidden();
		}
	}
	
	public static Result editAnimator(String id){
		if (Secured.isOrganizer()) {
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			Form<AnimatorForm> filledForm = Form.form(AnimatorForm.class).bindFromRequest();
		
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.animator.fail"));
				return badRequest(views.html.users.animator.details.render(Animator.find.byId(id),filledForm));
			} else {
	
				AnimatorForm animatorForm = filledForm.get();
				animatorForm.playgroundId = playground.id;	
				animatorForm.id = id;
				animatorForm.addressId = Animator.find.byId(id).address.id;
				animatorForm.update();
				
				flash("success", id);
				
				return redirect(routes.AnimatorController.showDetails(id));
			}
		} else {
			return forbidden();
		}
	}


}
