package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MainService {

	public void generate(Pane mainPane, int arraySize, String curGraphType) {
		if (curGraphType == Constants.BARS)
			generateRectangle(mainPane, arraySize);
		if (curGraphType == Constants.DOTS)
			generateCircle(mainPane, arraySize);
	}

	public void generateRectangle(Pane mainPane, int arraySize) {
		double xpart = mainPane.getPrefWidth() / arraySize;
		double ypart = mainPane.getPrefHeight() / arraySize;

		for (int i = 0; i < arraySize; i++) {
			Rectangle r = new Rectangle(0, 0, xpart, 10 + (int) (Math.random() * (mainPane.getPrefHeight() - 20)));
			r.setFill(Color.rgb(157, 133, 255));
			r.setStroke(Color.WHITE);
			if (arraySize >= 150)
				r.setStrokeWidth(0.1);
			else
				r.setStrokeWidth(0.5);
			r.setX(i * r.getWidth());
			r.setY(mainPane.getPrefHeight() - r.getHeight());
			r.setId(i + "");
//			System.out.println(r); // debug

			mainPane.getChildren().add(r);
		}
	}

	public void generateCircle(Pane mainPane, int arraySize) {
		for (int i = 0; i < arraySize; i++) {
			Circle c = new Circle(mainPane.getPrefWidth() / (2 * arraySize));
			c.setCenterX(i * c.getRadius() * 2 + c.getRadius());
			c.setCenterY(c.getRadius() + (int) (Math.random() * (mainPane.getPrefHeight() - 2 * c.getRadius())));
			c.setFill(Color.rgb(157, 133, 255));
			c.setId(i + "");
//			System.out.println(c); // debug

			mainPane.getChildren().add(c);
		}
	}

	public void swapRectangle(Rectangle s1, Rectangle s2) {
		double temp = s1.getHeight();
		double tempy = s1.getY();

		s1.setHeight(s2.getHeight());
		s1.setY(s2.getY());

		s2.setHeight(temp);
		s2.setY(tempy);
	}

	public void swapCircle(Circle c1, Circle c2) {
		double temp = c1.getCenterY();
		c1.setCenterY(c2.getCenterY());
		c2.setCenterY(temp);
	}
}
