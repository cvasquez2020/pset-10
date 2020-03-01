import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
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
		
		static public Boolean ascending = true;
		static Words lastWord = null;
		
		static private List<String> currentWordList;
		
		static private CheckBox asc = new CheckBox("asc");
	    static private CheckBox desc = new CheckBox("desc");
	    
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
		private static int extraDefs = 0;
		private static VBox left = new VBox();
		private static Button addButton = new Button("Add");
		
		private static Button rmButton = new Button("Remove");
		
	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Dictionary.addAllWords();
		asc.setSelected(true);
	    launch(args);
	    
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		
		EventHandler<ActionEvent> removeWord = new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		       rmButton.setText("rmWorks");
		        event.consume();
		    }
		};
		
		right = new VBox();
	    Dictionary.listSpellings(ascending).forEach(data::add);
	  
	    defHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21)); 
	    synHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    antHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    filterInput = new TextField();
	    
	    
	    filterInput.textProperty().addListener(obs->{
	    	
	        String filter = filterInput.getText(); 
	        if (filter == null || filter.length() == 0) {
	            filteredData.setPredicate(s -> true);
	        }
	        else {
	            filteredData.setPredicate(s -> s.contains(filter));
	        }
	        
	    });
	    
	    currentWordList = filteredData;
	    
	    int maxHeight = 600;
	   
	    index = -1;
	    content = new GridPane();
	    
	    content.setPadding(new Insets(5, 10, 5, 5));
	    
	    list.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          
		public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
			
            ArrayList<Words> wordList = null;
            
            try {
				wordList = Dictionary.sortObj(ascending);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
            if (lastWord != null) {
            if (!currentWordList.contains(lastWord.getSpelling())) {
		    	right.getChildren().clear();
		    }
            }
            index = currentWordList.indexOf(list.getSelectionModel().getSelectedItem());

            definitions = new ArrayList<Definitions>();

            synonyms = new ArrayList<String>();

            antonyms = new ArrayList<String>();
            if (index >= 0) {
            	right.getChildren().clear();
            	spelling.setText(wordList.get(index).getSpelling());
            	lastWord = wordList.get(index);
            	right.getChildren().addAll(spelling);
            	right.getChildren().addAll(defHeader);
            	definitions = wordList.get(index).getDefintion();
            }
            
           for (Definitions def : definitions) {
        	   right.getChildren().addAll(new Text(definitions.indexOf(def) + 1 + ". " + wordList.get(index).getSpelling() + " (" + def.getPartOfSpeech() + ")"));
        	   right.getChildren().addAll(new Text("\t" + def.getDefinition()));
           }
          
           if (index >= 0) {
           synonyms = wordList.get(index).getSynonyms();
           for (String syn : synonyms) {
        	   right.getChildren().addAll(new Text("\t" + ((int) synonyms.indexOf(syn) + 1) + ". " +  syn));
           }
           right.getChildren().add(synHeader);
           }
          
           
           if (index >= 0) {
        	   antonyms = wordList.get(index).getAntonyms();
               
               right.getChildren().add(antHeader);
              for (String ant : antonyms) {
            	  right.getChildren().addAll(new Text("\t" +  ((int) antonyms.indexOf(ant) + 1) + ". " + ant));
              }
           }
            if (lastWord != null && !currentWordList.contains(lastWord.getSpelling())) {
            	
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
	      
	     Scene scene = new Scene(content, 1100, maxHeight);
	     
		      
	        desc.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    	  
		          @Override
		          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

		              if(newValue){
		            	  data.clear();
		            	  ascending = false;
		            	  asc.setSelected(false);
		            	  display(ascending, primaryStage, scene, lastWord);
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
		            	  display(ascending, primaryStage, scene, lastWord);
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
	
	public void display(boolean ascending, Stage ps, Scene scene, Words word) {
	
		right.getChildren().clear();
	    Dictionary.listSpellings(ascending).forEach(data::add);
	    defHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21)); 
	    synHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    antHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));

		  content.setPadding(new Insets(5, 10, 5, 5));
		 spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
		 ArrayList<String> synonyms = new ArrayList<String>();

         ArrayList<String> antonyms = new ArrayList<String>();
         if (lastWord != null ) {
         	right.getChildren().clear();
         	spelling.setText(lastWord.getSpelling());
         	
         	right.getChildren().addAll(spelling);
         	right.getChildren().addAll(defHeader);
         	definitions = lastWord.getDefintion();
         }
         
        for (Definitions def : definitions) {
     	   right.getChildren().addAll(new Text(definitions.indexOf(def) + 1 + ". " + lastWord.getSpelling() + " (" + def.getPartOfSpeech() + ")"));
     	   right.getChildren().addAll(new Text("\t" + def.getDefinition()));
        }
       
        if (lastWord != null) {
        synonyms = lastWord.getSynonyms();
        for (String syn : synonyms) {
     	   right.getChildren().addAll(new Text("\t" + ((int) synonyms.indexOf(syn) + 1) + ". " +  syn));
        }
        right.getChildren().add(synHeader);
        }
       
        
        if (lastWord != null) {
     	   antonyms = lastWord.getAntonyms();
            
            right.getChildren().add(antHeader);
           for (String ant : antonyms) {
         	  right.getChildren().addAll(new Text("\t" +  ((int) antonyms.indexOf(ant) + 1) + ". " + ant));
           }
           if ( lastWord != null && !currentWordList.contains(lastWord.getSpelling())) {
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
		list.setPrefHeight(maxHeight );
		left = new VBox(buttons, filterInput, check, separator1, list);
	      left.setSpacing(5);
	      right.setSpacing(10);
	      left.setPadding(new Insets(2, 2, 2, 2));
	      GridPane.setMargin(right, new Insets(2,10,2,2));
	      HBox both = new HBox(left, right);
	      both.setSpacing(20);
	      content.add(both, 0, 0);
	      
	      if (lastWord != null && !currentWordList.contains(lastWord.getSpelling())) {
          	
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
		    //rmButton.setOnAction(removeWord);
		list.getSelectionModel().clearSelection();
	      ps.setScene(scene);
	      ps.show();
    }
	
	public void addWordScreen(Stage ps, Scene scene, VBox left) {		
		
		right.getChildren().clear();
	    defHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21)); 
	    synHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    antHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
		content.setPadding(new Insets(5, 10, 5, 5));
		spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
        right.getChildren().clear();
        
        spelling.setText("Word");
        right.getChildren().addAll(spelling);
        
        TextField addSpelling = new TextField("New word...");
        right.getChildren().add(addSpelling);
        
        Button extraDef = new Button("+");
        EventHandler<ActionEvent> addDefField = new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	extraDefs++;
		        event.consume();
		    }
		};
        extraDef.setOnAction(addDefField);
        HBox DefField = new HBox(defHeader, extraDef);
        right.getChildren().add(DefField);
              
        ObservableList<String> partsOfSpeech = 
        	    FXCollections.observableArrayList(
        	        "Adjective",
        	        "Noun",
        	        "Verb"
        	    );
        
        for (int i = 0; i < (3 + extraDefs); i++) {
        	right.getChildren().add(new TextField("New word.."));
        	final ComboBox<String> addPOS = new ComboBox<String>(partsOfSpeech);
        	addPOS.setValue("Part of speech...");
        	right.getChildren().add(addPOS);        	
        }
        
        
        
        
        right.getChildren().add(synHeader);
        TextField synField = new TextField("Synonyms");
        right.getChildren().add(antHeader);
        
	    spelling.setFill(Color.BLACK); 
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
	    
	    list.getSelectionModel().clearSelection();
	    ps.setScene(scene);
	    ps.show();
    }
	
}