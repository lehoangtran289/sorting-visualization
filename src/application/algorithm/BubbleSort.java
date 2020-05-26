package application.algorithm;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import application.Constants;
import application.MainService;
import application.task.SortTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class BubbleSort extends SortTask{

	public BubbleSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		Color prev = Color.rgb(157, 133, 255);
		Paint green = Color.LIGHTGREEN;
		Paint red = Color.INDIANRED;
		
		if (curGraphType == Constants.BARS) {		// if current graph is bars
			for (int i = 0; i < size - 1; i++) {
				for (int j = i + 1; j < size; j++) {
					System.out.println("ok");
					Rectangle cur = service.getRect(pane, i);
					Rectangle temp = service.getRect(pane, j);
					if (cur.getHeight() > temp.getHeight()) {
						FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
							service.swapRectangle(cur, temp); // code to update UI...
							cur.setFill(prev);
						}, /* return value from task: */ null);
						Platform.runLater(updateUITask); // submit for execution on FX Application Thread:
						updateUITask.get(); // block thread until work complete:
						delay();
						continue;
					}
					delay();
				}
				
			}
		}
	}

}
