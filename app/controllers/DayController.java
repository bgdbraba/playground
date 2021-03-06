package controllers;

import models.day.ChildDay;
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
			
			for(FormulaDay day : PlaygroundDay.find.byId(id).formulaDays){
				FormulaDay.remove(day.id);
			}
			
			PlaygroundDay.remove(id);		

			return redirect(routes.PlaygroundController.history());

		}else{
			return forbidden();
		}
	}
	
	public static Result removeChildDay(Long id){
		if(Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())){
			String childId = ChildDay.find.byId(id).child.id;

			ChildDay.remove(id);		
					
			return redirect(routes.ChildController.showDetails(childId));

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
