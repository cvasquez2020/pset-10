import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sun.glass.events.MouseEvent;

import javafx.scene.control.CheckBox;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Callback;

	public class Interface extends Application {
		static int lastIndex = -1;
		static String lastWord = "";
		Button button;
		
	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Dictionary.addAllWords();
		
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		VBox right = new VBox();
	    ObservableList<String> data = FXCollections.observableArrayList();
	    Dictionary.listWords().forEach(data::add);

	    FilteredList<String> filteredData = new FilteredList<>(data, s -> true);
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
	    List<String> wordsDisplayed = data;
	    
	    int maxHeight = 600;
	    ListView<String> list = new ListView<String>(filteredData);
	    GridPane content = new GridPane();
	    
	    content.setPadding(new Insets(5, 10, 5, 5));
	    
	    list.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
        	if (lastIndex != -1) {
        		
        		lastWord = Dictionary.listWords().get(lastIndex);
        		if (true) {
        			right.getChildren().clear();
        		}
        	}
            int index = wordsDisplayed.indexOf(list.getSelectionModel().getSelectedItem());
            lastIndex= index;
            Words[] wordList = null;
            try {
				wordList = Dictionary.addAllWords();
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			} catch (JsonIOException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
            
           //Word currentWord = list.(list.getSelectionModel().getSelectedItem())].indexOf();
             
            ArrayList<Definitions> definitions = new ArrayList<Definitions>();

            ArrayList<String> synonyms = new ArrayList<String>();

            ArrayList<String> antonyms = new ArrayList<String>();
            if (index >= 0) {
            	right.getChildren().clear();
            	spelling.setText(wordList[index].getSpelling());
            	right.getChildren().addAll(spelling);
            	right.getChildren().addAll(defHeader);
            	definitions = wordList[index].getDefintion();
            }
           
           
           for (Definitions def : definitions) {
        	   right.getChildren().addAll(new Text(definitions.indexOf(def) + 1 + ". " + wordList[index].getSpelling() + " (" + def.getPartOfSpeech() + ")"));
        	   right.getChildren().addAll(new Text("\t" + def.getDefinition()));
           }
          
           if (index >= 0) {
           synonyms = wordList[index].getSynonyms();
           for (String syn : synonyms) {
        	   right.getChildren().addAll(new Text("\t" + ((int) synonyms.indexOf(syn) + 1) + ". " +  syn));
           }
           right.getChildren().add(synHeader);
           }
          
           
           if (index >= 0) {
        	   antonyms = wordList[index].getAntonyms();
               
               right.getChildren().add(antHeader);
              for (String ant : antonyms) {
            	  right.getChildren().addAll(new Text("\t" +  ((int) antonyms.indexOf(ant) + 1) + ". " + ant));
              }
           }
           
           
          }
        });
	    
	      spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
	      CheckBox asc = new CheckBox("asc");
	      CheckBox desc = new CheckBox("desc");
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
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
}