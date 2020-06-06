package application.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import application.constant.Constants;
import application.service.MainService;
import application.task.SortTask;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BucketSort extends SortTask {
	private double xpart = pane.getPrefWidth() / size;
	private SequentialTransition sq = new SequentialTransition();
	private String prevStr = textArea.getText();

	public BucketSort(int size, int delay, String curGraphType, Pane pane, MainService service, TextArea textArea) {
		super(size, delay * 2, curGraphType, pane, service, textArea);
	}

	private int getBucket(double h, double maxHeight, int bucketNumber) {
		return (int) ((double) h / maxHeight * (bucketNumber - 1));
	}

	@Override
	public void doSort() throws InterruptedException, ExecutionException {
		double maxHeight = pane.getPrefHeight() + 10;
		final int bucketNumber = (int) Math.sqrt(size);

		textArea.setText(prevStr + "\n---------------\nSorting . . . ");

		if (curGraphType == Constants.BARS) { // if current graph is bars

			// initialize buckets
			List<List<Rectangle>> buckets = new ArrayList<>(bucketNumber);
			for (int i = 0; i < bucketNumber; i++)
				buckets.add(new ArrayList<>());

			// Distribute rectangles
			Platform.runLater(() -> {
				textArea.setText(prevStr + "\n---------------\nDistribute to " + (buckets.size() - 1) + " buckets . . . ");
			});
			for (int i = 0; i < size; i++) {
				Rectangle cur = service.getRect(pane, i);
				double h = cur.getHeight();
				buckets.get(getBucket(h, maxHeight, bucketNumber)).add(cur);
				Platform.runLater(() -> {
					cur.setFill(Constants.paintArray[getBucket(h, maxHeight, bucketNumber)]);
				});
				delay();
			}

			for (int i = 0; i < size; i++) {
				Rectangle r = service.getRect(pane, i);
				FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
					pane.getChildren().remove(r);
				}, null);
				Platform.runLater(updateUITask);
				updateUITask.get();
			}

			// Place buckets to pane by order
			Platform.runLater(() -> {
				textArea.setText(prevStr + "\n---------------\nPlace buckets in order . . . ");
			});
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
					delay();
				}
				delay();
				delay();
			}

			// Sort each bucket and update UI
			Platform.runLater(() -> {
				textArea.setText(prevStr + "\n---------------\nSort each buckets . . . ");
			});
			pos = 0;
			for (List<Rectangle> bkt : buckets) {
				bkt.sort((r1, r2) -> { // sort bucket
					return (int) (r1.getHeight() - r2.getHeight());
				});

				ParallelTransition pt = new ParallelTransition();

				for (int i = 0; i < bkt.size(); ++i) {
					Rectangle newRect = bkt.get(i);
					TranslateTransition tt = new TranslateTransition(Duration.millis(delay * 3), newRect);
					tt.setByX(pos * xpart - newRect.getX());
					pt.getChildren().add(tt);
					pos++;
				}
				sq.getChildren().add(pt);
				if (bkt != buckets.get(buckets.size() - 1))
					sq.getChildren().add(new TranslateTransition(Duration.millis(delay * 2)));
			}
			delay();
			sq.play();
			delay();
		}

		if (curGraphType == Constants.DOTS) { // if current graph is bars

			// initialize buckets
			List<List<Circle>> buckets = new ArrayList<>(bucketNumber);
			for (int i = 0; i < bucketNumber; i++)
				buckets.add(new ArrayList<>());

			// Distribute circle
			Platform.runLater(() -> {
				textArea.setText(prevStr + "\n---------------\nDistribute to " + (buckets.size() - 1) + " buckets . . . ");
			});
			for (int i = 0; i < size; i++) {
				Circle cur = service.getCircle(pane, i);
				double h = maxHeight - cur.getCenterY();
				buckets.get(getBucket(h, maxHeight, bucketNumber)).add(cur);
				Platform.runLater(() -> {
					cur.setFill(Constants.paintArray[getBucket(h, maxHeight, bucketNumber)]);
				});
				delay();
			}

			for (int i = 0; i < size; i++) {
				Circle c = service.getCircle(pane, i);
				FutureTask<Void> updateUITask = new FutureTask<Void>(() -> {
					pane.getChildren().remove(c);
				}, null);
				Platform.runLater(updateUITask);
				updateUITask.get();
			}

			// Place buckets to pane by order
			Platform.runLater(() -> {
				textArea.setText(prevStr + "\n---------------\nPlace buckets in order . . . ");
			});
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
			Platform.runLater(() -> {
				textArea.setText(prevStr + "\n---------------\nSort each buckets . . . ");
			});
			pos = 0;
			for (List<Circle> bkt : buckets) {
				bkt.sort((r1, r2) -> { // sort bucket
					return (int) (-r1.getCenterY() + r2.getCenterY());
				});
				ParallelTransition pt = new ParallelTransition();

				for (int i = 0; i < bkt.size(); ++i) {
					Circle newRect = bkt.get(i);
					TranslateTransition tt = new TranslateTransition(Duration.millis(delay * 3), newRect);
					tt.setByX(pos * xpart - newRect.getCenterX() + newRect.getRadius());
					pt.getChildren().add(tt);
					pos++;
				}
				sq.getChildren().add(pt);
				if (bkt != buckets.get(buckets.size() - 1))
					sq.getChildren().add(new TranslateTransition(Duration.millis(delay * 2)));
			}
			delay();
			sq.play();
			delay();
		}

		sq.setOnFinished((e) -> {
			textArea.setText(prevStr + "\n---------------\nSorting Done");
		});
	}
}

//			pos = 0;
//			int pos1 = 0;
//			for (List<Circle> bkt : buckets) {
//				bkt.sort((c1, c2) -> {
//					return (int) (c2.getCenterY() - c1.getCenterY());
//				});
//				for (int i = 0; i < bkt.size(); ++i) { // remove circles in pane which are in each bucket
//					Circle newCircle = bkt.get(i);
//					Platform.runLater(() -> {
//						pane.getChildren().remove(newCircle);
//					});
//					pos++;
//				}
//				delay();
//				for (int i = 0; i < bkt.size(); ++i) { // add sorted circles in each buckets to pane
//					Circle newCircle = bkt.get(i);
//					newCircle.setCenterX(xpart * pos1 + newCircle.getRadius());
//					Platform.runLater(() -> {
//						pane.getChildren().add(newCircle);
//					});
//					pos1++;
//					delay();
//				}
//				delay();
//			}
