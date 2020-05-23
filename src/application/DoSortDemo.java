package application;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

// THIS FILE IS USE FOR TESTING PURPOSES (like a sandbox :> )
// YOU CAN TEST ANYTHING INSIDE THIS FILE :> 
// TO RUN THIS TEST -> MODIFY property Task in MainController
public class DoSortDemo extends Task<Void> {
	private int size;
	private String curGraphType;
	private Pane pane;
	private MainService service = new MainService();
	private int delay;

	public DoSortDemo(int size, int delay, String curGraphType, Pane pane, MainService service) {
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

	// TODO: generate different algorithms
	@Override
	protected Void call() throws Exception {
		if (isCancelled())
			return null;

		Color prev = Color.rgb(157, 133, 255);
		Paint green = Color.LIGHTGREEN;
		Paint red = Color.INDIANRED;

		if (curGraphType == Constants.BARS) {
//			Rectangle cur = service.getRect(pane, 0);
//			Rectangle min = service.getRect(pane, 1);
//			System.out.println("X " + cur.getX() + "     " + min.getX());
//
//			if (cur.getHeight() > min.getHeight()) {
//				double tempx = min.getX() - cur.getX();
//				SequentialTransition trans = new SequentialTransition();
//				trans.getChildren().clear();
//
//				TranslateTransition tt1 = new TranslateTransition(Duration.millis(1000), cur);
//				tt1.setToX(tempx);
//
//				TranslateTransition tt2 = new TranslateTransition(Duration.millis(1000), min);
//				tt2.setToX(-tempx);
//
//				trans.getChildren().add(new ParallelTransition(tt1, tt2));
//
//				trans.play();
//
//				trans.setOnFinished((e) -> {
//					cur.setX(cur.getX() + cur.getTranslateX());
//					cur.setTranslateX(0);
//					min.setX(min.getX() + min.getTranslateX());
//					min.setTranslateX(0);
//				});
//			}
			
			for (int i = 0; i < size - 1; i++) {
				Rectangle cur = service.getRect(pane, i);
				Rectangle min = service.getRect(pane, i);
				for (int j = i + 1; j < size; j++) {
					Rectangle temp = service.getRect(pane, j);
					Thread.sleep(delay);
					if (min.getHeight() > temp.getHeight()) {
						min = temp;
					}
				}
//				service.swapRectangle(cur, min);
				// -----
				Rectangle cur2 = cur;
				Rectangle min2 = min;
				String id1 = cur.getId();
				String id2 = min2.getId();
				double tempx = min2.getX() - cur.getX();
				SequentialTransition trans = new SequentialTransition();
				trans.getChildren().clear();

				TranslateTransition tt1 = new TranslateTransition(Duration.millis(delay), cur);
				tt1.setToX(tempx);

				TranslateTransition tt2 = new TranslateTransition(Duration.millis(delay), min2);
				tt2.setToX(-tempx);

				trans.getChildren().add(new ParallelTransition(tt1, tt2));

				trans.play();

				trans.setOnFinished((e) -> {
					cur2.setX(cur2.getX() + cur2.getTranslateX());
					cur2.setTranslateX(0);
					cur2.setId(id2);
					min2.setX(min2.getX() + min2.getTranslateX());
					min2.setTranslateX(0);
					min2.setId(id1);
				});
				// -----

//				cur.setFill(prev);
			}
		}
		
		if (curGraphType == Constants.DOTS)
			for (int i = 0; i < size; i++) {
				Circle cur = service.getCircle(pane, i);
//				cur.setFill(green);
				Circle min = service.getCircle(pane, i);
				for (int j = i + 1; j < size; j++) {
					Circle temp = service.getCircle(pane, j);
//					temp.setFill(red);
					if (min.getCenterY() < temp.getCenterY()) {
						min = temp;
					}
					Thread.sleep(delay);
//					temp.setFill(prev);
				}
				service.swapCircle(cur, min);
//				cur.setFill(prev);
			}
		return null;
	}

}
