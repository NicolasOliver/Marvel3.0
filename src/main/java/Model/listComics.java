package Model;

import java.util.ArrayList;

/**
 * Classe qui permet d'afficher une liste de Comics
 * @author Nico
 *
 */
public class listComics {

	private int total;
	private ArrayList<Integer> comicsID = new ArrayList<Integer>();
	private ArrayList<String> comicsName = new ArrayList<String>();
	private String lien_image;

	public void setTotal(int int1) {
		this.total = int1;
	}

	public void setComics(int id) {
		this.comicsID.add(id);
	}
	
	public int getComicsId(int id) {
		return comicsID.get(id);
	}

	public void afficher() {
		System.out.println("Sur quel comics voulez-vous des informations : ");
		for(int i=0; i<comicsID.size(); i++)
		{
			String title = String.valueOf(comicsName.get(i));
			System.out.println(i+". "+title);
		}
	}

	public void setComicsName(String setComics) {
		this.comicsName.add(setComics);
	}

	public ArrayList<Integer> getComicsID() {
		return comicsID;
	}

	public void setComicsID(ArrayList<Integer> comicsID) {
		this.comicsID = comicsID;
	}

	public ArrayList<String> getComicsName() {
		return comicsName;
	}

	public void setComicsName(ArrayList<String> comicsName) {
		this.comicsName = comicsName;
	}

	public String getLien_image() {
		return lien_image;
	}

	public void setLien_image(String lien_image) {
		this.lien_image = lien_image;
	}

	public int getTotal() {
		return total;
	}
	
	
}
