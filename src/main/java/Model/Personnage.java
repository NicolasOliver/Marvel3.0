package Model;

import java.util.ArrayList;

/**
 * Classe qui représente un personnage Marvel de l'API
 * @author Nico
 *
 */

public class Personnage {
	private int id;
	private String name;
	private String description;
	private String lien_image;
	private ArrayList<String> comics= new ArrayList<String>() ;
	public Personnage(int id, String name, String description, String lien_image) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.lien_image = lien_image;
		
	}
	public Personnage() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLien_image() {
		return lien_image;
	}
	public void setLien_image(String lien_image) {
		this.lien_image = lien_image;
	}
	public void setComics(String comics) {
		this.comics.add(comics);
	}
	public void getComics() {
		System.out.println(" Voici les titres des comics ou le personnage apparait: ");
		for(int i=0; i<comics.size(); i++)
		{
			String title = (String)comics.get(i);
			System.out.println(title);
		}
	}
	
	public ArrayList<String> getComics2() {
		return comics;
	}
	
	public void afficher() {
		System.out.println("Nom du personnage : " + this.getName());
		System.out.println("Id : " + this.getId());
		System.out.println(" Description: " + this.getDescription());
		System.out.println(" Lien de l'image de profil: " + this.getLien_image());
	} 
	public void afficheComics() {
		this.getComics();
	}

}
