package conf;

import java.util.HashMap;
import java.util.Map;

import play.i18n.Messages;

public enum Language {

	NL; //, FR, DE, EN;

	private static HashMap<String, Language> map = new HashMap<String, Language>();

	static {
		for (Language lang : values()) {
			map.put(lang.toString().toLowerCase(), lang);
		}
	}

	public static Map<String, String> options() {
		Map<String, String> map = new HashMap<String, String>();
		for (Language lang : values()) {
			map.put(lang.toString(), Messages.get(lang.toString()));
		}
		return map;
	}

	public static Language parse(String languageCode) {

		String lang = languageCode.toLowerCase();
		
		if(map.containsKey(lang)){
			return map.get(lang);
		}else{
			// if language not found default Dutch
			return NL;
		}		
	} 
}
