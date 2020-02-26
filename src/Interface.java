import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.IntStream;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import javafx.scene.control.CheckBox;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
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
	    Text spelling = new Text("Definition: ");
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
	    int maxHeight = 600;
	    ListView<String> list = new ListView<String>(filteredData);
	    GridPane content = new GridPane();
	    content.setPadding(new Insets(5,10,5,5));
	    list.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
            int index = Dictionary.listWords().indexOf(list.getSelectionModel().getSelectedItem());
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
            
           spelling.setText(wordList[index].getSpelling());
           
          }
        });
	    
	    spelling.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 36)); 
; 
	      CheckBox asc = new CheckBox("asc");
	      CheckBox desc = new CheckBox("desc");
	      HBox check = new HBox(asc,desc);
	    
	      spelling.setFill(Color.BLACK); 
	      Separator separator1 = new Separator();
	    
	      Separator separator2 = new Separator();
	      separator2.setOrientation(Orientation.VERTICAL);
	      spelling.setStrokeWidth(1); 
	      Button addWord = new Button("Add");
	      Button rmWord = new Button("Remove");
	      HBox buttons = new HBox(addWord, rmWord);
	      
	      list.setPrefWidth(150);
	      list.setPrefHeight(maxHeight);
	      VBox right = new VBox(spelling);
	      
	      VBox left = new VBox(buttons,filterInput,check,separator1,list);
	      left.setSpacing(5);
	      left.setPadding(new Insets(2,2,2,2));
	      HBox both = new HBox(left,right);
	      both.setSpacing(20);
	      content.add(both,0,0);
	      
	      Scene scene = new Scene(content, 900, maxHeight);
	    
	      primaryStage.setScene(scene);
	      primaryStage.show();
	}
	
}