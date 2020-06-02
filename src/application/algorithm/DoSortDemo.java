package application.algorithm;

import java.util.ArrayList;
import java.util.List;

import application.constant.Constants;
import application.service.MainService;
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

	private List<Rectangle> rectList = new ArrayList<>();
	private SequentialTransition st = new SequentialTransition();

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
		
		double xpart = pane.getPrefWidth() / size;

		if (curGraphType == Constants.BARS) {

			System.out.println("herre");
			for (int i = 0; i < size; i++) {
				Rectangle temp = service.getRect(pane, i);
				rectList.add(temp);
			}
			rectList.forEach(i -> System.out.println(i));

			System.out.println(" ------ ");

			for (int i = 0; i < size; i++) {
				Rectangle cur = rectList.get(i);
				Rectangle min = rectList.get(i);
				int minIndex = i;

				for (int j = i + 1; j < size; j++) {
					Rectangle tmp = rectList.get(j);
					if (min.getHeight() > tmp.getHeight()) {
						min = tmp;
						minIndex = j;
					}
				}

				if (minIndex != i) {
					System.out.println(cur + "\n" + min + "\n--");
					double tempx = minIndex - i;
					TranslateTransition tt1 = new TranslateTransition(Duration.millis(delay), cur);
					tt1.setByX(tempx * xpart);

					TranslateTransition tt2 = new TranslateTransition(Duration.millis(delay), min);
					tt2.setByX(-tempx * xpart);
					
					ParallelTransition pt = new ParallelTransition(tt1, tt2);
					st.getChildren().add(pt);
					System.out.println(st.getChildren().toString());
					
					rectList.set(i, min);
					rectList.set(minIndex, cur);
				}
			}
			st.play();

			st.setOnFinished((e) -> {
				System.out.println("finished");
			});

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
