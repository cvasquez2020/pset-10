import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException; 
import com.fasterxml.jackson.annotation.JsonProperty;;

public class Words {
	
	@JsonProperty("word")
	public String word;
	@JsonProperty("definitions")
	ArrayList<String> definitions = new ArrayList<String>(); 
	//ArrayList<String> partsOfSpeech = new ArrayList<String>(); 
	ArrayList<String> synonyms = new ArrayList<String>();
	ArrayList<String> antonyms = new ArrayList<String>();
	
	public Words(String word ) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Words[] allWords = new Gson().fromJson(new FileReader("C:\\Users\\Acexa\\Desktop\\APCSA\\pset-10\\JSON\\words.json"), Words[].class);
	}
	public void setDefinition(String definition, String partsOfSpeech) {
		this.definitions.add(definition);
		//this.partsOfSpeech.add(partsOfSpeech);
	}
	public void addSynonym(String synonym) {
		this.synonyms.add(synonym);
	}
	public void addAntonym(String antonym) {
		this.antonyms.add(antonym);
	}
	public static void main(String[] args) {
		//System.out.println(allWords[0]);

	}

}
