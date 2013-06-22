package conf;

import java.util.Random;

public class PasswordGenerator {

	private static int PW_LENGTH = 8;
    private static String characters ="abcdefghijklmnopqrstuvwxyz0123456789";
    private PasswordGenerator(){};
	
    
    public static String generate() {
    	String result = "";
    	Random r = new Random();
    	
    	for(int i = 0; i < PW_LENGTH; i++) {
    		int index = r.nextInt(characters.length());
    		result += characters.substring(index, index+1);
    	}
    	return result;
    }
	
}
