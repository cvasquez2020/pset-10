import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException; 

import java.util.Collections;
public class Words {
	private int wordId;
	public static int nextId = -1;
	public String word;
	ArrayList<Definitions> definitions = new ArrayList<Definitions>(); 
	ArrayList<String> synonyms = new ArrayList<String>();
	ArrayList<String> antonyms = new ArrayList<String>();
	
	public Words(String word, Definitions definitions, ArrayList<String> synonoyms, ArrayList<String> antonyms) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		this.word = word;
		this.definitions.add(definitions);
		Collections.copy(this.synonyms, synonyms);
		Collections.copy(this.antonyms, antonyms);
		this.wordId = nextId + 1;
		nextId++;
	
	}
	public void setDefinition(Definitions definitions) {
		this.definitions.add(definitions);  
	}
	public void addSynonym(String synonym) {
		this.synonyms.add(synonym);
	}
	public void addAntonym(String antonym) {
		this.antonyms.add(antonym);
	}
	public String getSpelling() {
		return this.word;
	}
	
	public int getId() {
		return this.wordId;
	}
	
	public ArrayList<Definitions> getDefintion() {
		return this.definitions;
	}
	public ArrayList<String> getSynonyms() {
		return this.synonyms;
	}
	public ArrayList<String> getAntonyms() {
		return this.antonyms;
	}

}
