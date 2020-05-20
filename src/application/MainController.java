package application;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

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

	private Map<String, String> algoInfo = Map.of(Constants.BUBBLE, "Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)",
			Constants.SELECTION, "Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)", Constants.MERGE,
			"Best Case: O(nlogn)\nWorst Case: O(nlogn)\nAverage: O(nlogn)", Constants.BUCKET,
			"Best Case: O(n+k)\nWorst Case: O(n^2)\nAverage: O(n+k)");

	private ObservableList<String> algorithmsList = FXCollections.observableArrayList(Constants.BUBBLE, Constants.SELECTION, Constants.MERGE, Constants.BUCKET);

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
		sizeLabel.setText("Array size: " + this.arraySize);
		sizeSlider.valueProperty().addListener((observable, oldVal, newVal) -> {
			this.arraySize = newVal.intValue() / 10 * 10;
			sizeLabel.setText("Array size: " + this.arraySize);
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
		mainPane.getChildren().clear();
		service.generate(mainPane, arraySize, curGraphType);
	}

	public void resetButtonClick(ActionEvent e) {
		mainPane.getChildren().clear();
		service.generate(mainPane, arraySize, curGraphType);
	}

	public void creditButtonClick(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Credit");
		alert.setHeaderText("JavaFX OOLT Project 6: Sorting Algorithms Visualization");
		alert.setContentText("Tran Le Hoang - 20176764\nTran Hai Son - 2017xxxx\nHoang Tuan Anh - 2017xxxx");
		alert.show();
	}
	
	public void sortButtonClick(ActionEvent e) {
//		ObservableList<Node> lst = mainPane.getChildren();
//		lst = lst.sorted((a,b) -> {
//			return (int) (a.getLayoutY() - b.getLayoutY());
//		});
//		mainPane.getChildren().clear();
//		mainPane.getChildren().addAll(lst);
		
		Shape s1 = (Shape) mainPane.getChildren().get(0);
		Shape s2 = (Shape) mainPane.getChildren().get(1);
		System.out.println("s1: " + s1.getLayoutY());
		System.out.println("s2: " + s2.getLayoutY());
		if (s1.getLayoutY() < s2.getLayoutY()) {
			service.swapObj(s1, s2);
		}
		
//		for(int i = 0; i < arraySize; i++) {
//			for (int j = i+1; j < arraySize; j++) {
//				Shape s1 = (Shape) mainPane.getChildren().get(i);
//				Shape s2 = (Shape) mainPane.getChildren().get(j);
//				if (s1.getLayoutY() < s2.getLayoutY())
//					service.swapObj(s1, s2);
//			}
//		}
	}
}
