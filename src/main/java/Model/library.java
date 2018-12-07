package Model;

import javafx.beans.property.SimpleStringProperty;

public class library {
	
	 private final SimpleStringProperty  id;
	 private final SimpleStringProperty  titre;
	 private final SimpleStringProperty  description;
	 private final SimpleStringProperty  auteur;
	 
	 
	 
	 public library(String id,String titre, String description, String auteur){
		 this.id = new SimpleStringProperty(id);
		 this.titre = new SimpleStringProperty(titre);
		 this.description = new SimpleStringProperty(description);
		 this.auteur = new SimpleStringProperty(auteur);
	 }
	
	
	


	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id.set(id);
	}
	public String getTitre() {
		return titre.get();
	}
	public void setTitre(String titre) {
		this.titre.set(titre);
	}
	public String getDescription() {
		return description.get();
	}
	public void String(String description) {
		this.description.set(description);
	}
	public String getAuteur() {
		return auteur.get();
	}
	public void setAuteur(String auteur) {
		this.auteur.set(auteur);
	}
	
	

	
	
	

}
