package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class PagesController extends Controller{
	
	public static Result about(){
		return ok(views.html.pages.about.render());
	}
	
	public static Result contact(){
		return ok(views.html.pages.contact.render());
	}


}
