import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException; 

import java.util.Collection;
import java.util.Collections;
public class Words {
	
	public String word;
	ArrayList<Definitions> definitions = new ArrayList<Definitions>(); 
	ArrayList<String> synonyms = new ArrayList<String>();
	ArrayList<String> antonyms = new ArrayList<String>();
	
	public Words(String word, Definitions definitions, ArrayList<String> synonoyms, ArrayList<String> antonyms) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		//Words[] allWords = new Gson().fromJson(new FileReader("C:\\Users\\Acexa\\Desktop\\APCSA\\pset-10\\JSON\\words.json"), Words[].class);
		this.word = word;
		this.definitions.add(definitions);
		Collections.copy(this.synonyms, synonyms);
		Collections.copy(this.antonyms, antonyms);
	
	}
	public void setDefinition(Definitions definitions, String partsOfSpeech) {
		this.definitions.add(definitions);
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
