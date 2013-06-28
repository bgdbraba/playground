package controllers;

import models.playground.Activity;
import models.playground.Playground;
import models.playground.forms.ActivityForm;
import models.users.Organizer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.DateConverter;
import conf.MyMessages;

@Security.Authenticated(Secured.class)
public class ActivityController extends Controller{
	public static Result registerActivity(){
		if(Secured.isOrganizer()){
			
			Form<ActivityForm> filledForm = Form.form(ActivityForm.class).bindFromRequest();
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;
			
			if(DateConverter.parseDate(filledForm.field("beginDate").value()) > DateConverter.parseDate(filledForm.field("endDate").value()) ){
				filledForm.reject("beginDate", MyMessages.get("beginDate.bigger.than.endDate"));
				filledForm.reject("endDate", MyMessages.get("beginDate.bigger.than.endDate"));
			}else if(DateConverter.parseDate(filledForm.field("beginDate").value()) < DateConverter.getCurrentDate()){
				filledForm.reject("beginDate", MyMessages.get("date.in.past"));
			}
			
			
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.activity.fail"));
			
				return badRequest(views.html.playground.activity.showActivities.render(Activity.getActivitiesForPlayground(playground.id), filledForm));
							
			} else {
				flash("success", MyMessages.get("register.activity.success"));			
							
				ActivityForm activityForm = filledForm.get();
				activityForm.playgroundId = playground.id;
							
				activityForm.submit();
							
				return redirect(routes.ActivityController.showActivities());
			}
						
			
		}else{
			return forbidden();
		}
	}
	
	public static Result showActivities(){
		if(Secured.isOrganizer()){
			
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;
			
			return ok(views.html.playground.activity.showActivities.render(Activity.getActivitiesForPlayground(playground.id), Form.form(ActivityForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result editActivity(Long id){
		if(Secured.isOrganizer()){
			Form<ActivityForm> filledForm = Form.form(ActivityForm.class).bindFromRequest();
			Organizer organizer = Organizer.find.byId(request().username());
			
			Playground playground = organizer.playground;
			
			if(DateConverter.parseDate(filledForm.field("beginDate").value()) > DateConverter.parseDate(filledForm.field("endDate").value()) ){
				filledForm.reject("beginDate", MyMessages.get("beginDate.bigger.than.endDate"));
				filledForm.reject("endDate", MyMessages.get("beginDate.bigger.than.endDate"));
			}else if(DateConverter.parseDate(filledForm.field("beginDate").value()) < DateConverter.getCurrentDate()){
				filledForm.reject("beginDate", MyMessages.get("date.in.past"));
			}
			
			
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("edit.activity.fail"));
			
				return badRequest(views.html.playground.activity.details.render(Activity.find.byId(id), filledForm));
							
			} else {
				flash("success", MyMessages.get("edit.activity.success"));			
							
				ActivityForm activityForm = filledForm.get();
				activityForm.playgroundId = playground.id;
							
				activityForm.update();
							
				return redirect(routes.ActivityController.showDetails(id));
			}
		}else{
			return forbidden();
		}
	}
	
	/** iedereen mag eigenlijk de details zien alleen de knoppen om aan te passen niet en dergelijke ENKEL ORGANIZER MAG DEZE KNOPPEN ZIEN */
	
	public static Result showDetails(Long id){
		return ok(views.html.playground.activity.details.render(Activity.find.byId(id),Form.form(ActivityForm.class).fill(Activity.find.byId(id).toForm())));
	}
}
