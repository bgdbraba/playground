package conf;

import models.users.BasicUser;


public class IdGenerator{
	
	private static int ID_LENGTH = 8;
    private static String characters ="abcdefghijklmnopqrstuvwxyz";
    private IdGenerator(){};

    public static String generate(String first, String last){
        String id = "";
        first=first.replaceAll("\\s","");
        last=last.replaceAll("\\s","");
        if(first.length() >= 2){
            //in case first name is long enough: only first 2 letters
            id=id.concat(first.substring(0, 2));
        }else{
            //otherwise just entire first name
            id=id.concat(first);
        }
        if(last.length() >= 4){
            //in case last name is long enough: only 4 first letters
            id=id.concat(last.substring(0, 4));
        }else{
            //otherwise just entire last name
            id=id.concat(last);
        }
        //generate extra characters to get ID_LENGTH length
        for(int i=0; id.length() <ID_LENGTH ;i++){
            int index = (int) (Math.random()*characters.length());
            id=id.concat(characters.substring(index, index+1));
        }
        return id.toLowerCase();
		
	}
    
    public static String generateUnique(String first, String last){
    	
    	String res = generate(first, last);
    	
    	while(idExists(res)){
    		
    		res=generate(first, last);
    		
    	}
    	
    	return res;
    	
    	
    }
    
    private static boolean idExists(String id){
    	
    	return BasicUser.find.byId(id)!=null;
    	
    }

}