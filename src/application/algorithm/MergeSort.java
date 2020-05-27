package application.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import application.Constants;
import application.MainService;
import application.task.SortTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class MergeSort extends SortTask {
	private double xpart = pane.getPrefWidth() / size;

	public MergeSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay * 4, curGraphType, pane, service);
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
		List<Integer> idList = new ArrayList<>();
		left.forEach(i -> idList.add(Integer.parseInt(i.getId())));
		right.forEach(i -> idList.add(Integer.parseInt(i.getId())));

		// list of ids to update ui of each rectangle
		List<Integer> ids = idList.stream().sorted().collect(Collectors.toList());

		// -- merge logic
		int leftIndex = 0;
		int rightIndex = 0;
		List<Shape> merged = new ArrayList<>();

		while (leftIndex < left.size() && rightIndex < right.size()) {
			if (curGraphType == Constants.BARS)
				if (((Rectangle) left.get(leftIndex)).getHeight() < ((Rectangle) right.get(rightIndex)).getHeight()) {
					merged.add(left.get(leftIndex++));
				} else {
					merged.add(right.get(rightIndex++));
				}
			if (curGraphType == Constants.DOTS)
				if (((Circle) left.get(leftIndex)).getCenterY() > ((Circle) right.get(rightIndex)).getCenterY()) {
					merged.add(left.get(leftIndex++));
				} else {
					merged.add(right.get(rightIndex++));
				}

		}
		merged.addAll(left.subList(leftIndex, left.size()));
		merged.addAll(right.subList(rightIndex, right.size()));

		// done merge --> update ui
		// clear pane in range and add "merged"
		for (int i = 0; i < merged.size(); i++) {
			int j = i;
			Shape temp = merged.get(i); // get item in merged list

			if (temp instanceof Rectangle)
				((Rectangle) temp).setX(xpart * ids.get(i)); // set X to locate from the start of left list
			if (temp instanceof Circle)
				((Circle) temp).setCenterX(xpart * ids.get(i) + ((Circle) temp).getRadius());

			Platform.runLater(() -> {
				pane.getChildren().remove(merged.get(j));
				pane.getChildren().add(temp);
			});

			delay();
		}
		delay();

		return merged;
	}

//	public List<Rectangle> sortedR(List<Rectangle> list) throws InterruptedException, ExecutionException {
//		if (list.size() < 2) {
//			return list;
//		}
//		int mid = list.size() / 2;
//		return mergedR(sortedR(list.subList(0, mid)), sortedR(list.subList(mid, list.size())));
//	}
//
//	public List<Rectangle> mergedR(List<Rectangle> left, List<Rectangle> right)
//			throws InterruptedException, ExecutionException {
//
//		List<Integer> idList = new ArrayList<>();
//		left.forEach(i -> idList.add(Integer.parseInt(i.getId())));
//		right.forEach(i -> idList.add(Integer.parseInt(i.getId())));
//
//		// list of ids to update ui of each rectangle
//		List<Integer> ids = idList.stream().sorted().collect(Collectors.toList());
//
//		// -- merge logic
//
//		int leftIndex = 0;
//		int rightIndex = 0;
//		List<Rectangle> merged = new ArrayList<>();
//
//		while (leftIndex < left.size() && rightIndex < right.size()) {
//			if (left.get(leftIndex).getHeight() < right.get(rightIndex).getHeight()) {
//				merged.add(left.get(leftIndex++));
//			} else {
//				merged.add(right.get(rightIndex++));
//			}
//		}
//		merged.addAll(left.subList(leftIndex, left.size()));
//		merged.addAll(right.subList(rightIndex, right.size()));
//
//		// done merge --> update ui
//		// clear pane in range and add "merged"
//		for (int i = 0; i < merged.size(); i++) {
//			int j = i;
//			Rectangle temp = merged.get(i); // get item in merged list
//			temp.setX(xpart * ids.get(i)); // set that item's X to locate from the start of left list
//			Platform.runLater(() -> {
//				pane.getChildren().remove(merged.get(j));
//				pane.getChildren().add(temp);
//			});
//			delay();
//		}
//		delay();
//
//		return merged;
//	}
//
//	public List<Circle> sortedC(List<Circle> list) throws InterruptedException, ExecutionException {
//		if (list.size() < 2) {
//			return list;
//		}
//		int mid = list.size() / 2;
//		return mergedC(sortedC(list.subList(0, mid)), sortedC(list.subList(mid, list.size())));
//	}
//
//	public List<Circle> mergedC(List<Circle> left, List<Circle> right) throws InterruptedException, ExecutionException {
//
//		List<Integer> idList = new ArrayList<>();
//		left.forEach(i -> idList.add(Integer.parseInt(i.getId())));
//		right.forEach(i -> idList.add(Integer.parseInt(i.getId())));
//
//		// list of ids to update ui of each rectangle
//		List<Integer> ids = idList.stream().sorted().collect(Collectors.toList());
//
//		// -- merge logic
//
//		int leftIndex = 0;
//		int rightIndex = 0;
//		List<Circle> merged = new ArrayList<>();
//
//		while (leftIndex < left.size() && rightIndex < right.size()) {
//			if (left.get(leftIndex).getCenterY() > right.get(rightIndex).getCenterY()) {
//				merged.add(left.get(leftIndex++));
//			} else {
//				merged.add(right.get(rightIndex++));
//			}
//		}
//		merged.addAll(left.subList(leftIndex, left.size()));
//		merged.addAll(right.subList(rightIndex, right.size()));
//
//		// done merge --> update ui
//		// clear pane in range and add "merged"
//		for (int i = 0; i < merged.size(); i++) {
//			int j = i;
//			Circle temp = merged.get(i); // get item in merged list
//			temp.setCenterX(xpart * ids.get(i) + temp.getRadius()); // set that item's X to locate from the start of
//																	// left list
//			Platform.runLater(() -> {
//				pane.getChildren().remove(merged.get(j));
//				pane.getChildren().add(temp);
//			});
//			delay();
//		}
//		delay();
//
//		return merged;
//	}
//
//	public Rectangle findRectWithX(double x) { // not use yet
//		Optional<Node> res = pane.getChildren().stream().filter(i -> ((Rectangle) i).getX() == x).findFirst();
//		if (!res.isEmpty())
//			return (Rectangle) res.get();
//		return null;
//	}

}
