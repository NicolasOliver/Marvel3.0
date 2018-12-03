package Model;

import java.util.ArrayList;

public class listComics {

	private int total;
	private ArrayList<Integer> comicsID = new ArrayList<Integer>();
	private ArrayList<String> comicsName = new ArrayList<String>();

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
}
