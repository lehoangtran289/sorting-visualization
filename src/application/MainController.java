package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class MainController implements Initializable {
	@FXML
	public ComboBox<String> algorithmsBox;
	
	@FXML
	public ComboBox<String> graphTypesBox;

	public ObservableList<String> algorithmsList = FXCollections.observableArrayList("Bubble sort", "Selection sort",
			"Merge sort", "Bucket sort");
	
	public ObservableList<String> graphTypesList = FXCollections.observableArrayList("Bar graphs", "Dots");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		algorithmsBox.setItems(algorithmsList);
		graphTypesBox.setItems(graphTypesList);
	}

}
