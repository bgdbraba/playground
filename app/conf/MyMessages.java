package conf;

import play.i18n.Messages;

public class MyMessages {

	public static String get(String key){
		return Messages.get(LanguageSettings.getLang(), key);
	}
	
}
