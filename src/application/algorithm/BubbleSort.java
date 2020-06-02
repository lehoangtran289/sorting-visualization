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

public class BubbleSort extends SortTask {

	public BubbleSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		Color prev = (Color) Constants.PRIMARY;
		Paint green = (Color) Constants.GREEN;
		Paint red = (Color) Constants.RED;

		if (curGraphType == Constants.BARS) { // if current graph is bars
			for (int i = 0; i < size - 1; i++) {
				Rectangle cur = service.getRect(pane, i);
				Platform.runLater(() -> {
					cur.setFill(green);
				});
				for (int j = i + 1; j < size; j++) {
					Rectangle temp = service.getRect(pane, j);
					Platform.runLater(() -> {
						temp.setFill(red);
					});
					
					if (cur.getHeight() > temp.getHeight()) {
						FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
							service.swapRectangle(cur, temp); // code to update UI...
							temp.setFill(green);
						}, /* return value from task: */ null);
						Platform.runLater(updateUITask); // submit for execution on FX Application Thread:
						updateUITask.get(); // block thread until work complete:
						delay();
					}
					delay();
					Platform.runLater(() -> {
						temp.setFill(prev);
					});
				}
				Platform.runLater(() -> {
					cur.setFill(prev);
				});
			}
		}
		if (curGraphType == Constants.DOTS) {
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					Circle cur = service.getCircle(pane, i);
					Circle temp = service.getCircle(pane, j);
					if (cur.getCenterY() < temp.getCenterY()) {
						FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
							service.swapCircle(cur, temp); // code to update UI...
							cur.setFill(prev);
						}, /* return value from task: */ null);
						Platform.runLater(updateUITask); // submit for execution on FX Application Thread:
						updateUITask.get(); // block thread until work complete:
						delay();
					}
					delay();
				}
			}
		}
	}

}
