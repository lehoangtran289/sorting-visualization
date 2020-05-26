package application.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import application.MainService;
import application.task.SortTask;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class MergeSort extends SortTask {

	public MergeSort(int size, int delay, String curGraphType, Pane pane, MainService service) {
		super(size, delay, curGraphType, pane, service);
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		List<Rectangle> list = new ArrayList<>();
		IntStream.range(0, size).forEach(i -> {
			list.add(service.getRect(pane, i));
		});

		sorted(list).forEach(i -> {
			System.out.println(i);
		});

		List<Rectangle> result = sorted(list);
		for (int i = 0; i < result.size(); ++i)
			result.get(i).setX((pane.getPrefWidth() / size) * i);

		Platform.runLater(() -> {
			pane.getChildren().clear();
			result.forEach(rect -> pane.getChildren().add(rect));
		});
	}

	public List<Rectangle> sorted(List<Rectangle> list) {
		if (list.size() < 2) {
			return list;
		}
		int mid = list.size() / 2;
		return merged(sorted(list.subList(0, mid)), sorted(list.subList(mid, list.size())));
	}

	public List<Rectangle> merged(List<Rectangle> left, List<Rectangle> right) {
		int i = 0;
		int j = 0;
		List<Rectangle> merged = new ArrayList<>();

		while (i < left.size() && j < right.size()) {
			if (left.get(i).getHeight() < right.get(j).getHeight()) {
				merged.add(left.get(i++));
			} else {
				merged.add(right.get(j++));
			}
		}
		merged.addAll(left.subList(i, left.size()));
		merged.addAll(right.subList(j, right.size()));
		// clear pane and addAll "merged"
		return merged;
	}

}
