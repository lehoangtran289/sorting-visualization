package application;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DoSort extends Task<Void> {
	private int size;
	private String curGraphType;
	private Pane pane;
	private MainService service = new MainService();
	private int delay;

	public DoSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		this.size = size;
		this.delay = delay;
		this.curGraphType = curGraphType;
		this.pane = pane;
		this.service = service;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	// TODO: generate different algorithms and delay time for animation
	@Override
	protected Void call() throws Exception {
		if (isCancelled())
			return null;

		Color prev = Color.rgb(157, 133, 255);
		Paint green = Color.LIGHTGREEN;
		Paint red = Color.INDIANRED;
		Paint blue = Color.CADETBLUE;
		
		if (curGraphType == Constants.BARS)
			for (int i = 0; i < size - 1; i++) {		
				Rectangle cur = service.getRect(pane, i);
				cur.setFill(green);
				Rectangle min = service.getRect(pane, i);
				for (int j = i + 1; j < size; j++) {
					Rectangle temp = service.getRect(pane, j);
					temp.setFill(red);
					if (min.getHeight() > temp.getHeight()) {
						min = temp;
					}
					Thread.sleep(delay);			
					temp.setFill(prev);
				}
				service.swapRectangle(cur, min);
				cur.setFill(prev);
			}
		if (curGraphType == Constants.DOTS)
			for (int i = 0; i < size; i++) {			
				Circle cur = service.getCircle(pane, i);
				cur.setFill(green);
				Circle min = service.getCircle(pane, i);
				for (int j = i + 1; j < size; j++) {
					Circle temp = service.getCircle(pane, j);
					temp.setFill(red);
					if (min.getCenterY() < temp.getCenterY()) {
						min = temp;
					}
					Thread.sleep(delay);
					temp.setFill(prev);
				}
				service.swapCircle(cur, min);
				cur.setFill(prev);
			}
		return null;
	}

}
