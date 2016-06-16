package controllers;

import conf.MyMessages;
import models.playground.Playground;
import models.playground.SessionCard;
import models.playground.forms.SessionCardForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class SessionCardController extends Controller{
	
	public static Result registerSessionCard(){
		if(Secured.isOrganizer()){
			
			Form<SessionCardForm> filledForm = Form.form(SessionCardForm.class).bindFromRequest();
			
			Playground playground = Application.getPlayground();
			
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.fail"));
			
				return badRequest(views.html.playground.sessionCard.showSessionCard.render(SessionCard.getSessionCardByPlayground(playground.id), filledForm));
							
			} else {
				flash("success", MyMessages.get("register.success"));			
							
				SessionCardForm sessionCardForm = filledForm.get();
				sessionCardForm.playgroundId = playground.id;
							
				sessionCardForm.submit();
							
				return redirect(routes.SessionCardController.showSessionCard());
			}
						
			
		}else{
			return forbidden();
		}
	}
	
	public static Result editSessionCard(){
		if(Secured.isOrganizer()){
			Form<SessionCardForm> filledForm = Form.form(SessionCardForm.class).bindFromRequest();
			
			Playground playground = Application.getPlayground();
						
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("edit.fail"));
			
				return badRequest(views.html.playground.sessionCard.showSessionCard.render(SessionCard.getSessionCardByPlayground(playground.id), filledForm));
							
			} else {
				flash("success", MyMessages.get("edit.success"));			
							
				SessionCardForm sessionCardForm = filledForm.get();
				sessionCardForm.playgroundId = playground.id;
				sessionCardForm.id = SessionCard.getSessionCardByPlayground(playground.id).id;
							
				sessionCardForm.update();
							
				return redirect(routes.SessionCardController.showSessionCard());
			}
		}else{
			return forbidden();
		}
	}
	
	public static Result showSessionCard(){
		if(Secured.isOrganizer()){
			
			Playground playground = Application.getPlayground();
			
			return ok(views.html.playground.sessionCard.showSessionCard.render(SessionCard.getSessionCardByPlayground(playground.id), Form.form(SessionCardForm.class).fill(playground.sessionCard.toForm())));
		}else{
			return forbidden();
		}
	}
	
	public static Result deactivate() {
		if (Secured.isOrganizer()) {

			Playground playground = Application.getPlayground();
			
			SessionCard.deactivate(SessionCard.getSessionCardByPlayground(playground.id).id);
			
			return redirect(routes.SessionCardController.showSessionCard());
		} else {
			return forbidden();
		}
	}

	public static Result activate() {
		if (Secured.isOrganizer()) {
			
			Playground playground = Application.getPlayground();

			SessionCard.activate(SessionCard.getSessionCardByPlayground(playground.id).id);
			
			return redirect(routes.SessionCardController.showSessionCard());
		} else {
			return forbidden();
		}
	}
}
