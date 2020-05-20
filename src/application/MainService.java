package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class MainService {
	
	public void generate(Pane mainPane, int arraySize, String curGraphType) {
		if (curGraphType == Constants.BARS)
			generateRectangle(mainPane, arraySize);
		if (curGraphType == Constants.DOTS)
			generateCircle(mainPane, arraySize);
	}

	public void generateRectangle(Pane mainPane, int arraySize) {
		for (int i = 0; i < arraySize; i++) {
			Rectangle r = new Rectangle(mainPane.getPrefWidth() / (arraySize),
					10 + (int) (Math.random() * (mainPane.getPrefHeight() - 20)));
			r.setFill(Color.rgb(157, 133, 255));
			r.setStroke(Color.WHITE);
			if (arraySize >= 150)
				r.setStrokeWidth(0.1);
			else
				r.setStrokeWidth(0.5);
			r.setLayoutX(i * r.getWidth());
			r.setLayoutY(mainPane.getPrefHeight() - r.getHeight());
			r.setId(i + "");
			mainPane.getChildren().add(r);
//			System.out.println(getYValue(r));
		}
//		System.out.println(mainPane.getChildren().get(10).getLayoutY());
	}

	public void generateCircle(Pane mainPane, int arraySize) {
		for (int i = 0; i < arraySize; i++) {
			Circle c = new Circle(mainPane.getPrefWidth() / (2 * arraySize));
			c.setLayoutX(i * c.getRadius() * 2 + c.getRadius());
			c.setLayoutY(10 + (int) (Math.random() * (mainPane.getPrefHeight() - 20)));
			c.setFill(Color.rgb(157, 133, 255));
			c.setId(i + "");
			mainPane.getChildren().add(c);
//			System.out.println(getYValue(c));
		}
	}
	
	public double getYValue(Pane mainPane, Shape s) {
		return mainPane.getPrefHeight() - s.getLayoutY();
	}

	public void delay(int delayTime) throws InterruptedException {
		Thread.sleep(delayTime);
	}
	
	public void swapObj(Shape s1, Shape s2) {
		double temp = s1.getLayoutX();
		String id = s1.getId();
		s1.setLayoutX(s2.getLayoutX());
		s2.setLayoutX(temp);
		s1.setId(s2.getId());
		s2.setId(id);
	}
}
