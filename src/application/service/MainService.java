package application.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import application.constant.Constants;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class MainService {
	
	// generate pane according to size and graph type
	public void generate(Pane pane, int size, String curGraphType) {
		pane.getChildren().clear(); 	// clear current pane
		if (curGraphType == Constants.BARS)
			generateRectangle(pane, size);
		if (curGraphType == Constants.DOTS)
			generateCircle(pane, size);
	}
	
	// generate pane of Rectangles
	public void generateRectangle(Pane pane, int size) {
		double xpart = pane.getPrefWidth() / size;	// 1 part of Pane's width
		double ypart = pane.getPrefHeight() / size;	// 1 part of Pane's height

		List<Integer> lst = IntStream.range(1, size + 1).boxed().collect(Collectors.toList()); 
		for (int i = 0; i < size; i++) {
			Random random = new Random(); 			// generate distinct height index for each rectangle
			int randomY = lst.get(random.nextInt(lst.size()));
			lst.remove(lst.indexOf(randomY));
			
			// config rectangle
			Rectangle r = new Rectangle(0, 0, xpart, randomY * ypart);
			r.setFill(Constants.PRIMARY);
			r.setStroke(Color.WHITE);
			if (size >= 150)
				r.setStrokeWidth(0.1);
			else
				r.setStrokeWidth(0.5);
			r.setX(i * r.getWidth());
			r.setY(pane.getPrefHeight() - r.getHeight());
			r.setId(i + "");
			
			//add rectangle to pane
			pane.getChildren().add(r);
		}
	}
	
	// generate pane of Circles
	public void generateCircle(Pane pane, int size) {
		List<Integer> lst = IntStream.range(0, size).boxed().collect(Collectors.toList());
		double radius = pane.getPrefWidth() / (2 * size);		
		double ypart = (pane.getPrefHeight() - 2 * radius) / size;

		for (int i = 0; i < size; i++) {
			Random r = new Random(); 				// generate distinct height index for each circle
			int randomY = lst.get(r.nextInt(lst.size()));
			lst.remove(lst.indexOf(randomY));
			
			// config rectangle
			Circle c = new Circle(radius); 		
			c.setCenterX(i * radius * 2 + radius);
			c.setCenterY(randomY * ypart + radius);
			c.setFill(Constants.PRIMARY);
			c.setId(i + "");
			
			//add circle to pane
			pane.getChildren().add(c);
		}
	}
	
	// swap = swap height and swap y
	public void swapRectangle(Rectangle s1, Rectangle s2) {
		double temp = s1.getHeight();
		double tempy = s1.getY();

		s1.setHeight(s2.getHeight());
		s1.setY(s2.getY());

		s2.setHeight(temp);
		s2.setY(tempy);
	}
	
	// swap = swap center Y
	public void swapCircle(Circle c1, Circle c2) {
		double temp = c1.getCenterY();
		c1.setCenterY(c2.getCenterY());
		c2.setCenterY(temp);
	}
	
	// get Rectangle with given id
	public Rectangle getRect(Pane pane, int i) {
		return (Rectangle) pane.lookup("#" + i);
	}
	
	// get Circle with given id
	public Circle getCircle(Pane pane, int i) {
		return (Circle) pane.lookup("#" + i);
	}
}
