package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class FormulaController extends Controller{
	
	public static Result registerFormula(){
		if(Secured.isOrganizer()){
			// ingelogd speelplein vastleggen in form
			
			return TODO;
		}else{
			return forbidden();
		}
	}
	
	public static Result showFormulas(){
		if(Secured.isOrganizer()){
			// enkel van ingelogd speelplein
			
			return TODO;
		}else{
			return forbidden();
		}
	}

}
