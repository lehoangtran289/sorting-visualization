package application.algorithm;

import application.Constants;
import application.MainService;
import application.task.SortTask;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SelectionSort extends SortTask {

	public SelectionSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException {
		Color prev = Color.rgb(157, 133, 255);
		Paint green = Color.LIGHTGREEN;
		Paint red = Color.INDIANRED;

		if (curGraphType == Constants.BARS)
			for (int i = 0; i < size - 1; i++) {
				Rectangle cur = service.getRect(pane, i);
				cur.setFill(green);
				Rectangle min = service.getRect(pane, i);
				
				for (int j = i + 1; j < size; j++) {
					Rectangle temp = service.getRect(pane, j);
//					temp.setFill(red);
					delay();
					if (min.getHeight() > temp.getHeight()) {
						min = temp;
						temp.setFill(red);
						delay();
					}
					temp.setFill(prev);
				}
				service.swapRectangle(cur, min);
				cur.setFill(prev);
				delay();
			}
		
		if (curGraphType == Constants.DOTS)
			for (int i = 0; i < size; i++) {
				Circle cur = service.getCircle(pane, i);
				cur.setFill(green);
				Circle min = service.getCircle(pane, i);
				for (int j = i + 1; j < size; j++) {
					Circle temp = service.getCircle(pane, j);
//					temp.setFill(red);
					delay();
					if (min.getCenterY() < temp.getCenterY()) {
						min = temp;
						temp.setFill(red);
						delay();
					}
					temp.setFill(prev);
				}
				service.swapCircle(cur, min);
				cur.setFill(prev);
				delay();
			}
	}

}
