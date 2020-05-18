package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainController implements Initializable {
	@FXML
	private Slider delaySlider;

	@FXML
	private Slider sizeSlider;

	@FXML
	private Label sizeLabel;

	@FXML
	private ComboBox<String> algorithmsBox;

	@FXML
	private ComboBox<String> graphTypesBox;

	@FXML
	private HBox mainHBox;

	private ObservableList<String> algorithmsList = FXCollections.observableArrayList("Bubble sort", "Selection sort",
			"Merge sort", "Bucket sort");

	private ObservableList<String> graphTypesList = FXCollections.observableArrayList("Bar graphs", "Dots");

	// ----------------------------------------
	private String curAlgo;

	private String curGraphType;

	private int arraySize = 50;

	private int delayTime = 0;

	// ----handlers------------------------------------

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// init delaytime
		delaySlider.valueProperty().addListener((observable, oldVal, newVal) -> {
			this.delayTime = newVal.intValue();
		});

		// init array size
		sizeLabel.setText("Array size: " + this.arraySize);
		sizeSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
			this.arraySize = newVal.intValue() / 50 * 50;
			sizeLabel.setText("Array size: " + this.arraySize);
		});

		// init combobox
		algorithmsBox.setItems(algorithmsList);
		graphTypesBox.setItems(graphTypesList);

		generate();
	}

	public void algorithmsBoxChange(ActionEvent e) {
		this.curAlgo = algorithmsBox.getValue();
	}

	public void graphTypesBoxChange(ActionEvent e) {
		this.curGraphType = graphTypesBox.getValue();
	}

	public void resetButtonClick(ActionEvent e) {
		mainHBox.getChildren().clear();
		generate();
	}
	
	// ----utils------------------------------------------------

	public void generate() {
		for (int i = 0; i < arraySize; i++) {
			Rectangle r = new Rectangle(mainHBox.getPrefWidth() / this.arraySize,
					10 + (int) (Math.random() * (mainHBox.getPrefHeight() - 20)));
			r.setFill(Color.rgb(157, 133, 255));
//          r.setStroke(Color.BLACK);
			mainHBox.getChildren().add(r);
		}
	}

	public void delay() throws InterruptedException {
		Thread.sleep(delayTime);
	}

}
