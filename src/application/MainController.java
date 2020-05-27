package application;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import application.algorithm.*;
import application.task.SortTask;
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

public class MainController implements Initializable {
	// FXML components
	@FXML
	private Slider delaySlider;
	@FXML
	private Slider sizeSlider;
	@FXML
	private Label sizeLabel;
	@FXML
	private Label delayLabel;
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
	@FXML
	private Button sortButton;

	private Map<String, String> algoInfo = Map.of(Constants.BUBBLE,
			"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)", Constants.SELECTION,
			"Best Case: O(n)\nWorst Case: O(n^2)\nAverage: O(n^2)", Constants.MERGE,
			"Best Case: O(nlogn)\nWorst Case: O(nlogn)\nAverage: O(nlogn)", Constants.BUCKET,
			"Best Case: O(n+k)\nWorst Case: O(n^2)\nAverage: O(n+k)");

	// these ObservableList are used for Combobox initialization
	private ObservableList<String> algorithmsList = FXCollections.observableArrayList(Constants.SELECTION,
			Constants.BUBBLE, Constants.MERGE, Constants.BUCKET);

	private ObservableList<String> graphTypesList = FXCollections.observableArrayList("Bar graphs", "Dots");

	private int isStart = 0; // check if sort start
	private String curAlgo; // current algo being selected
	private String curGraphType; // current graph type being selected
	private int arraySize = 10; // init array size
	private int delay = 30; // init delay time

	private MainService service = new MainService();

	private SortTask sortTask;
	

	// ----FXML HANDLERS------------------------------------

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// init delaytime slider
		delayLabel.setText("Delay: " + delay + " ms"); // set label text
		delaySlider.valueProperty().addListener((observable, oldVal, newVal) -> { // add listener to delay slider
			delay = newVal.intValue();
			delayLabel.setText("Delay: " + delay + " ms");
			if (oldVal != newVal && isStart == 1) { // bind delay value change with SortTask
				sortTask.setDelay(delay);
			}
		});

		// init array size slider
		List<Integer> size = Arrays.asList(10, 40, 70, 100);

		sizeLabel.setText("Array size: " + arraySize); // set label text
		sizeSlider.valueProperty().addListener((observable, oldVal, newVal) -> { // add listener to size slider
			int prevSize = arraySize;
			arraySize = newVal.intValue() / 10 * 10;
			if (prevSize != arraySize && size.contains(arraySize)) { // reset pane if array size change
				sizeLabel.setText("Array size: " + arraySize);
				service.generate(mainPane, arraySize, curGraphType); // generate new pane
				isStart = 0;
				if (sortTask != null) // if current sort is running -> cancel that
					sortTask.cancel();
			}
		});

		// init combobox
		algorithmsBox.setItems(algorithmsList);
		graphTypesBox.setItems(graphTypesList);
		graphTypesBox.getSelectionModel().selectFirst();
		algorithmsBox.getSelectionModel().selectFirst();

		// init current graph type
		this.curGraphType = graphTypesBox.getValue(); // bar graph
		this.curAlgo = algorithmsBox.getValue(); // selection sort
		textArea.setText(algoInfo.get(this.curAlgo)); // selection sort info

		// init pane
		service.generate(mainPane, arraySize, curGraphType);
	}

	// set algorithm when combobox change
	public void algorithmsBoxChange(ActionEvent e) {
		this.curAlgo = algorithmsBox.getValue();
		textArea.setText(algoInfo.get(this.curAlgo));
	}

	// set graph type when combobox change
	public void graphTypesBoxChange(ActionEvent e) {
		this.curGraphType = graphTypesBox.getValue();
		service.generate(mainPane, arraySize, curGraphType); // generate new pane
		isStart = 0;
		if (sortTask != null) // if current sort is running -> cancel that
			sortTask.cancel();
	}

	// RESET BUTTON
	public void resetButtonClick(ActionEvent e) {
		service.generate(mainPane, arraySize, curGraphType); // generate new pane
		isStart = 0;
		if (sortTask != null) // if current sort is running -> cancel that
			sortTask.cancel();
	}

	// CREDIT BUTTON
	public void creditButtonClick(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Credit");
		alert.setHeaderText("JavaFX OOLT Project 6: Sorting Algorithms Visualization");
		alert.setContentText("Tran Le Hoang - 20176764\nTran Hai Son - 2017xxxx\nHoang Tuan Anh Van - 20170224");
		alert.show();
	}

	// SORT BUTTON
	public void sortButtonClick(ActionEvent e) throws InterruptedException {
		if (sortTask != null) { // if current sort is running -> cancel that
			sortTask.cancel();
			isStart = 0;
		}

		// TODO: Switch different algorithms here
		switch (curAlgo) {
		case Constants.SELECTION:
			sortTask = new SelectionSort(arraySize, delay, curGraphType, mainPane, service); // call Sort Task
//			sortTask = new DoSortDemo(arraySize, delay, curGraphType, mainPane, service);
			break;
		
		case Constants.BUBBLE:
			sortTask = new BubbleSort(arraySize, delay, curGraphType, mainPane, service);
			break;
			
		case Constants.BUCKET:
			sortTask = new BucketSort(arraySize, delay, curGraphType, mainPane, service);
			textArea.setText("Not yet implement ^^");
			break;
			
		case Constants.MERGE:
			sortTask = new MergeSort(arraySize, delay, curGraphType, mainPane, service);
			textArea.setText("Not yet implement ^^");
			break;

		default:
			break;
		}
		isStart = 1;

		System.out.println("active threads: " + Thread.activeCount()); // current # of threads running
//		Set<Thread> threads = Thread.getAllStackTraces().keySet();
//		for (Thread t : threads) 
//			System.out.printf("%-20s \t\t %s \t %d\n", t.getName(), t.getState(), t.getPriority());

		Thread thread = new Thread(sortTask); // run thread
		thread.setDaemon(true);
		thread.start();

	}
}
