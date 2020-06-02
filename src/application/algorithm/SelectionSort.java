package application.algorithm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import application.constant.Constants;
import application.service.MainService;
import application.task.SortTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SelectionSort extends SortTask {
	
	// constructor from parent class
	public SelectionSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}
	
	// NOTE: modify UI must be done in Platform.runLater()
	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		Color prev = (Color) Constants.PRIMARY;
		Paint green = (Color) Constants.GREEN;
		Paint red = (Color) Constants.RED;

		if (curGraphType == Constants.BARS) {		// if current graph is bars
			for (int i = 0; i < size - 1; i++) {
				Rectangle cur = service.getRect(pane, i);	
				Platform.runLater(() -> {			
					cur.setFill(green);
				});
				Rectangle min = service.getRect(pane, i);
				for (int j = i + 1; j < size; j++) {
					Rectangle temp = service.getRect(pane, j);
					Platform.runLater(() -> {
						temp.setFill(red);
					});
//					delay();
					if (min.getHeight() > temp.getHeight()) {
						min = temp;
						Platform.runLater(() -> {
							temp.setFill(prev);
						});
						delay();
						continue;
					}
					delay();
					Platform.runLater(() -> {
						temp.setFill(prev);
					});
				}
				
				Rectangle min2 = min;
				FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
					service.swapRectangle(cur, min2); // code to update UI...
					cur.setFill(prev);
				}, /* return value from task: */ null);
				Platform.runLater(updateUITask); // submit for execution on FX Application Thread:
				updateUITask.get(); // block thread until work complete:

//				delay();
			}
		}

		if (curGraphType == Constants.DOTS) {
			for (int i = 0; i < size; i++) {
				Circle cur = service.getCircle(pane, i);
				Platform.runLater(() -> {
					cur.setFill(green);
				});
				Circle min = service.getCircle(pane, i);
				for (int j = i + 1; j < size; j++) {
					Circle temp = service.getCircle(pane, j);
					Platform.runLater(() -> {
						temp.setFill(red);
					});
//					delay();
					if (min.getCenterY() < temp.getCenterY()) {
						min = temp;
					}
					delay();
					Platform.runLater(() -> {
						temp.setFill(prev);
					});
				}

				Circle min2 = min;
				FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
					service.swapCircle(cur, min2); // code to update UI...
					cur.setFill(prev);
				}, /* return value from task: */ null);
				Platform.runLater(updateUITask); // submit for execution on FX Application Thread:
				updateUITask.get(); // block until work complete:

//				delay();
			}
		}
	}

}
