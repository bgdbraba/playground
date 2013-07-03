package controllers;

import java.util.Arrays;
import java.util.Map;

import models.day.ChildDay;
import models.day.PlaygroundDay;
import models.day.forms.DayForm;
import models.playground.Activity;
import models.playground.Formula;
import models.playground.Playground;
import models.playground.forms.LinkActivityForm;
import models.users.Animator;
import models.users.BasicUser;
import models.users.Child;
import models.users.forms.ChildForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.MyMessages;

@Security.Authenticated(Secured.class)
public class ChildController extends Controller{
	
	public static Result registerChild(){
		if (Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())) {
			
			Animator animator = Animator.find.byId(request().username());
			
			Playground playground = animator.playground;	
			
			Form<ChildForm> filledForm = Form.form(ChildForm.class).bindFromRequest();
		
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.child.fail"));
			
				return badRequest(views.html.users.child.showChildren.render(Child.getChildrenForPlayground(playground.id), filledForm));
			
			} else {
	
				ChildForm childForm = filledForm.get();
				childForm.playgroundId = playground.id;				
				Child child = childForm.submit();
				
				flash("success", child.id);
				
				return redirect(routes.ChildController.showChildren());
			}
			
		} else {
			return forbidden();
		}
	}
	
	public static Result showChildren(){
		if(Secured.isAnimator() && Secured.hasAdministration()){
			Animator animator = Animator.find.byId(request().username());
			
			Playground playground = animator.playground;	
			
			return ok(views.html.users.child.showChildren.render(Child.getChildrenForPlayground(playground.id), Form.form(ChildForm.class)));
		}else{
			return forbidden();
		}
	}
	
	public static Result showDetails(String id){
		if (Secured.isOrganizer() || (Secured.isAnimator() && Secured.hasAdministration())) {
			Child child = Child.find.byId(id);
			ChildForm editForm = child.toChildForm();
			
			return ok(views.html.users.child.details.render(child,Form.form(ChildForm.class).fill(editForm),Form.form(DayForm.class)));
		
		} else {
			return forbidden();
		}
	}
	

	public static Result editChild(String id){
		if (Secured.isAnimator() && Secured.hasAdministration()) {
			
			Animator animator = Animator.find.byId(request().username());
			
			Playground playground = animator.playground;	
			
			Form<ChildForm> filledForm = Form.form(ChildForm.class).bindFromRequest();
		
			if (filledForm.hasErrors()) {
				flash("fail", MyMessages.get("register.child.fail"));
			
				return badRequest(views.html.users.child.details.render(Child.find.byId(id), filledForm,Form.form(DayForm.class)));
			
			} else {
	
				ChildForm childForm = filledForm.get();
				childForm.playgroundId = playground.id;		
				childForm.id = id;
				childForm.addressId = Child.find.byId(id).address.id;
				
				childForm.update();
				
				flash("success", id);
				
				return redirect(routes.ChildController.showDetails(id));
			}
			
		} else {
			return forbidden();
		}
	}

	public static Result deactivate(String childId) {
		if (Secured.isAnimator() && Secured.hasAdministration()) {
			BasicUser.deactivate(childId);
			
			return redirect(routes.ChildController.showDetails(childId));
		} else {
			return forbidden();
		}
	}

	public static Result activate(String childId) {
		if (Secured.isAnimator() && Secured.hasAdministration()) {
			BasicUser.activate(childId);
			
			return redirect(routes.ChildController.showDetails(childId));
		} else {
			return forbidden();
		}
	}
	
	public static Result payment(String childId){
		return ok(views.html.users.child.payment.render(Child.find.byId(childId)));
	}
	
	public static Result scribeIn(String childId){
		
		Form<DayForm> filledForm = Form.form(DayForm.class).bindFromRequest();
		DayForm form = filledForm.get();
		form.childId = childId;
		
		// get request value from submitted form
	    Map<String, String[]> map = request().body().asFormUrlEncoded();
	    
	    if(map.size() > 0){
	    	String[] checkedVal = map.get("formula"); // get selected topics
	
		    // assign checked value to model
		    form.formulas = Arrays.asList(checkedVal);
	    } 
	 	    
	    form.submit();
	    
		return redirect(routes.ChildController.payment(childId));
	}
	
	public static Result scribeOut(String childId){
		Child child = Child.find.byId(childId);
		
		Child.offPlayground(childId);
		
		
		
		return redirect(routes.DayController.showPlaygroundDay(PlaygroundDay.findByPlayground(child.playground.id).id));
	}
	

	
	public static Result payNow(String childId){
		Animator animator = Animator.find.byId(request().username());
		
		Playground playground = animator.playground;
		
		PlaygroundDay playgroundDay = PlaygroundDay.findbyPlayground(playground.id);
		
		
		
		ChildDay day;
		
		if(ChildDay.exists(childId)){
			day = ChildDay.getCurrentChildDay(childId);
			
		}else{
			day = ChildDay.create();
			day.initialize(day.id);
		}
		
		day.amountPayed += Child.find.byId(childId).notPayed;
		day.update();
		
		PlaygroundDay.addMoney(playgroundDay.id, Child.find.byId(childId).notPayed);
		
		Child.payed(childId);
		
		return redirect(routes.ChildController.showChildren());
	}
	
	public static Result payLater(String childId){		
		return redirect(routes.ChildController.showChildren());
	}
	
	public static Result linkActivityToChild(String childId){
		if (Secured.isAnimator() && Secured.hasAdministration()) {
			
			Form<LinkActivityForm> filledForm = Form.form(LinkActivityForm.class).bindFromRequest();
			
			if (filledForm.hasErrors()) {
				flash("fail", "");
				
				return badRequest(views.html.users.child.linkActivity.render(Child.find.byId(childId), filledForm));
			
			} else if (Child.alreadyHasActivity(childId,filledForm.get().activityId)) {
				flash("fail", "");
				
				return badRequest(views.html.users.child.linkActivity.render(Child.find.byId(childId), filledForm));
			} else if (Activity.full(filledForm.get().activityId)) {
				flash("fail", MyMessages.get("activity.full"));
				
				return badRequest(views.html.users.child.linkActivity.render(Child.find.byId(childId), filledForm));
			} else{
				
				LinkActivityForm form = filledForm.get();
				form.childId = childId;
				form.submit();
				Child.addNotPayed(childId, Activity.find.byId(form.activityId).cost);
				flash("success", "");
				
				return redirect(routes.ChildController.payment(childId));
			}
		} else {
			return forbidden();
		}	
	}
	
	public static Result linkedActivities(String childId){
		if (Secured.isAnimator() && Secured.hasAdministration()) {
				return ok(views.html.users.child.linkActivity.render(Child.find.byId(childId),Form.form(LinkActivityForm.class)));
		} else {
			return forbidden();
		}	
	}
	
	public static Result renewSessionCard(String childId){
		if (Secured.isAnimator() && Secured.hasAdministration()) {
			Child.renewSessionCard(childId);
			Child.addNotPayed(childId, Child.find.byId(childId).playground.sessionCard.cost);
			
			return redirect(routes.ChildController.showDetails(childId));
		} else {
			return forbidden();
		}
	}
}
