import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;  

public class GSON { 	
   public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {      
      GsonBuilder builder = new GsonBuilder(); 
      builder.setPrettyPrinting(); 
      //JSONObject json = new JSONObject(jsonResult);
      Gson gson = builder.create(); 
     // String loudScreaming = json.getJSONObject("praise").getString("definition[1]");
      Words word = gson.fromJson(new FileReader("../GSON/words.json"), Words.class); 
      //System.out.println(loudScreaming);    
 
   } 
} 