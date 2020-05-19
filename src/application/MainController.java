package application;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class MainController implements Initializable {
	private final String BUBBLE = "Bubble sort";
	private final String SELECTION = "Selection Sort";
	private final String MERGE = "Merge sort";
	private final String BUCKET = "Bucket sort";

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
	private Pane mainPane;
	@FXML
	private TextArea textArea;

	private Map<String, String> algoInfo = Map.of(BUBBLE, "Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)",
			SELECTION, "Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)", MERGE,
			"Best Case: O(nlogn)\nWorst Case: O(nlogn)\nAverage: O(nlogn)", BUCKET,
			"Best Case: O(n+k)\nWorst Case: O(n^2)\nAverage: O(n+k)");

	private ObservableList<String> algorithmsList = FXCollections.observableArrayList(BUBBLE, SELECTION, MERGE, BUCKET);

	private ObservableList<String> graphTypesList = FXCollections.observableArrayList("Bar graphs", "Dots");

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
		graphTypesBox.getSelectionModel().selectFirst();

		this.curGraphType = graphTypesBox.getValue();
		generate();
	}

	public void algorithmsBoxChange(ActionEvent e) {
		this.curAlgo = algorithmsBox.getValue();
		textArea.setText(algoInfo.get(this.curAlgo));
	}

	public void graphTypesBoxChange(ActionEvent e) {
		this.curGraphType = graphTypesBox.getValue();
		mainPane.getChildren().clear();
		generate();
	}

	public void resetButtonClick(ActionEvent e) {
		mainPane.getChildren().clear();
		generate();
	}

	// ----utils------------------------------------------------

	public void generate() {
		if (curGraphType == "Bar graphs")
			generateRectangle();
		else
			generateCircle();
	}

	public void generateRectangle() {
		for (int i = 0; i < arraySize; i++) {
			Rectangle r = new Rectangle(mainPane.getPrefWidth() / (this.arraySize),
					10 + (int) (Math.random() * (mainPane.getPrefHeight() - 20)));
			r.setFill(Color.rgb(157, 133, 255));
			r.setStroke(Color.WHITE);
			if (arraySize >= 150)
				r.setStrokeWidth(0.1);
			else
				r.setStrokeWidth(0.5);
			r.setLayoutX(i * r.getWidth());
			r.setLayoutY(mainPane.getPrefHeight() - r.getHeight());

			mainPane.getChildren().add(r);
//			System.out.println(getYValue(r));
		}
	}

	public void generateCircle() {
		for (int i = 0; i < arraySize; i++) {
			Circle c = new Circle(mainPane.getPrefWidth() / (2 * this.arraySize));
			c.setLayoutX(i * c.getRadius() * 2 + c.getRadius());
			c.setLayoutY(10 + (int) (Math.random() * (mainPane.getPrefHeight() - 20)));
			c.setFill(Color.rgb(157, 133, 255));
			mainPane.getChildren().add(c);
//			System.out.println(getYValue(c));
		}
	}
	
	public double getYValue(Shape s) {
		return mainPane.getPrefHeight() - s.getLayoutY();
	}

	public void delay() throws InterruptedException {
		Thread.sleep(delayTime);
	}

}
