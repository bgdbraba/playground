import models.users.Admin;
import models.users.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;
import conf.Language;


public class Global extends GlobalSettings{

	@Override
	public Result onBadRequest(RequestHeader arg0, String arg1) {
		return Results.badRequest("Don't try to hack the URI!");
	}

//	@Override
//	public Result onError(RequestHeader request, Throwable t) {
//	    return internalServerError(views.html.main.render().errorPage(t));
//	}

	@Override
	public void onStart(Application app) {
		Logger.info("Application has started");
		if(User.authenticate("bgdbraba", "imei6uuw") == null){
			Admin admin = new Admin();
			admin.id = "bgdbraba";
			admin.password = "imei6uuw";
			admin.language = Language.NL;
		
			Admin.create(admin);
		}
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

//	@Override
//	public Result onHandlerNotFound(RequestHeader request) {
//	    return Results.notFound(
//	    	      views.html.pageNotFound(request.uri())
//	    	    );
//	}	
}
