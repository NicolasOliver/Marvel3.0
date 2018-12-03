package View;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


import org.json.JSONException;

import Controler.Database;
import Model.Parse;
import Model.Personnage;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Gère l'interface de l'application
 * @author Nico
 *
 */
public class Fenetre extends Application {
	
	Stage primaryStage;
	static StackPane root = new StackPane();
	Scene scene = new Scene(root,800,600);
	BackgroundImage back = new BackgroundImage(new Image("Marvel.png"),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	Button herosBtn = new Button("Recherche un personnage");
	Button comicsBtn = new Button("Rechercher un comics");
	Button dbBtn = new Button("Se connecter");
	Button yesBtn = new Button("Oui");
	Button noBtn = new Button("Non");
	Button backBtn = new Button("Retour");
	Button validateBtn = new Button("Valider");
	Button decoBtn = new Button("Se déconnecter");
	Button biblioBtn = new Button("Bibliothèque");
	Alert alert = new Alert(AlertType.INFORMATION);
	Boolean isConnected = false;
	Personnage perso = null;
	HBox hbHeros;
	
	public void start() {
		
		herosBtn.setMaxWidth(200);
		comicsBtn.setMaxWidth(200);
		dbBtn.setMaxWidth(200);
		decoBtn.setMaxWidth(200);
		biblioBtn.setMaxWidth(200);
		
		VBox vbButtons = new VBox();
		vbButtons.setSpacing(20);
		vbButtons.setPadding(new Insets(300, 20, 10, 300));
		if(isConnected) {
			vbButtons.getChildren().addAll(herosBtn,comicsBtn,biblioBtn,decoBtn);
		}
		else {
			vbButtons.getChildren().addAll(herosBtn,comicsBtn,dbBtn);
		}
		
		root.getChildren().add(vbButtons);
		root.setBackground(new Background(back));
		
		primaryStage.setTitle("Marvel Universe");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		herosBtn.setOnAction(new EventHandler<ActionEvent>()  {

			@Override
			public void handle(ActionEvent event) {
				searchHeros();
				
			}
			
		});
		
		comicsBtn.setOnAction(new EventHandler<ActionEvent>()  {

			@Override
			public void handle(ActionEvent event) {
				searchComics();
			}
			
		});
		
		dbBtn.setOnAction(new EventHandler<ActionEvent>()  {

			@Override
			public void handle(ActionEvent event) {
				connect(); }
			
		});
		
		decoBtn.setOnAction(new EventHandler<ActionEvent>()  {

			@Override
			public void handle(ActionEvent event) {
				Database.deconnection(); 
				isConnected = false;
				alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("Vous êtes déconnecté !");
				alert.showAndWait();
				root.getChildren().clear();
				start();
			}
			
		});
		
		biblioBtn.setOnAction(new EventHandler<ActionEvent>()  {

			@Override
			public void handle(ActionEvent event) {
				displayBiblio(); 
			}
			
		});
	}
	
	public void displayBiblio() {
		root.getChildren().clear();
	}
	
	public void searchHeros() {
		root.getChildren().clear();
		Label label = new Label("Entrer le nom d'un héros : ");
		TextField textField = new TextField();
		textField.setPromptText("Héros...");
		// A modifier pour un comics mais même principe que perso
		/*
		validateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(textField.getText().isEmpty()) {
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Le champ est vide !");
					alert.showAndWait();
				}else{
					try {
						perso = Parse.infoPersonnage(textField.getText());
						System.out.println(perso.getName());
						String url = perso.getLien_image();
						Image img = new Image(url,250,250,false,true);
						ImageView imgPerso = new ImageView(img);
						Label labelName = new Label("Nom : ");
						Text infoName = new Text();
						infoName.setText(perso.getName());
						
						Label labelDescription = new Label("Description : ");
						Label infoDescription = new Label(perso.getDescription());
						infoDescription.setMaxWidth(350);
						infoDescription.setWrapText(true);
						
						
						HBox hb1 = new HBox();
						hb1.getChildren().addAll(labelName,infoName);
						hb1.setSpacing(10);
						hb1.setPadding(new Insets(0, 20, 10, 20));
						
						HBox hb2 = new HBox();
						hb2.getChildren().addAll(labelDescription,infoDescription);
						hb2.setSpacing(10);
						hb2.setPadding(new Insets(0, 20, 10, 20));
						
						VBox vb = new VBox();
						vb.setSpacing(10);
						vb.setPadding(new Insets(0, 20, 10, 20)); 
						vb.getChildren().addAll(hb1,hb2);
						
						hbHeros = new HBox();
						hbHeros.getChildren().addAll(imgPerso,vb);
						hbHeros.setSpacing(10);
						hbHeros.setPadding(new Insets(350, 20, 10, 20));
						//root.getChildren().add(hbHeros);
						
					} catch (NoSuchAlgorithmException | IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Un problème est survenu.");
						alert.showAndWait();
					}	
				}
			}
		}); */
		
		
		HBox hb = new HBox();
		hb.getChildren().addAll(label,textField,validateBtn,backBtn);
		hb.setSpacing(10);
		hb.setPadding(new Insets(300, 20, 10, 222));
		
		root.getChildren().add(hb);
		
		
	}
	
	public void searchComics() {
		root.getChildren().clear();
		Label label = new Label("Rechercher un comics : ");
		TextField textField = new TextField();
		textField.setPromptText("Comics...");

		validateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(textField.getText().isEmpty()) {
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Le champ est vide !");
					alert.showAndWait();
				}else{
					try {
						perso = Parse.infoPersonnage(textField.getText());
						System.out.println(perso.getName());
						String url = perso.getLien_image();
						Image img = new Image(url,250,250,false,true);
						ImageView imgPerso = new ImageView(img);
						Label labelName = new Label("Nom : ");
						Text infoName = new Text();
						infoName.setText(perso.getName());
						
						Label labelDescription = new Label("Description : ");
						Label infoDescription = new Label(perso.getDescription());
						infoDescription.setMaxWidth(350);
						infoDescription.setWrapText(true);
						
						
						HBox hb1 = new HBox();
						hb1.getChildren().addAll(labelName,infoName);
						hb1.setSpacing(10);
						hb1.setPadding(new Insets(0, 20, 10, 20));
						
						HBox hb2 = new HBox();
						hb2.getChildren().addAll(labelDescription,infoDescription);
						hb2.setSpacing(10);
						hb2.setPadding(new Insets(0, 20, 10, 20));
						
						VBox vb = new VBox();
						vb.setSpacing(10);
						vb.setPadding(new Insets(0, 20, 10, 20)); 
						vb.getChildren().addAll(hb1,hb2);
						
						hbHeros = new HBox();
						hbHeros.getChildren().addAll(imgPerso,vb);
						hbHeros.setSpacing(10);
						hbHeros.setPadding(new Insets(350, 20, 10, 20));
						//root.getChildren().add(hbHeros);
						
					} catch (NoSuchAlgorithmException | IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Un problème est survenu.");
						alert.showAndWait();
					}	
				}
			}
		});
		
		
		HBox hb = new HBox();
		hb.getChildren().addAll(label,textField,validateBtn,backBtn);
		hb.setSpacing(10);
		hb.setPadding(new Insets(300, 20, 10, 240));
		
		root.getChildren().addAll(hb);
	}
	
	public void connect() {
		root.getChildren().clear();
		Label label = new Label("Avez-vous déjà un compte ?");
		
		HBox hb = new HBox();
		hb.getChildren().addAll(yesBtn,noBtn,backBtn);
		hb.setSpacing(10);
		hb.setPadding(new Insets(0, 20, 10, 0));
		
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.setPadding(new Insets(300, 20, 10, 325));
		vb.getChildren().addAll(label,hb);
		
		root.getChildren().add(vb);
	
	}
	 
	public void connectAccount() {
		root.getChildren().clear();
		Label userName = new Label("Entrer votre nom d'utilisateur :");
		Label password = new Label("Entrer votre mot de passe :");
		TextField textUser = new TextField();
		textUser.setPromptText("Nom d'utilisateur..");
		PasswordField textPassword = new PasswordField();
		textPassword.setPromptText("Mot de passe...");
		
		HBox hb = new HBox();
		hb.getChildren().addAll(userName,textUser);
		hb.setSpacing(10);
		hb.setPadding(new Insets(0, 20, 10, -100));
		
		HBox hb2 = new HBox();
		hb2.getChildren().addAll(password,textPassword);
		hb2.setSpacing(10);
		hb2.setPadding(new Insets(0, 20, 10, -100));
		
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.setPadding(new Insets(300, 20, 10, 325));
		vb.getChildren().addAll(hb,hb2,validateBtn,backBtn);
		
		root.getChildren().add(vb);
		
		validateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(textUser.getText().isEmpty() || textPassword.getText().isEmpty()) {
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Un des champs est vide !");
					alert.showAndWait();
				}
				else {
					if(Database.connectDatabase(textUser.getText(), textPassword.getText())) {
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Bienvenue "+textUser.getText()+" !");
						isConnected = true;
						alert.showAndWait();
						root.getChildren().clear();
						start();
					} else {
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Un erreur est survenue !");
						alert.showAndWait();
					}
				}	
			}
		});
	}
	
	public void createAccount() {
		root.getChildren().clear();
		Label userName = new Label("Entrer un nom d'utilisateur :");
		Label password = new Label("Entrer un mot de passe :");
		TextField textUser = new TextField();
		textUser.setPromptText("Nom d'utilisateur..");
		PasswordField textPassword = new PasswordField();
		textPassword.setPromptText("Mot de passe...");
		
		HBox hb = new HBox();
		hb.getChildren().addAll(userName,textUser);
		hb.setSpacing(10);
		hb.setPadding(new Insets(0, 20, 10, -100));
		
		HBox hb2 = new HBox();
		hb2.getChildren().addAll(password,textPassword);
		hb2.setSpacing(10);
		hb2.setPadding(new Insets(0, 20, 10, -100));
		
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.setPadding(new Insets(300, 20, 10, 325));
		vb.getChildren().addAll(hb,hb2,validateBtn,backBtn);
		
		validateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(textUser.getText().isEmpty() || textPassword.getText().isEmpty()) {
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Un des champs est vide !");
					alert.showAndWait();
				}
				else {
					if(Database.createDatabase(textUser.getText(), textPassword.getText())) {
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Compte créé !");
						alert.showAndWait();
						isConnected = true;
						root.getChildren().clear();
						start();
					} else {
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Un erreur est survenue !");
						alert.showAndWait();
					}
				}	
			}
		});
		
		root.getChildren().add(vb);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		backBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				root.getChildren().clear();
				start();
			}
			
		});
		yesBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				connectAccount();
			}
		});
		noBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				createAccount();	
			}
		});
		start();
	}
	
}
