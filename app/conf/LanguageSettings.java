package conf;

import java.util.HashMap;
import java.util.Map;

import play.i18n.Lang;
import play.mvc.Http;


public class LanguageSettings {
	
	/** Language files for datatables */
	private static final Map<Lang,String> datatables = new HashMap<Lang,String>();
	
	static{
		for(Lang lang : Lang.availables()){
			datatables.put(lang, "datatablelanguages/datatables."+lang.code().toLowerCase()+".txt");
		}
	}

    public static Lang getLang(){
        String langCode = Http.Context.current().session().get("language");
        
        Lang lang = Lang.forCode("nl");
        
        if(langCode != null && !langCode.isEmpty()) {
        	lang = Lang.forCode(langCode); 
        }
        return lang;
    }
    
    public static void setLang(String langCode){
    	Http.Context.current().session().put("language", langCode);
    }
    
    public static String getLanguageAsString(){
    	return getLang().language();
    }
    
    public static Language getLanguageAsEnum(){
    	return Language.parse(getLanguageAsString());
    }
    
    public static String getDatatableURL(){
    	return datatables.get(getLang());
    }
}
