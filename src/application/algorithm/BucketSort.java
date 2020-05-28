package application.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

public class BucketSort extends SortTask{
	private double xpart = pane.getPrefWidth() / size;

	public BucketSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay  * 10, curGraphType, pane, service);
	}
	
	private static int getBucket(double h, double maxHeight, int bucketNumber) {
		return (int) ((double) h / maxHeight * (bucketNumber - 1));
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		double maxHeight = pane.getPrefHeight();
		final int bucketNumber = (int) Math.sqrt(size);
		List<List<Rectangle>> buckets = new ArrayList<>(bucketNumber);
		
		for (int i = 0; i < bucketNumber; i++) 
			buckets.add(new ArrayList<>());
		
		if (curGraphType == Constants.BARS) { // if current graph is bars
			// Distribute rectangles
			for (int i = 0; i < size; i++) {
				Rectangle cur = service.getRect(pane, i);
				double h = cur.getHeight();
				buckets.get(getBucket(h, maxHeight, bucketNumber)).add(cur);
			}
			
			// Place buckets to pane by order
			int pos = 0;
			for (int i = 0; i < buckets.size(); i++) {
				int pos1 = pos;
				for (int j = 0; j < buckets.get(i).size(); j++) {
					Rectangle oldRect = service.getRect(pane, pos);
					FutureTask<Void> rmRect = new FutureTask<Void>(() -> {
						pane.getChildren().remove(oldRect);
					}, /* return value from task: */ null);
					Platform.runLater(rmRect);
					System.out.println(oldRect);
					pos++;
					delay();
				}
				delay();
				for (int j = 0; j < buckets.get(i).size(); j++) {
					Rectangle newRect = buckets.get(i).get(j);
					newRect.setX(xpart * pos1);
					FutureTask<Void> insRect = new FutureTask<Void>(() -> {
						pane.getChildren().add(newRect);
					}, /* return value from task: */ null);
					Platform.runLater(insRect);
					pos1++;
					delay();
				}
				System.out.println(pos);
				delay();
				delay();
			}
			delay();
			delay();
			
			// Sort each bucket
			for (int i = 0; i < buckets.size(); i++) {
				buckets.get(i).sort( (r1, r2) -> {
					return (int) (r1.getHeight() - r2.getHeight());
				} );
			}
			
			// merge buckets to get the final order
			List<Rectangle> sortedRects = new ArrayList<>();
			for (List<Rectangle> bucket : buckets) {
				sortedRects.addAll(bucket);
			}
			
			// remove all rectangles in pane
			for (int i = 0; i < size; i++) {
				Rectangle oldRect = service.getRect(pane, i);
				Platform.runLater(() -> {
					pane.getChildren().remove(oldRect);
				});
				delay();
			}
			
			// place rectangles in pane in sorted order
			for (int i = 0; i < sortedRects.size(); ++i) {
				Rectangle newRect = sortedRects.get(i);
				newRect.setX(xpart * i);
				Platform.runLater(() -> {
					pane.getChildren().add(newRect);
				});
				delay();
			}
		}
	}


}
