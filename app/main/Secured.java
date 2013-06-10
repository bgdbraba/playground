package models;

import models.contests.PlayedContest;
import models.contests.TeacherContest;
import models.users.BasicUser;
import models.users.Teacher;
import models.users.UserType;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import controllers.routes;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("id");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return redirect(routes.Application.login());
    }
    
}
