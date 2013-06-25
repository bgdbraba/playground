package controllers;

import models.playground.Playground;
import models.playground.forms.FormulaForm;
import models.users.Organizer;
import models.users.BasicUser;
import models.users.forms.OrganizerForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class FormulaController extends Controller{
	
	public static Result registerFormula(){
		if(Secured.isOrganizer()){
			// ingelogd speelplein vastleggen in form
			Form<FormulaForm> filledForm = Form.form(FormulaForm.class).bindFromRequest();
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;			
			
			if (filledForm.hasErrors()) {
				flash("fail", "register.formula.fail");
				
				return badRequest(views.html.playground.formula.showFormulas.render(playground.formulas, filledForm));
				
			} else {
				flash("success", "register.formula.success");			
				
				FormulaForm formulaForm = filledForm.get();
				formulaForm.playgroundId = playground.id;
				
				formulaForm.submit();
				
				return redirect(routes.FormulaController.showFormulas());
			}
			
		}else{
			return forbidden();
		}
	}
	
	public static Result showFormulas(){
		if(Secured.isOrganizer()){
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;	
			
			return ok(views.html.playground.formula.showFormulas.render(playground.formulas, Form.form(FormulaForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result editFormula(Long id){
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
