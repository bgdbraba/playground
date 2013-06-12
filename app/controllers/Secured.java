package controllers;

import models.users.User;
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
    	return User.is(Http.Context.current().request().username(),UserType.ADMIN);
    }
    
    public static boolean isOrganizer() {
    	return User.is(Http.Context.current().request().username(),UserType.ORGANIZER);
    }
    
    public static boolean isChild() {
    	return User.is(Http.Context.current().request().username(),UserType.CHILD);
    }
    
    public static boolean isAnimator() {
    	return User.is(Http.Context.current().request().username(),UserType.ANIMATOR);
    }
    
}
