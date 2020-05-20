package application;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
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
	private Pane mainPane;
	@FXML
	private TextArea textArea;
	@FXML
	private Button creditButton;

	private Map<String, String> algoInfo = Map.of(Constants.BUBBLE,
			"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)", Constants.SELECTION,
			"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)", Constants.MERGE,
			"Best Case: O(nlogn)\nWorst Case: O(nlogn)\nAverage: O(nlogn)", Constants.BUCKET,
			"Best Case: O(n+k)\nWorst Case: O(n^2)\nAverage: O(n+k)");

	private ObservableList<String> algorithmsList = FXCollections.observableArrayList(Constants.BUBBLE,
			Constants.SELECTION, Constants.MERGE, Constants.BUCKET);

	private ObservableList<String> graphTypesList = FXCollections.observableArrayList("Bar graphs", "Dots");

	private String curAlgo;
	private String curGraphType;
	private int arraySize = 10;
	private int delayTime = 0;

	private MainService service = new MainService();

	// ----HANDLERS------------------------------------

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// init delaytime
		delaySlider.valueProperty().addListener((observable, oldVal, newVal) -> {
			this.delayTime = newVal.intValue();
		});

		// init array size
		sizeLabel.setText("Array size: " + arraySize);
		sizeSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
			int cur = arraySize;
			arraySize = newVal.intValue() / 10 * 10;
			if ( cur != arraySize && (arraySize == 10 || arraySize == 40 || arraySize == 70 || arraySize == 100)) {
				sizeLabel.setText("Array size: " + arraySize);
				service.generate(mainPane, arraySize, curGraphType);
			}
		});

		// init combobox
		algorithmsBox.setItems(algorithmsList);
		graphTypesBox.setItems(graphTypesList);
		graphTypesBox.getSelectionModel().selectFirst();

		this.curGraphType = graphTypesBox.getValue();
		service.generate(mainPane, arraySize, curGraphType);
	}

	public void algorithmsBoxChange(ActionEvent e) {
		this.curAlgo = algorithmsBox.getValue();
		textArea.setText(algoInfo.get(this.curAlgo));
	}

	public void graphTypesBoxChange(ActionEvent e) {
		this.curGraphType = graphTypesBox.getValue();
		service.generate(mainPane, arraySize, curGraphType);
	}

	public void resetButtonClick(ActionEvent e) {
		service.generate(mainPane, arraySize, curGraphType);
	}

	public void creditButtonClick(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Credit");
		alert.setHeaderText("JavaFX OOLT Project 6: Sorting Algorithms Visualization");
		alert.setContentText("Tran Le Hoang - 20176764\nTran Hai Son - 2017xxxx\nHoang Tuan Anh Van - 20170224");
		alert.show();
	}

	// TODO: generate different algorithms and delay time for animation
	public void sortButtonClick(ActionEvent e) throws InterruptedException {
		if (curGraphType == Constants.BARS)
			for (int i = 0; i < arraySize; i++) {
				Rectangle s1 = (Rectangle) mainPane.lookup("#" + i);
				for (int j = i + 1; j < arraySize; j++) {
					Rectangle s2 = (Rectangle) mainPane.lookup("#" + j);
					if (s1.getHeight() > s2.getHeight())
						service.swapRectangle(s1, s2);
				}
			}
		if (curGraphType == Constants.DOTS)
			for (int i = 0; i < arraySize; i++) {
				for (int j = i + 1; j < arraySize; j++) {
					Circle s1 = (Circle) mainPane.lookup("#" + i);
					Circle s2 = (Circle) mainPane.lookup("#" + j);
					if (s1.getCenterY() < s2.getCenterY())
						service.swapCircle(s1, s2);
				}
			}
	}
}
