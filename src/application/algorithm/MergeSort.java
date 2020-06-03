package application.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import application.constant.Constants;
import application.service.MainService;
import application.task.SortTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class MergeSort extends SortTask {
	
	private double xpart = pane.getPrefWidth() / size;

	public MergeSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		if (curGraphType == Constants.BARS) {
			List<Shape> list = new ArrayList<>(); // init a list of current rectangles in pane
			IntStream.range(0, size).forEach(i -> {
				list.add(service.getRect(pane, i));
			});
			sorted(list); // call sort
		}

		if (curGraphType == Constants.DOTS) {
			List<Shape> list = new ArrayList<>(); // init a list of current circles in pane
			IntStream.range(0, size).forEach(i -> {
				list.add(service.getCircle(pane, i));
			});
			sorted(list); // call sort
		}
	}

	public List<Shape> sorted(List<Shape> list) throws InterruptedException, ExecutionException {
		if (list.size() < 2) {
			return list;
		}
		int mid = list.size() / 2;
		return merged(sorted(list.subList(0, mid)), sorted(list.subList(mid, list.size())));
	}

	public List<Shape> merged(List<Shape> left, List<Shape> right) throws InterruptedException, ExecutionException {
		System.out.println(delay);
		List<Integer> idList = new ArrayList<>();
		left.forEach(i -> idList.add(Integer.parseInt(i.getId())));
		right.forEach(i -> idList.add(Integer.parseInt(i.getId())));

		// list of ids to update ui of each rectangle
		List<Integer> ids = idList.stream().sorted().collect(Collectors.toList());
		System.out.println(ids);

		// -- merge logic
		int leftIndex = 0;
		int rightIndex = 0;
		List<Shape> merged = new ArrayList<>();
		
		left.forEach(shape -> shape.setFill(Color.DODGERBLUE));
		right.forEach(shape -> shape.setFill(Color.DODGERBLUE));
		
		while (leftIndex < left.size() && rightIndex < right.size()) {
			Shape currLeft = left.get(leftIndex);
			Shape currRight = right.get(rightIndex);
			Platform.runLater(() -> {
				currLeft.setFill(Constants.RED);
				currRight.setFill(Constants.RED);
			});
			
			if (curGraphType == Constants.BARS)
				if (((Rectangle) currLeft).getHeight() < ((Rectangle) currRight).getHeight()) {
					merged.add(left.get(leftIndex++));
				} else {
					merged.add(right.get(rightIndex++));
				}
			
			if (curGraphType == Constants.DOTS)
				if (((Circle) currLeft).getCenterY() > ((Circle) currRight).getCenterY()) {
					merged.add(left.get(leftIndex++));
				} else {
					merged.add(right.get(rightIndex++));
				}
			
			delay();
			Platform.runLater(() -> {
				currLeft.setFill(Color.DODGERBLUE);
				currRight.setFill(Color.DODGERBLUE);
			});
			delay();
		}
		
		merged.addAll(left.subList(leftIndex, left.size()));
		merged.addAll(right.subList(rightIndex, right.size()));

		// done merge --> update ui by clearing pane in range and adding "merged"
		// clear pane
		for (int i = 0; i < merged.size(); i++) {
			int j = i;
			FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
				pane.getChildren().remove(merged.get(j));
			}, null);
			Platform.runLater(updateUITask); 
			updateUITask.get(); 
		}
		delay();
		delay();
		
		// add merged rectangle to pane
		for (int i = 0; i < merged.size(); i++) {
			Shape temp = merged.get(i); // get item in merged list

			if (temp instanceof Rectangle)
				((Rectangle) temp).setX(xpart * ids.get(i)); // set X to locate from the start of left list
			if (temp instanceof Circle)
				((Circle) temp).setCenterX(xpart * ids.get(i) + ((Circle) temp).getRadius());

			FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
//				pane.getChildren().remove(merged.get(j));
				pane.getChildren().add(temp);
			}, null);
			Platform.runLater(updateUITask); 
			updateUITask.get(); 

			delay();
			Platform.runLater(() -> {
				temp.setFill(Constants.PRIMARY);
			});
		}
		delay();

		return merged;
	}
}
