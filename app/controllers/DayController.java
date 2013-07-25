package controllers;

import models.day.FormulaDay;
import models.day.PlaygroundDay;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class DayController extends Controller{
	
	public static Result showPlaygroundDay(Long id){
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			
			return ok(views.html.day.playgroundDay.details.render(PlaygroundDay.find.byId(id)));

		}else{
			return forbidden();
		}
		
	}
	
	public static Result removePlaygroundDay(Long id){
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			Long playgroundId = PlaygroundDay.find.byId(id).playground.id;
			
			PlaygroundDay.remove(id);			
			
			return redirect(routes.PlaygroundController.history(playgroundId));

		}else{
			return forbidden();
		}
	}
	
	public static Result showFormulaDay(Long id){
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			
			return ok(views.html.day.formulaDay.details.render(FormulaDay.find.byId(id)));

		}else{
			return forbidden();
		}
		
	}

}
