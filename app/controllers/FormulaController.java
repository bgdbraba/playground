package controllers;

import conf.MyMessages;
import models.playground.Formula;
import models.playground.Playground;
import models.playground.forms.FormulaForm;
import models.users.Organizer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class FormulaController extends Controller{
	
	public static Result registerFormula(){
		if(Secured.isOrganizer()){
			Form<FormulaForm> filledForm = Form.form(FormulaForm.class).bindFromRequest();
			
			Organizer organizer = Organizer.find.byId(request().username());

			Playground playground = organizer.playground;			
			
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.formula.fail"));
				
				return badRequest(views.html.playground.formula.showFormulas.render(Formula.getFormulasForPlayground(playground.id), filledForm));
				
			} else {
				flash("success", MyMessages.get("register.formula.success"));			
				
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
			
			return ok(views.html.playground.formula.showFormulas.render(Formula.getFormulasForPlayground(playground.id), Form.form(FormulaForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result editFormula(Long id){
		if(Secured.isOrganizer()){
			Form<FormulaForm> filledForm = Form.form(FormulaForm.class).bindFromRequest();
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;			
			
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.formula.fail"));
				
				return badRequest(views.html.playground.formula.details.render(Formula.find.byId(id), filledForm));
				
			} else {
				flash("success", MyMessages.get("register.formula.success"));			
				
				FormulaForm formulaForm = filledForm.get();
				formulaForm.playgroundId = playground.id;
				formulaForm.id = id;
				
				formulaForm.update();
				
				return redirect(routes.FormulaController.showDetails(id));
			}
			
		}else{
			return forbidden();
		}
	}
	
	public static Result showDetails(Long id){
		if(Secured.isOrganizer()){
			return ok(views.html.playground.formula.details.render(Formula.find.byId(id), Form.form(FormulaForm.class).fill(Formula.find.byId(id).toForm())));
		}else{
			return forbidden();
		}
	}

    public static Result removeFormula(Long formulaId){
        if(Secured.isOrganizer()){
			Formula.deactivate(formulaId);
			Organizer organizer = Organizer.find.byId(request().username());

            Playground playground = organizer.playground;

            return ok(views.html.playground.formula.showFormulas.render(Formula.getFormulasForPlayground(playground.id), Form.form(FormulaForm.class)));
        }else{
            return forbidden();
        }
    }
	
	public static Result getFormulasForPlayground(Long playgroundId){
		return TODO;
	}

}
