package application;

import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

		Color prevColor = Color.rgb(157, 133, 255);
		if (curGraphType == Constants.BARS)
			for (int i = 0; i < size - 1; i++) {		// selection sort
				Rectangle s1 = service.getRect(pane, i);
				for (int j = i + 1; j < size; j++) {
					Rectangle s2 = service.getRect(pane, j);
					Thread.sleep(delay);			
					if (s1.getHeight() > s2.getHeight()) {
						service.swapRectangle(s1, s2);
					}
				}
			}
		if (curGraphType == Constants.DOTS)
			for (int i = 0; i < size; i++) {			// selection sort
				Circle s1 = service.getCircle(pane, i);
				for (int j = i + 1; j < size; j++) {
					Thread.sleep(delay);
					Circle s2 = service.getCircle(pane, j);
					if (s1.getCenterY() < s2.getCenterY())
						service.swapCircle(s1, s2);
				}
			}
		return null;
	}

}
