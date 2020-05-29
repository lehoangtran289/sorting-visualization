package application.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import application.Constants;
import application.MainService;
import application.task.SortTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class BucketSort extends SortTask {
	private double xpart = pane.getPrefWidth() / size;

	public BucketSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay * 5, curGraphType, pane, service);
	}

	private int getBucket(double h, double maxHeight, int bucketNumber) {
		return (int) ((double) h / maxHeight * (bucketNumber - 1));
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		double maxHeight = pane.getPrefHeight();
		final int bucketNumber = (int) Math.sqrt(size);

		if (curGraphType == Constants.BARS) { // if current graph is bars
			
			// initialize buckets
			List<List<Rectangle>> buckets = new ArrayList<>(bucketNumber);
			for (int i = 0; i < bucketNumber; i++)
				buckets.add(new ArrayList<>());
			
			// Distribute rectangles
			for (int i = 0; i < size; i++) {
				Rectangle cur = service.getRect(pane, i);
				double h = cur.getHeight();
				buckets.get(getBucket(h, maxHeight, bucketNumber)).add(cur);
			}
			
			for (int i = 0; i < size; i++) {
				Rectangle r = service.getRect(pane, i);
				FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
					pane.getChildren().remove(r);
				}, /* return value from task: */ null);
				Platform.runLater(updateUITask); // submit for execution on FX Application Thread:
				updateUITask.get(); // block thread until work complete:
			}
			
			// Place buckets to pane by order
			int pos = 0;
			for (List<Rectangle> bkt : buckets) {
				for (int i = 0; i < bkt.size(); ++i) {
					int pos1 = pos;
					Rectangle newRect = bkt.get(i);
					newRect.setX(pos1 * xpart);
					Platform.runLater(() -> {
						pane.getChildren().add(newRect);
					});
					pos++;
//					delay();
				}
				delay();
				delay();
			}
			
			// Sort each bucket and update UI
			pos = 0;
			int pos1 = 0;
			for (List<Rectangle> bkt : buckets) {
				bkt.sort((r1, r2) -> {
					return (int) (r1.getHeight() - r2.getHeight());
				});
				for (int i = 0; i < bkt.size(); ++i) {	// remove rectangles in pane which are in each bucket 
					Rectangle newRect = bkt.get(i);
					Platform.runLater(() -> {
						pane.getChildren().remove(newRect);
					});
					pos++;
				}
				delay();
				for (int i = 0; i < bkt.size(); ++i) {	// add sorted rectangles in each buckets to pane
					Rectangle newRect = bkt.get(i);
					newRect.setX(xpart * pos1);
					Platform.runLater(() -> {
						pane.getChildren().add(newRect);
					});
					pos1++;
					delay();
				}
				delay();
			}
		}
		
		if (curGraphType == Constants.DOTS) { // if current graph is bars
			
			// initialize buckets
			List<List<Circle>> buckets = new ArrayList<>(bucketNumber);
			for (int i = 0; i < bucketNumber; i++)
				buckets.add(new ArrayList<>());
			
			// Distribute circle
			for (int i = 0; i < size; i++) {
				Circle cur = service.getCircle(pane, i);
				double h = maxHeight - cur.getCenterY();
				buckets.get(getBucket(h, maxHeight, bucketNumber)).add(cur);
			}
			
			for (int i = 0; i < size; i++) {
				Circle c = service.getCircle(pane, i);
				Platform.runLater(() -> {
					pane.getChildren().remove(c);
				});
			}
			
			// Place buckets to pane by order
			int pos = 0;
			for (List<Circle> bkt : buckets) {
				for (int i = 0; i < bkt.size(); ++i) {
					int pos1 = pos;
					Circle newCircle = bkt.get(i);
					newCircle.setCenterX(pos1 * xpart + newCircle.getRadius());
					Platform.runLater(() -> {
						pane.getChildren().add(newCircle);
					});
					pos++;
					delay();
				}
				delay();
			}
			
			// Sort each bucket and update UI
			pos = 0;
			int pos1 = 0;
			for (List<Circle> bkt : buckets) {
				bkt.sort((c1, c2) -> {
					return (int) (c2.getCenterY() - c1.getCenterY());
				});
				for (int i = 0; i < bkt.size(); ++i) {	// remove circles in pane which are in each bucket 
					Circle newCircle = bkt.get(i);
					Platform.runLater(() -> {
						pane.getChildren().remove(newCircle);
					});
					pos++;
				}
				delay();
				for (int i = 0; i < bkt.size(); ++i) {	// add sorted circles in each buckets to pane
					Circle newCircle = bkt.get(i);
					newCircle.setCenterX(xpart * pos1 + newCircle.getRadius());
					Platform.runLater(() -> {
						pane.getChildren().add(newCircle);
					});
					pos1++;
					delay();
				}
				delay();
			}
		}
	}

}
