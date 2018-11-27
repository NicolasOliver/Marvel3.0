import static org.junit.Assert.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.Test;

import Model.Comics;
import Model.Parse;

public class testComics {

	@Test
	public void test() {
		int idExpected= 21171;
		String titleExpected="Amazing Spider-Man (1999) #558 (Turner Variant)";
		String descriptionExpected="The frighteningly funky and fearsome Freak returns, more powerful than ever! And this action-packed issue features the \r<br>gorgeous art of Barry Kitson - fresh from Marvel's THE ORDER!  Plus: Menace! Curt Connors!  Aunt May!  Lots of innocent bystanders!  Gale wrote it, Wacker edited it, and Marvel actually thought it was a good idea to publish it!\r<br>Rated A ...$2.99 \r<br>";
		String lien_imageExpected="http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg";
		ArrayList<String> creatorsExpected = new ArrayList<String>();
		creatorsExpected.add("WRITER : Bob Gale");
		creatorsExpected.add("ARTIST : Barry Kitson");
		creatorsExpected.add("PENCILLER (COVER) : Michael Turner");
		ArrayList<String> charactersExpected = new ArrayList<String>();
		charactersExpected.add("Spider-Man");
		
		Comics comic=new Comics();
		 try {
			comic=Parse.infoComicsId("21171");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 int idObtenu=comic.getId();
		 String titleObtenu=comic.getTitle();
		 String descriptionObtenu=comic.getDescription();
		 String lien_imageObtenu=comic.getLien_image();
		 ArrayList<String> CreateursObtenu=comic.getCreators2();
		 ArrayList<String> CharacterObtenu=comic.getCharacters2();
		 
		 assertEquals(idExpected,idObtenu);
		 assertEquals(lien_imageObtenu,lien_imageExpected);
		 assertEquals(titleExpected,titleObtenu);
		 assertEquals(descriptionExpected,descriptionObtenu);
		 assertEquals(creatorsExpected,CreateursObtenu);
		 assertEquals(charactersExpected,CharacterObtenu);
	}

}
