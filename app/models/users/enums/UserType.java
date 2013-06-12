package models.users.enums;

import com.avaje.ebean.annotation.EnumMapping;

@EnumMapping(nameValuePairs="CHILD=child, ANIMATOR=animator, ORGANIZER=organizer, ADMIN=admin")
public enum UserType {

	CHILD,
	ANIMATOR,
	ORGANIZER,
	ADMIN;
}
