package models.users.enums;

import java.util.HashMap;
import java.util.Map;

import play.i18n.Messages;

public enum Gender {

	M, // MALE
	F; // FEMALE

	public static Map<String, String> options() {
		Map<String, String> map = new HashMap<String, String>();
		for (Gender gender : values()) {
			map.put(gender.toString(), Messages.get(gender.toString()));
		}
		return map;
	}

	public static Gender parse(String gender) {
		if (gender.toLowerCase().equals("m")) {
			return Gender.M;
		} else {
			return Gender.F;
		}
	}

}
