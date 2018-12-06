package View;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


import org.json.JSONException;

import Controler.Database;
import Model.Comics;
import Model.Parse;
import Model.Personnage;
import Model.listComics;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import javafx.scene.text.Font;
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
	Button detail = new Button("Détails");
	Button biblioBtn = new Button("Bibliothèque");
	Button ajoutBibli = new Button("Ajouter");
	Alert alert = new Alert(AlertType.INFORMATION);
	Boolean isConnected = false;
	Personnage perso = null;
	listComics comics = null;
	HBox hbHeros;
	Comics comic = null;
	TextField textFieldHeros = new TextField();
	
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
		Label label = new Label("Votre biblihotèque :");
		label.setStyle("-fx-font-weight: bold");
		label.setFont(new Font("Arial", 20));
		TableView table = new TableView();
		TableColumn NameCol = new TableColumn("Name");
	    TableColumn descriptionCol = new TableColumn("Description");
	    TableColumn AutorCol = new TableColumn("Auteur");
	    TableColumn imgCol = new TableColumn("Image");
	    table.getColumns().addAll(NameCol, descriptionCol,AutorCol, imgCol);

	    final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(300, 0, 0, 0));
        vbox.getChildren().addAll(label, table);
	    
	    root.getChildren().addAll(vbox, backBtn);
	}
	
	public HBox newHb() {
		Label label = new Label("Entrer le nom d'un héros : ");
		TextField textField = new TextField();
		textField.setPromptText("Héros...");
		HBox hb = new HBox();
		hb.getChildren().addAll(label,textField,validateBtn,backBtn);
		hb.setSpacing(10);
		hb.setPadding(new Insets(300, 20, 10, 222));
		return hb;
	}
	
	public void searchHeros() {
		root.getChildren().clear();
		Label label = new Label("Entrer le nom d'un héros : ");
		textFieldHeros.setPromptText("Héros...");
		// A modifier pour un comics mais même principe que perso
		
		HBox hb = new HBox();
		hb.getChildren().addAll(label,textFieldHeros,validateBtn,backBtn);
		hb.setSpacing(10);
		hb.setPadding(new Insets(300, 20, 10, 222));
		root.getChildren().add(hb);
		
		validateBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(textFieldHeros.getText().isEmpty()) {
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Le champ est vide !");
					alert.showAndWait();
				}else{
					try {
						perso = new Personnage();
						root.getChildren().clear();
						perso = Parse.infoPersonnage(textFieldHeros.getText());
						System.out.println(textFieldHeros.getText());
						textFieldHeros.clear();
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
						getRoot().getChildren().add(hbHeros);
						root.getChildren().add(hb);
						
					} catch (NoSuchAlgorithmException | IOException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						alert.setTitle("Information");
						alert.setHeaderText(null);
						alert.setContentText("Un problème est survenu.");
						alert.showAndWait();
						root.getChildren().clear();
						searchHeros();
					}	
				}
			}
		}); 
	}
	
	public HBox newHbCo() {
		Label label = new Label("Rechercher un comics : ");
		TextField textField = new TextField();
		textField.setPromptText("Comics...");
		HBox hb = new HBox();
		hb.getChildren().addAll(label,textField,validateBtn,backBtn);
		hb.setSpacing(10);
		hb.setPadding(new Insets(300, 20, 10, 240));
		return hb;
	}
	
	public void searchComics() {
		root.getChildren().clear();
		Label label = new Label("Rechercher un comics : ");
		TextField textField = new TextField();
		textField.setPromptText("Comics...");
		ListView<String> listView = new ListView<String>();
		
		HBox hb = new HBox();
		hb.getChildren().addAll(label,textField,validateBtn,backBtn);
		hb.setSpacing(10);
		hb.setPadding(new Insets(300, 20, 10, 240));
		root.getChildren().addAll(hb);

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
						comics = Parse.listeComics(textField.getText(), 0);
						if(comics.getComicsID().isEmpty()) {
							alert.setTitle("Information");
							alert.setHeaderText(null);
							alert.setContentText("Aucun comics trouvé !");
							alert.showAndWait();
						} else {
							listView.getItems().clear();
						 for(int i = 0;i<comics.getComicsName().size();i++) {
							 listView.getItems().add(comics.getComicsID().get(i)+" - "+comics.getComicsName().get(i));
						 }
						 	root.getChildren().clear();
						 	root.setBackground(new Background(back));
						 
					        VBox hb1 = new VBox(listView);
					        hb1.setSpacing(10);
					        hb1.setPadding(new Insets(0, 20, 10, 20));
					        
					        HBox hb = new HBox();
							hb.getChildren().addAll(label,textField,validateBtn,backBtn , detail);
							hb.setSpacing(10);
							hb.setPadding(new Insets(0, 20, 10, 240));
						
							VBox vbox = new VBox();
							vbox.getChildren().addAll(hb, hb1);
							vbox.setSpacing(10);
							vbox.setPadding(new Insets(300, 20, 10, 20));

					        root.getChildren().addAll(vbox);
						}
						
						
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
		
		detail.setOnAction(event -> {
            ObservableList<String> selectedIndices = listView.getSelectionModel().getSelectedItems();
            root.getChildren().clear();
		 	root.setBackground(new Background(back));
		 	if(selectedIndices.isEmpty()) {
		 		alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("Il faut sélectionner un item !");
				alert.showAndWait();
				searchComics();
		 	} else {
		 		try {
			 		
			 		comic = Parse.infoComicsId(selectedIndices.get(0).substring(0, 5));
			 		comic.afficher();
			 		
			 		String url = comic.getLien_image();
					Image img = new Image(url,250,250,false,true);
					ImageView imgPerso = new ImageView(img);
			 		
			 		Label labelTitre = new Label("Titre : ");
					Label titre = new Label(comic.getTitle());
					titre.setMaxWidth(350);
					titre.setWrapText(true);
					
					Label labelAuteur = new Label("Auteur : ");
					Label Auteur = new Label(comic.getPremierCreateur());
					Auteur.setMaxWidth(350);
					Auteur.setWrapText(true);
					
					Label labelDesc = new Label("Description : ");
					Label Desc = new Label(comic.getDescription());
					Desc.setMaxWidth(350);
					Desc.setWrapText(true);
					
					HBox hb0 = new HBox();
					if(isConnected) { 
						hb0.getChildren().addAll(label,textField,validateBtn,backBtn , ajoutBibli);
					} else {
						hb0.getChildren().addAll(label,textField,validateBtn,backBtn);
					}
					
					hb0.setSpacing(10);
					hb0.setPadding(new Insets(0, 0, 10, 20));
									
					HBox hbBt = new HBox();
					hbBt.getChildren().add(ajoutBibli);
					hbBt.setSpacing(10);
					hbBt.setPadding(new Insets(0, 20, 10, 20));
					
					HBox hb1 = new HBox();
					hb1.getChildren().addAll(labelTitre,titre);
					hb1.setSpacing(10);
					hb1.setPadding(new Insets(0, 20, 10, 20));
					
					HBox hb2 = new HBox();
					hb2.getChildren().addAll(labelAuteur,Auteur);
					hb2.setSpacing(10);
					hb2.setPadding(new Insets(0, 20, 10, 20));
					
					HBox hb3 = new HBox();
					hb3.getChildren().addAll(labelDesc,Desc);
					hb3.setSpacing(10);
					hb3.setPadding(new Insets(0, 20, 10, 20));
					
					VBox vb = new VBox();
					vb.setSpacing(10);
					vb.setPadding(new Insets(0, 20, 10, 20)); 
					if(isConnected) {
						vb.getChildren().addAll(hb0, hb1,hb2, hb3, hbBt);
					} else {
						vb.getChildren().addAll(hb0, hb1,hb2, hb3);
					}
					
					hbHeros = new HBox();
					hbHeros.getChildren().addAll(imgPerso,vb);
					hbHeros.setSpacing(10);
					hbHeros.setPadding(new Insets(300, 20, 10, 20));
					getRoot().getChildren().add(hbHeros);
					
					
					
				} catch (NoSuchAlgorithmException | IOException | JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("Un problème est survenu.");
					alert.showAndWait();
					root.getChildren().clear();
					searchComics();
				}	
			 	
			 	
	                System.out.println(selectedIndices);
		 	}
		 	
		 	
            
        });
		
		this.ajoutBibli.setOnAction(event -> {
	 		 if(Database.insert(comic.getId(), comic.getTitle(), comic.getDescription(), comic.getPremierCreateur())) {
	 			alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("Comics correctement ajouté à la bibliothèque !");
				alert.showAndWait();
	 		 } else {
	 			alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("Un problème est survenu !");
				alert.showAndWait();
	 		 }
	 		comic = new Comics();
	 	});
		
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

	public static StackPane getRoot() {
		return root;
	}

	public static void setRoot(StackPane root) {
		Fenetre.root = root;
	}
	
}
