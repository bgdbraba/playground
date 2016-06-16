package controllers;

import conf.MyMessages;
import models.playground.Playground;
import models.playground.forms.PlaygroundForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class PlaygroundController extends Controller{

	public static Result showPlaygrounds() {
		if (Secured.isAdmin()) {
			return ok(views.html.playground.showPlaygrounds.render(Playground.find.all(), Form.form(PlaygroundForm.class)));
		} else {
			return forbidden();
		}
	}
	
	public static Result registerPlayground() {
		if (Secured.isAdmin()) {
			Form<PlaygroundForm> filledForm = Form.form(PlaygroundForm.class).bindFromRequest();
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.playground.fail"));
				return badRequest(views.html.playground.showPlaygrounds.render(Playground.find.all(), filledForm));
			} else {
				flash("success", MyMessages.get("register.playground.success"));
				
				filledForm.get().create();
				
				return redirect(routes.PlaygroundController.showPlaygrounds());
			}
		
		} else {
			return forbidden();
		}
	}
	
	public static Result showDetails(Long id) {
		if (Secured.isAdmin()) {
			Playground playground = Playground.find.byId(id);
			return ok(views.html.playground.details.render(playground,
					Form.form(PlaygroundForm.class).fill(playground.toForm())));
		} else {
			return forbidden();
		}
	}
	
	public static Result editPlayground(Long id) {
		if (Secured.isAdmin()) {
			Form<PlaygroundForm> editedForm = Form.form(PlaygroundForm.class).bindFromRequest();
			
			if (editedForm.hasErrors()) {
				flash("fail", MyMessages.get("edit.playground.fail"));
				return badRequest(views.html.playground.details.render(Playground.find.byId(id), editedForm));
			} else {
				flash("success", MyMessages.get("edit.playground.succes"));
				PlaygroundForm playgroundForm = editedForm.get();
				playgroundForm.update(id);
				
				return redirect(routes.PlaygroundController.showDetails(id));
			}
		} else {
			return forbidden();
		}
	}
	
	public static Result showToday(Long playgroundId){
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			
				return ok(views.html.day.playgroundToday.render(Playground.find.byId(playgroundId)));

		}else{
			return forbidden();
		}
	}
	
	public static Result history(Long playgroundId){
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			
			return ok(views.html.day.history.render(Playground.find.byId(playgroundId)));

		}else{
			return forbidden();
		}
	}

	public static Result owedMoney(Long playgroundId) {
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			// Get list of children owing money.
			// Present list.
			return ok(views.html.playground.owedMoney.owedMoney.render(Playground.getListOfChildrenInDebt(playgroundId)));

		}else{
			return forbidden();
		}
	}
}
