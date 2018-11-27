import static org.junit.Assert.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.json.JSONException;
import org.junit.Test;

import Model.Parse;
import Model.Personnage;

public class testPersonnage {

	@Test
	public void test() throws NoSuchAlgorithmException, IOException, JSONException {
		String nameExpected1="Thor";
		int idExpected1=1009664;
		String descExpected1="As the Norse God of thunder and lightning, Thor wields one of the greatest weapons ever made, the enchanted hammer Mjolnir. While others have described Thor as an over-muscled, oafish imbecile, he's quite smart and compassionate.  He's self-assured, and he would never, ever stop fighting for a worthwhile cause.";
		Personnage pers1 = new Personnage();
		pers1=Parse.infoPersonnage("thor");
		String nameObtenu1=pers1.getName();
		int idObtenu1=pers1.getId();
		String descObtenu1=pers1.getDescription();
		
		assertEquals(idExpected1,idObtenu1);
		assertEquals(nameExpected1,nameObtenu1);
		assertEquals(descExpected1,descObtenu1);
	}

}
