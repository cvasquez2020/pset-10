import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import javafx.scene.control.CheckBox;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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
		static public Boolean ascending = false;
		static String lastWord = "";
		static private CheckBox asc = new CheckBox("asc");
	    static private CheckBox desc = new CheckBox("desc");
		static int index;
		Button button;
		ObservableList<String> data = FXCollections.observableArrayList();
		FilteredList<String> filteredData = new FilteredList<>(data, s -> true);
		ListView<String> list = new ListView<String>(filteredData);
	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Dictionary.addAllWords();
		
	      launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		VBox right = new VBox();
	    Dictionary.listSpellings(ascending).forEach(data::add);
	    Text spelling = new Text();
	    Text defHeader = new Text("Definitions");
	    Text synHeader = new Text("Synonyms");
	    Text antHeader = new Text("Antonyms");
	    defHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21)); 
	    synHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    antHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 21));
	    TextField filterInput = new TextField();
	    filterInput.textProperty().addListener(obs->{
	    	
	        String filter = filterInput.getText(); 
	        if (filter == null || filter.length() == 0) {
	            filteredData.setPredicate(s -> true);
	        }
	        else {
	            filteredData.setPredicate(s -> s.contains(filter));
	        }
	        
	    });
	    
	    List<String> currentWordList = filteredData;
	    
	    int maxHeight = 600;
	   
	    index = -1;
	    GridPane content = new GridPane();
	    
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
            if (!currentWordList.contains(lastWord)) {
		    	right.getChildren().clear();
		    }
            index = currentWordList.indexOf(list.getSelectionModel().getSelectedItem());

            ArrayList<Definitions> definitions = new ArrayList<Definitions>();

            ArrayList<String> synonyms = new ArrayList<String>();

            ArrayList<String> antonyms = new ArrayList<String>();
            if (index >= 0) {
            	right.getChildren().clear();
            	spelling.setText(wordList.get(index).getSpelling());
            	lastWord = wordList.get(index).getSpelling();
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
           
            if (!currentWordList.contains(lastWord)) {
            	right.getChildren().clear();
            }
          }
        });
	    
	    
	      spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
	     
	      desc.setSelected(true);
	     
	      HBox check = new HBox(asc, desc);
	      
	      spelling.setFill(Color.BLACK); 
	      Separator separator1 = new Separator();
	    
	      Separator separator2 = new Separator();
	      separator2.setOrientation(Orientation.VERTICAL);
	      spelling.setStrokeWidth(2); 
	      Button addWord = new Button("Add");
	      Button rmWord = new Button("Remove");
	      HBox buttons = new HBox(addWord, rmWord);
	      
	      list.setPrefWidth(150);
	      list.setPrefHeight(maxHeight);
	      desc.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    	  
	          @Override
	          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

	              if(newValue){
	            	  ascending = false;
	            	  asc.setSelected(false);
	              } else {
	                 asc.setSelected(true);
	              }
	              
	          }
	      });
	      asc.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    	  
	          @Override
	          public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

	              if(newValue){
	            	  ascending = true;
	            	  desc.setSelected(false);
	              } else {
	                 desc.setSelected(true);
	              }
	              TextField filterInput = new TextField();
	      	    filterInput.textProperty().addListener(obs->{
	      	    	
	      	        String filter = filterInput.getText(); 
	      	        if (filter == null || filter.length() == 0) {
	      	            filteredData.setPredicate(s -> true);
	      	        }
	      	        else {
	      	            filteredData.setPredicate(s -> s.contains(filter));
	      	        }
	      	        
	      	    });
	          }
	      });
	      VBox left = new VBox(buttons, filterInput, check, separator1, list);
	      left.setSpacing(5);
	      right.setSpacing(10);
	      left.setPadding(new Insets(2, 2, 2, 2));
	      GridPane.setMargin(right, new Insets(2,10,2,2));
	      HBox both = new HBox(left, right);
	      both.setSpacing(20);
	      content.add(both, 0, 0);
	      ScrollBar sc = new ScrollBar();
	      Scene scene = new Scene(content, 1100, maxHeight);
	        sc.setLayoutX(1100-sc.getWidth());
	        sc.setMin(0);
	        sc.setOrientation(Orientation.VERTICAL);
	        sc.setPrefHeight(180);
	        sc.setMax(1100);
	        right.getChildren().addAll(sc);

	        sc.valueProperty().addListener(new ChangeListener<Number>() {
	            public void changed(ObservableValue<? extends Number> ov,
	                Number old_val, Number new_val) {
	                    right.setLayoutY(-new_val.doubleValue());
	            }
	        });
		      
	       
	      list.getSelectionModel().clearSelection();
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
}