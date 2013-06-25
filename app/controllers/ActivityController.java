package controllers;

import models.playground.Playground;
import models.playground.forms.FormulaForm;
import models.users.Organizer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class ActivityController extends Controller{
	public static Result registerActivity(){
		if(Secured.isOrganizer()){
			return TODO;
			
		}else{
			return forbidden();
		}
	}
	
	public static Result showActivities(){
		if(Secured.isOrganizer()){
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			return ok(views.html.playground.formula.showFormulas.render(playground.formulas, Form.form(FormulaForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result editActivity(Long id){
		if(Secured.isOrganizer()){
			// enkel van ingelogd speelplein
			
			return TODO;
		}else{
			return forbidden();
		}
	}
	
	public static Result showDetails(Long id){
		if(Secured.isOrganizer()){
			// enkel van ingelogd speelplein
			
			return TODO;
		}else{
			return forbidden();
		}
	}
}
