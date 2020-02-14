import java.util.ArrayList; 


public class Words {
	public String word;
	ArrayList<String> definitions = new ArrayList<String>(); 
	ArrayList<String> partsOfSpeech = new ArrayList<String>(); 
	ArrayList<String> synonyms = new ArrayList<String>();
	ArrayList<String> antonyms = new ArrayList<String>();
	
	public Words(String word) {
		this.word = word;
	}
	public void setDefinition(String definition, String partsOfSpeech) {
		this.definitions.add(definition);
		this.partsOfSpeech.add(partsOfSpeech);
	}
	public void addSynonym(String synonym) {
		this.synonyms.add(synonym);
	}
	public void addAntonym(String antonym) {
		this.antonyms.add(antonym);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
