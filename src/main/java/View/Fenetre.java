package View;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Gère l'interface de l'application
 * @author Nico
 *
 */
public class Fenetre extends Application {
	
	@Override
	public void start(final Stage primaryStage) {
		Button button = new Button();
		button.setText("Rechercher un personnage");
		BackgroundImage back = new BackgroundImage(new Image("Marvel.png"),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		
		button.setOnAction(new EventHandler<ActionEvent>()  {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		StackPane root = new StackPane();
		root.getChildren().add(button);
		root.setBackground(new Background(back));
		
		Scene scene = new Scene(root,800,600);
		
		primaryStage.setTitle("Univers Marvel");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
