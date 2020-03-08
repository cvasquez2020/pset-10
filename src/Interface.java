import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Labeled;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javafx.beans.value.ChangeListener;

import javafx.scene.paint.Color;


	public class Interface extends Application {
		static private Text spelling = new Text();
		static final Text defHeader = new Text("Definitions");
		static final Text synHeader = new Text("Synonyms");
		static final Text antHeader = new Text("Antonyms");
		static private Words currentWord = null;
		static public Boolean ascending = true;
		static Words lastWord = null;
		static private List<Words> objsDisplayed;
		static private List<String> currentWordList;
		static private VBox antsyn = new VBox();
		static private CheckBox asc = new CheckBox("asc");
	    static private CheckBox desc = new CheckBox("desc");
	    static private Boolean firstWord;
	    static private ArrayList<Definitions> definitions = new ArrayList<Definitions>();
	    private static VBox right = new VBox();
        static private ArrayList<String> synonyms = new ArrayList<String>();
        static private ArrayList<String> antonyms = new ArrayList<String>();
		static int index;
		
		ObservableList<String> data = FXCollections.observableArrayList();
		FilteredList<String> filteredData = new FilteredList<>(data, s -> true);
		ListView<String> list = new ListView<String>(filteredData);
		
		private GridPane content;
		private TextField filterInput;
		private Scene scene;
		private static int extraDefs = 0;
		private static VBox left = new VBox();
		private static Button addButton = new Button("Add");
		
		private static Button rmButton = new Button("Remove");
		static ScrollPane scroll = new ScrollPane();
		
	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Dictionary.addAllWords();
		firstWord = true;
		asc.setSelected(true);
	    launch(args);
	    
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		
		EventHandler<ActionEvent> removeWord = new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	if (list.getSelectionModel().getSelectedItems().size() != 0) { 
		    	Alert alert = new Alert(AlertType.CONFIRMATION);
		    	alert.setTitle("Confirmation Dialog");
		    	alert.setHeaderText("DELETING WORD");
		    	alert.setContentText("Are you sure you want to delete selected words?");

		    	Optional<ButtonType> result = alert.showAndWait();
		    	if (result.get() == ButtonType.OK && list.getSelectionModel().getSelectedIndices() != null) {
		    		try {
		    			Words[] wordsToDelete = new Words[list.getSelectionModel().getSelectedIndices().size()];
		    			for (int i = 0; i < list.getSelectionModel().getSelectedItems().size(); i++) {
		    				for (Words orgWord : Dictionary.wordList) {
		    					if (orgWord.getSpelling().equals(list.getSelectionModel().getSelectedItems().get(i))) {
		    						wordsToDelete[i] = orgWord;
		    					}
		    				}
		    			}
						Dictionary.delWord(wordsToDelete);
						data.clear();
						  filterInput.textProperty().addListener(obs->{
						    	
						        String filter = filterInput.getText().toLowerCase(); 
						        if (filter == null || filter.length() == 0) {
						            filteredData.setPredicate(s -> true);
						        }
						        else {
						            filteredData.setPredicate(s -> s.toLowerCase().matches(filter + ".*"));
						        }
						        
						    });
						   
						    currentWordList = filteredData;
						    
						display(ascending, primaryStage, scene);
						Alert success = new Alert(AlertType.INFORMATION);
						success.setHeaderText("Successfully deleted word!");
						Optional<ButtonType> done = success.showAndWait();
						
						 if (done.isPresent() && done.get() == ButtonType.OK) {
				    	     event.consume();
				    	 }
					} catch (JsonIOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonSyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		    	
		    	} else {
		    		event.consume();
		    	}
		      event.consume();
		    } else {
		    	Alert alert = new Alert(AlertType.ERROR);
		    	alert.setTitle("ERROR");
		    	alert.setHeaderText("No words to delete...");
		    	Optional<ButtonType> result = alert.showAndWait();
		    	 if (result.isPresent() && result.get() == ButtonType.OK) {
		    	     event.consume();
		    	 }
		    	event.consume();
		    }
		    }
		};
		
		right = new VBox();
	    Dictionary.listSpellings(ascending).forEach(data::add);
	  
	    defHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21)); 
	    synHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    antHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    filterInput = new TextField();
	    
	    
	    filterInput.textProperty().addListener(obs->{
	    	
	        String filter = filterInput.getText().toLowerCase(); 
	        if (filter == null || filter.length() == 0) {
	            filteredData.setPredicate(s -> true);
	        }
	        else {
	            filteredData.setPredicate(s -> s.toLowerCase().matches(filter + ".*"));
	        }
	        
	    });
	   
	    currentWordList = filteredData;
	    
	    int maxHeight = 600;
	   
	    index = -1;
	    content = new GridPane();
	    
	    content.setPadding(new Insets(5, 10, 5, 5));
	    list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	    list.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          
		

		public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
			
			if (list.getSelectionModel().getSelectedIndices().size() == 1 && (firstWord || currentWordList.contains(currentWord.getSpelling()))) {
			 index = currentWordList.indexOf(list.getSelectionModel().getSelectedItem());
	            
	     	   
	            try {
					objsDisplayed = Dictionary.sortObj(ascending, currentWordList);
				} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
					
					e.printStackTrace();
				}
	            if (index != -1) {
	            	
	            	currentWord = objsDisplayed.get(index);
	                	
	            }
			
            
           
         
            if (currentWord != null && !currentWordList.contains(currentWord.getSpelling())) {
            	currentWord = null;
		    	right.getChildren().clear();
		    
            }
            
            definitions = new ArrayList<Definitions>();

            synonyms = new ArrayList<String>();

            antonyms = new ArrayList<String>();
            if (index >= 0) {
            	
                
            	right.getChildren().clear();
            	spelling.setText(currentWord.getSpelling());
            	lastWord = currentWord;
            	right.getChildren().addAll(spelling);
            	right.getChildren().addAll(defHeader);
            	definitions = currentWord.getDefintion();
            }
            
           for (Definitions def : definitions) {
        	   right.getChildren().addAll(new Text(definitions.indexOf(def) + 1 + ". " + currentWord.getSpelling() + " (" + def.getPartOfSpeech() + ")"));
        	   right.getChildren().addAll(new Text("\t" + def.getDefinition()));
           }
          
           if (index >= 0) {
           synonyms = currentWord.getSynonyms();
           for (String syn : synonyms) {
        	   right.getChildren().addAll(new Text("\t" + ((int) synonyms.indexOf(syn) + 1) + ". " +  syn));
           }
           right.getChildren().add(synHeader);
           }
          
           
           if (index >= 0) {
        	   antonyms = currentWord.getAntonyms();
               
               right.getChildren().add(antHeader);
              for (String ant : antonyms) {
            	  right.getChildren().addAll(new Text("\t" +  ((int) antonyms.indexOf(ant) + 1) + ". " + ant));
              }
           }
            if (currentWord != null && !currentWordList.contains(currentWord.getSpelling())) {
            	
                currentWord = null;
            	right.getChildren().clear();
            }
			} else {
				currentWord = null;
				right.getChildren().clear();
			}
          }
        });
	    
	    
	      spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
	     
	      
	      HBox check = new HBox(asc, desc);
	      
	      spelling.setFill(Color.BLACK); 
	      Separator separator1 = new Separator();
	    
	      Separator separator2 = new Separator();
	      separator2.setOrientation(Orientation.VERTICAL);
	      spelling.setStrokeWidth(2); 
	      
	      
		    
	      HBox buttons = new HBox(addButton, rmButton);
	      
	      list.setPrefWidth(150);
	      list.setPrefHeight(maxHeight);
	      
	      left  = new VBox(buttons, filterInput, check, separator1, list);
	      left.setSpacing(5);
	      right.setSpacing(10);
	      left.setPadding(new Insets(2, 2, 2, 2));
	      GridPane.setMargin(right, new Insets(2,10,2,2));
	      HBox both = new HBox(left, right);
	      both.setSpacing(20);
	      content.add(both, 0, 0);
	      
	     scene = new Scene(content, 1100, maxHeight);
	     
		      
	        desc.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    	  
		          @Override
		          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

		              if(newValue){
		            	  data.clear();
		            	  ascending = false;
		            	  asc.setSelected(false);
		            	 
		            		  display(ascending, primaryStage, scene);  
		            	  
		            	  
		              } else {
		                 asc.setSelected(true);
		              }
		              
		          }
		      });
		      asc.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    	  
		          @Override
		          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

		              if (newValue){
		            	  data.clear();
		            	  ascending = true;
		            	  desc.setSelected(false);
		            	 
		            	  display(ascending, primaryStage, scene);
		              } else {
		                 desc.setSelected(true);
		              }
		              
		          }
		      });  
		      EventHandler<ActionEvent> addWord = new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent event) {
				    	
				    	addWordScreen(primaryStage, scene, left);
				        event.consume();
				    }
				};
				addButton.setOnAction(addWord);
			    rmButton.setOnAction(removeWord);
			    list.getSelectionModel().clearSelection();
			    primaryStage.setScene(scene);
			    primaryStage.show();
	      
	}
	
	public void display(boolean ascending, Stage ps, Scene scene) {
		
	    Dictionary.listSpellings(ascending).forEach(data::add);
	    defHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21)); 
	    synHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    antHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));

		  content.setPadding(new Insets(5, 10, 5, 5));
		 spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
		 ArrayList<String> synonyms = new ArrayList<String>();

         ArrayList<String> antonyms = new ArrayList<String>();
         if (currentWord != null ) {
        	
             
         	right.getChildren().clear();
         	spelling.setText(currentWord.getSpelling());
         	
         	right.getChildren().addAll(spelling);
         	right.getChildren().addAll(defHeader);
         	definitions = currentWord.getDefintion();
         	 for (Definitions def : definitions) {
           	   right.getChildren().addAll(new Text(definitions.indexOf(def) + 1 + ". " + currentWord.getSpelling() + " (" + def.getPartOfSpeech() + ")"));
           	   right.getChildren().addAll(new Text("\t" + def.getDefinition()));
              }
         }
         
       
       
        if (currentWord != null) {
        synonyms = currentWord.getSynonyms();
        for (String syn : synonyms) {
     	   right.getChildren().addAll(new Text("\t" + ((int) synonyms.indexOf(syn) + 1) + ". " +  syn));
        }
        right.getChildren().add(synHeader);
        }
       
        
        if (currentWord != null) {
     	   antonyms = currentWord.getAntonyms();
            
            right.getChildren().add(antHeader);
           for (String ant : antonyms) {
         	  right.getChildren().addAll(new Text("\t" +  ((int) antonyms.indexOf(ant) + 1) + ". " + ant));
           }
           if ( currentWord != null && !currentWordList.contains(currentWord.getSpelling())) {
        	   	currentWord = null;
        	   	
            	right.getChildren().clear();
            }
   	      
        }
        
         
	     
	      HBox check = new HBox(asc, desc);
	      
	      spelling.setFill(Color.BLACK); 
	      Separator separator1 = new Separator();
	    
	      Separator separator2 = new Separator();
	      separator2.setOrientation(Orientation.VERTICAL);
	      spelling.setStrokeWidth(2); 
	      
	      HBox buttons = new HBox(addButton, rmButton);
	      
	      list.setPrefWidth(150);
	      int maxHeight = 600;
		list.setPrefHeight(maxHeight);
		left = new VBox(buttons, filterInput, check, separator1, list);
	      left.setSpacing(5);
	      right.setSpacing(10);
	      left.setPadding(new Insets(2, 2, 2, 2));
	      GridPane.setMargin(right, new Insets(2,10,2,2));
	      HBox both = new HBox(left, right);
	      both.setSpacing(20);
	      content.add(both, 0, 0);
	      
	      if (currentWord != null && !currentWordList.contains(currentWord.getSpelling())) {
	    	  currentWord = null;
          	right.getChildren().clear();
          }
	      EventHandler<ActionEvent> addWord = new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent event) {
			    	addWordScreen(ps, scene, left);
			        event.consume();
			    }
			};
			addButton.setOnAction(addWord);
		  list.getSelectionModel().clearSelection();
	      ps.setScene(scene);
	      ps.show();
    }
	
	public void addWordScreen(Stage ps, Scene scene, VBox left) {		
		VBox topRight = new VBox();
        extraDefs = 0;
		right.getChildren().clear();
	    defHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21)); 
	    synHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    antHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
		content.setPadding(new Insets(5, 10, 5, 5));
		spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
        
        
        spelling.setText("Word");
        topRight.getChildren().addAll(spelling);
        
        TextField addSpelling = new TextField();
        addSpelling.setPromptText("New word...");
        topRight.getChildren().add(addSpelling);
        
        
      
        VBox defFields = new VBox();
        VBox AntSyn = new VBox();
              
        ObservableList<String> partsOfSpeech = 
        	    FXCollections.observableArrayList(
        	        "Adjective",
        	        "Noun",
        	        "Verb"
        	    );
        Button extraDef = new Button("+");
        
        EventHandler<ActionEvent> addDefField = new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	extraDefs++;
		    	TextField newDefinition = new TextField();
	        	newDefinition.setPromptText("New word...");
	        	defFields.getChildren().add(newDefinition);
	        	final ComboBox<String> addPOS = new ComboBox<String>(partsOfSpeech);
	        	addPOS.setPromptText("Part of speech...");
	        	defFields.getChildren().add(addPOS);  
		    }
		};
		  extraDef.setOnAction(addDefField);
	        HBox DefField = new HBox(defHeader, extraDef);
	        defFields.getChildren().add(DefField);
	        for (int i = 0; i < (3 + extraDefs); i++) {
        	TextField newDefinition = new TextField();
        	newDefinition.setPromptText("New word...");
        	defFields.getChildren().add(newDefinition);
        	ComboBox<String> addPOS = new ComboBox<String>(partsOfSpeech);
        	addPOS.setPromptText("Part of speech...");
        	defFields.getChildren().add(addPOS);        	
        }
	    ArrayList<Definitions> newDefs = new ArrayList<Definitions>();
        antsyn.getChildren().add(synHeader);
        TextField synField = new TextField();
        synField.setPromptText("Synonyms...");
        antsyn.getChildren().add(synField);
        
        antsyn.getChildren().add(antHeader);
        TextField antField = new TextField();
        antField.setPromptText("Antonyms...");
        antsyn.getChildren().add(antField);
        
        
        Button confirmNewWord = new Button("Add");
        EventHandler<ActionEvent> submit = new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	String[] tempAntonyms = synField.getText().split(",");
		    	List<String> antList = Arrays.asList(tempAntonyms); 
		    	ArrayList<String> antonyms = new ArrayList<String>(antList);
		    	
		    	String[] tempSynonyms = synField.getText().split(",");
		    	List<String> synList = Arrays.asList(tempSynonyms); 
		    	ArrayList<String> synonyms = new ArrayList<String>(synList);
		    	for (int i = 1; i < (extraDefs + 3); i += 2) {	
	
						Definitions newDefinition = new Definitions(((TextField)defFields.getChildren().get(i)).getText(), ((ComboBox<String>) defFields.getChildren().get(i + 1)).getValue());
		    			newDefs.add(newDefinition);
						
		    		 
		    		
		    	}
		    	Words newWord = null;
		    	String newSpelling = addSpelling.getText();		  
		    	try {
					newWord = new Words(newSpelling, newDefs, synonyms, antonyms);
				} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
					e.printStackTrace();
				}
		    	Dictionary.addWord(newWord);
		    	data.clear();
				  filterInput.textProperty().addListener(obs->{
				    	
				        String filter = filterInput.getText(); 
				        if (filter == null || filter.length() == 0) {
				            filteredData.setPredicate(s -> true);
				        }
				        else {
				            filteredData.setPredicate(s -> s.matches(filter + ".*"));
				        }
				        
				    });
				   
				    currentWordList = filteredData;
				    
				display(ascending, ps, scene);
		    	extraDefs = 0;

		    }
		};
		
		confirmNewWord.setOnAction(submit);
        confirmNewWord.setLayoutX(5);
     
        right.getChildren().addAll(topRight, defFields, antsyn);
        
        
        antsyn.getChildren().add(confirmNewWord);
	    Separator separator2 = new Separator();
	    separator2.setOrientation(Orientation.VERTICAL);
	    spelling.setStrokeWidth(2); 
	    list.setPrefWidth(150);
	    int maxHeight = 600;
	    list.setPrefHeight(maxHeight);
	    left.setSpacing(5);
	    right.setSpacing(10);
	    left.setPadding(new Insets(2, 2, 2, 2));
	    GridPane.setMargin(right, new Insets(2,10,2,2));
	    HBox both = new HBox(left, right);
	    both.setSpacing(20);
	    content.add(both, 0, 0);
	   
    }
	
}