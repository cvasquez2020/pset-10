import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.IntStream;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
import javafx.stage.Stage;
import javafx.util.Callback;

	public class Interface extends Application {
	
		Button button;
		
	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		Dictionary.addAllWords();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		
	    ObservableList<String> data = FXCollections.observableArrayList();
	    Dictionary.listWords().forEach(data::add);

	    FilteredList<String> filteredData = new FilteredList<>(data, s -> true);
	    Text text = new Text("Definition: ");
	    TextField filterInput = new TextField();
	    filterInput.textProperty().addListener(obs->{
	        String filter = filterInput.getText(); 
	        if(filter == null || filter.length() == 0) {
	            filteredData.setPredicate(s -> true);
	        }
	        else {
	            filteredData.setPredicate(s -> s.contains(filter));
	        }
	    });
	    int leftMenu = 200;
	    ListView<String> list = new ListView<String>(filteredData);
	    BorderPane content = new BorderPane();
	    list.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
            int index = Dictionary.listWords().indexOf(list.getSelectionModel().getSelectedItem());
            Words[] wordList = null;
            try {
				wordList = Dictionary.addAllWords();
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonIOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
           ArrayList<Definitions> definitions = wordList[index].getDefintion();
           for (Definitions def : definitions) {
        	   text.setText(def.getDefinition());
        	   
        	   
           }
          }
        });
	    
	    text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18)); 
	       
	      //setting the position of the text  
	      text.setX(50); 
	      text.setY(130);     
	       
	      //Setting the color 
	      text.setFill(Color.BLACK); 
	       
	      //Setting the Stroke  
	      text.setStrokeWidth(2); 
	      
	      // Setting the stroke color
	      text.setStroke(Color.BLUE);        
	   content.setRight(text);
	    content.setLeft(list);
	    list.setPrefWidth(leftMenu);
	    content.setBottom(filterInput);
	    filterInput.setPrefWidth(leftMenu);
	    
	    Scene scene = new Scene(content, 500, 500);
	    
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
}