package models.users.enums;

import com.avaje.ebean.annotation.EnumMapping;

@EnumMapping(nameValuePairs="ADMIN=admin, ORGANIZER=organizer, ANIMATOR=animator, CHILD=child")
public enum UserType {

	CHILD,
	ANIMATOR,
	ORGANIZER,
	ADMIN;
}
