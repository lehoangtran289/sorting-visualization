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

	public BucketSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		double maxHeight = pane.getPrefHeight();
		if (curGraphType == Constants.BARS) { // if current graph is bars
			List<List<Rectangle>> bucket = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				Rectangle cur = service.getRect(pane, i);
				double h = cur.getHeight();
				bucket.get((int) ((int) h / maxHeight * size)).add(cur);
			}
			
			for (int i = 0; i < bucket.size(); i++) {
				bucket.get(i).sort( (r1, r2) -> {
					return (int) (r1.getHeight() - r2.getHeight());
				} );
			}
			
			for (int i = 0; i < bucket.size(); i++) {
				
			}
		}
	}


}
