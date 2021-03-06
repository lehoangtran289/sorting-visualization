package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/sample.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.getIcons().add(new Image("/resources/1200px-Logo_Hust.png"));
			primaryStage.setTitle("Sorting Visualization");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
	            if (event.getCode() == KeyCode.F12) {
	                boolean newValue = !primaryStage.isFullScreen();
	                primaryStage.setAlwaysOnTop(newValue);
	                primaryStage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.E));
	                primaryStage.setFullScreen(newValue);
	            }
	        });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
