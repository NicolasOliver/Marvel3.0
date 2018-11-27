package Model;

import java.util.ArrayList;

/**
 * Classe qui représente un comics Marvel
 * @author Nico
 *
 */
public class Comics {
	private int id;
	private String title;
	private String description;
	private int total;
	private String lien_image;
	private String premierCreateur;
	private ArrayList<String> comics = new ArrayList<String>();
	private ArrayList<String> creators = new ArrayList<String>();
	private ArrayList<String> characters = new ArrayList<String>();
	
	
	/**
	 * Méthode qui permet de parametrer un objet Comics
	 * @param id
	 * @param title
	 * @param description
	 */
	public Comics(int id, String title, String description) {
		super();
		this.setId(id);
		this.setTitle(title);
		this.setDescription(description);
	}
	
	public Comics() {
		
	}

	public String getLien_image() {
		return lien_image;
	}
	public void setLien_image(String lien_image) {
		this.lien_image = lien_image;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setComics(String comics) {
		this.comics.add(comics);
	}
	
	public void getComics() {
		System.out.println("Pour cette recherche il y a "+total+" comics disponibles.");
		System.out.println("Sur quel comics voulez-vous des informations : ");
		for(int i=0; i<comics.size(); i++)
		{
			String title = (String)comics.get(i);
			System.out.println(i+1+". "+title);
		}
	}
	
	public ArrayList<String> getComics2() {
		return comics;
	}
	
	public void setCreators(String creators) {
		this.creators.add(creators);
	}
	
	public void getCreators() {
		for (int i=0; i<creators.size(); i++)
		{
			String creator = (String)creators.get(i);
			System.out.println(creator);
		}
	}
	
	public ArrayList<String> getCreators2() {
		return creators;
	}

	//cette methode pour affiche les titres des comics
	public void afficherComics() {
		this.getComics();
		
	}
	
	
	public void afficher() {
		System.out.println("Titre : " + this.getTitle());
		System.out.println("ID : " + this.getId());
		System.out.println("Description : " + this.getDescription());
		System.out.println("Liste des personnages dans ce comic : ");
		this.getCharacters();
		System.out.println("Liste des créateurs du comics : ");
		this.getCreators();
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public ArrayList<String> getCharacters2() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters.add(characters);
	}
	
	public void getCharacters() {
		for (int i=0; i<characters.size(); i++)
		{
			String character = (String)characters.get(i);
			System.out.println(character);
		}
		
	}

	public String getPremierCreateur() {
		return premierCreateur;
	}

	public void setPremierCreateur(String premierCreateur) {
		this.premierCreateur = premierCreateur;
	}
}
