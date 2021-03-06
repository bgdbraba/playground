package models;

import models.users.BasicUser;
import play.i18n.Messages;

public class Login {

	public String id;
	public String password;

	public String validate() {
		String result = null;
		
		if (BasicUser.authenticate(id, password) == null) {
			result = Messages.get("login.fail");
		}
		
		return result;
	}
}
