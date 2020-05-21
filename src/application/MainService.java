package application;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MainService {

	public void generate(Pane pane, int size, String curGraphType) {
		pane.getChildren().clear(); 
		if (curGraphType == Constants.BARS)
			generateRectangle(pane, size);
		if (curGraphType == Constants.DOTS)
			generateCircle(pane, size);
	}

	public void generateRectangle(Pane pane, int size) {
		double xpart = pane.getPrefWidth() / size;
		double ypart = pane.getPrefHeight() / size;

		List<Integer> lst = IntStream.range(1, size + 1).boxed().collect(Collectors.toList());
		for (int i = 0; i < size; i++) {
			Random random = new Random(); // generate distinct height index for each rectangle
			int randomY = lst.get(random.nextInt(lst.size()));
			lst.remove(lst.indexOf(randomY));

			Rectangle r = new Rectangle(0, 0, xpart, randomY * ypart);
			r.setFill(Color.rgb(157, 133, 255));
			r.setStroke(Color.WHITE);
			if (size >= 150)
				r.setStrokeWidth(0.1);
			else
				r.setStrokeWidth(0.5);
			r.setX(i * r.getWidth());
			r.setY(pane.getPrefHeight() - r.getHeight());
			r.setId(i + "");
//			System.out.println(r); // debug

			pane.getChildren().add(r);
		}
	}

	public void generateCircle(Pane pane, int size) {
		List<Integer> lst = IntStream.range(0, size).boxed().collect(Collectors.toList());
		double radius = pane.getPrefWidth() / (2 * size);
		double ypart = (pane.getPrefHeight() - 2 * radius) / size;

		for (int i = 0; i < size; i++) {
			Random r = new Random(); // generate distinct height index for each circle
			int randomY = lst.get(r.nextInt(lst.size()));
			lst.remove(lst.indexOf(randomY));

			Circle c = new Circle(radius); // radius
			c.setCenterX(i * radius * 2 + radius);
			c.setCenterY(randomY * ypart + radius);
			c.setFill(Color.rgb(157, 133, 255));
			c.setId(i + "");
//			System.out.println(c); // debug

			pane.getChildren().add(c);
		}
	}

	public void swapRectangle(Rectangle s1, Rectangle s2) {
		double temp = s1.getHeight();
		double tempy = s1.getY();
		Paint tempColor = s1.getFill();

		s1.setHeight(s2.getHeight());
		s1.setY(s2.getY());
//		s1.setFill(s2.getFill());

		s2.setHeight(temp);
		s2.setY(tempy);
//		s2.setFill(tempColor);
	}

	public void swapCircle(Circle c1, Circle c2) {
		double temp = c1.getCenterY();
		c1.setCenterY(c2.getCenterY());
		c2.setCenterY(temp);
	}
	
	public Rectangle getRect(Pane pane, int i) {
		return (Rectangle) pane.lookup("#" + i);
	}
	
	public Circle getCircle(Pane pane, int i) {
		return (Circle) pane.lookup("#" + i);
	}
}
