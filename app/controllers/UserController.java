package controllers;

import models.users.User;
import models.users.forms.UserForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import conf.DateConverter;
import conf.LanguageSettings;
import conf.MyMessages;

public class UserController extends Controller{
	
	@Security.Authenticated(Secured.class)
	public static Result profile(){
		
		if(!session().containsKey("id")){
			return badRequest();
		}else{
			
			User user = User.find.byId(request().username());
			
			UserForm editForm = user.toUserForm();
			
			return ok(views.html.users.profile.render(user, Form.form(UserForm.class).fill(editForm)));
		}
	}
	
	public static Result showUsers(){
		return TODO;
	}
	
	public static Result editUser(){
		Form<UserForm> editedForm = Form.form(UserForm.class).bindFromRequest();
		
		if (!editedForm.field("password1").value().equals(editedForm.field("password2").value())) {
			editedForm.reject("password2",MyMessages.get("password.mismatch"));
		}	
		
		if (editedForm.hasErrors()) {
			flash("fail", "edit.fail");
			
			User user = User.find.byId(session("id"));
			
			return badRequest(views.html.users.profile.render(user, editedForm));
		
		} else {
			flash("success", "edit.success");
			editedForm.get().update(request().username());
			User user = User.find.byId(session("id"));

			LanguageSettings.setLang(user.language.toString().toLowerCase());
			
			return ok(views.html.users.profile.render(user, editedForm));
		}
	}
}
