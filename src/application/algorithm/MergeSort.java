package application.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import application.MainService;
import application.task.SortTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class MergeSort extends SortTask {
	private double xpart = pane.getPrefWidth() / size;

	public MergeSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		List<Rectangle> list = new ArrayList<>();
		IntStream.range(0, size).forEach(i -> {
			list.add(service.getRect(pane, i));
		});

//		sorted(list).forEach(i -> {
//			System.out.println(i);
//		});

		List<Rectangle> result = sorted(list);
	}

	public List<Rectangle> sorted(List<Rectangle> list) throws InterruptedException, ExecutionException {
		if (list.size() < 2) {
			return list;
		}
		int mid = list.size() / 2;
		return merged(sorted(list.subList(0, mid)), sorted(list.subList(mid, list.size())));
	}

	public List<Rectangle> merged(List<Rectangle> left, List<Rectangle> right)
			throws InterruptedException, ExecutionException {

		List<Integer> idList = new ArrayList<>(); // list of ids to update ui of each rectangle
		left.forEach(i -> idList.add(Integer.parseInt(i.getId())));
		right.forEach(i -> idList.add(Integer.parseInt(i.getId())));
		List<Integer> ids = idList.stream().sorted().collect(Collectors.toList());
		System.out.println(ids);

		// -- logic

		int leftIndex = 0;
		int rightIndex = 0;
		List<Rectangle> merged = new ArrayList<>();

		while (leftIndex < left.size() && rightIndex < right.size()) {
			if (left.get(leftIndex).getHeight() < right.get(rightIndex).getHeight()) {
				merged.add(left.get(leftIndex++));
			} else {
				merged.add(right.get(rightIndex++));
			}
		}
		merged.addAll(left.subList(leftIndex, left.size()));
		merged.addAll(right.subList(rightIndex, right.size()));
		delay();

		// clear pane in range and addAll "merged"
		System.out.println(" --------------- ");
		merged.forEach(i -> System.out.println(i));
		System.out.println("--");

		for (int i = 0; i < merged.size(); i++) {
			int j = i;
			Platform.runLater(() -> {
				pane.getChildren().remove(merged.get(j));
			});
		}
		for (int i = 0; i < merged.size(); i++) {
			int j = i;
			Rectangle temp = merged.get(i);
			temp.setX(xpart * ids.get(i));
			Platform.runLater(() -> {
				pane.getChildren().add(temp);
			});
//			delay();
		}
		delay();
		return merged;
	}

}
