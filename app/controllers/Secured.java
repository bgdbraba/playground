package controllers;

import models.users.Animator;
import models.users.Organizer;
import models.users.BasicUser;
import models.users.enums.UserType;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("id");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(routes.Application.login());
    }
    
    public static boolean isAdmin() {
    	return BasicUser.is(Http.Context.current().request().username(),UserType.ADMIN);
    }
    
    public static boolean isOrganizer() {
    	return BasicUser.is(Http.Context.current().request().username(),UserType.ORGANIZER);
    }
    
    public static boolean isChild() {
    	return BasicUser.is(Http.Context.current().request().username(),UserType.CHILD);
    }
    
    public static boolean isAnimator() {
    	return BasicUser.is(Http.Context.current().request().username(),UserType.ANIMATOR);
    }
    
    public static boolean hasAdministration(){
    	return Animator.find.byId(Http.Context.current().request().username()).administration;
    }

	public static boolean samePlayground(String animatorId) {
		return Organizer.find.byId(Http.Context.current().request().username()).playground.equals(Animator.find.byId(animatorId).playground);
	}
}
