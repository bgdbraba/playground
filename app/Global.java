import java.util.List;

import models.users.User;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;
import play.mvc.Http.RequestHeader;
import play.mvc.Result;
import play.mvc.Results;

import com.avaje.ebean.Ebean;


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
		
		 // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            Ebean.save((List) Yaml.load("data.yml"));

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
