package Model;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Controler.Database;
import Controler.HttpConnect;

/** 
 * Classe qui récupère les données de l'API
 * @author Nico
 *
 */
public class Parse {

	private static String info;
	private static String privateKey ="2901430602e3e8e4a3e16d6d9813979391ea45c4";
	private static String publicKey = "11db561f5be05ff04926efa082f034ac"; 
	private static String reqNom = "https://gateway.marvel.com:443/v1/public/characters?name=";
	private static String reqlistComics = "https://gateway.marvel.com:443/v1/public/comics?characters=";
	private static String reqTitle = "https://gateway.marvel.com:443/v1/public/comics?titleStartsWith=";
	private static String reqTitleId = "https://gateway.marvel.com:443/v1/public/comics/";
	private static String limit = "&limit=10";
	private static String offset = "&offset=";
	private static String apikey = "&apikey=";
	private static String timestp = "&ts=";
	private static String hash = "&hash=";
	private static String donnees ="data";
	private static String tableau = "results";
	private static String identifiant = "id";
	private static String name = "name";
	private static String description = "description";
	private static String titre = "title";
	private static String thumbnail = "thumbnail";
	private static String path = "path";
	private static String extension = "extension";
	private static String creators = "creators";
	private static String characters = "characters";
	private static String books = "comics";
	private static String items = "items";
	private static String role = "role"; 
	private static String total = "total";
	private static String count = "count";
	private static String available = "available";
	private static String returned = "returned";
	private static String ts=Long.toString(System.currentTimeMillis()); //generation du timstamp:
	private static MessageDigest md5hash;
	private static Scanner sc1;
	
	/**
	 * Récupère les informations d'un personnage
	 * @param nom
	 * @return pers
	 * @throws IOException
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 */
	public static Personnage infoPersonnage(String nom) throws IOException, JSONException, NoSuchAlgorithmException {
		nom=nom.replace(" ", "%20");
		//generation du md5:
		md5hash = MessageDigest.getInstance("MD5");
		md5hash.update(StandardCharsets.UTF_8.encode(ts+privateKey+publicKey));
		String md5=String.format("%032x", new BigInteger(1, md5hash.digest()));
		
		//on envoie la requete http
		info = HttpConnect.readUrl(reqNom+nom+timestp+ts+apikey+publicKey+hash+md5);
		
		// la reponse de la requete est un JSON
		JSONObject obj = new JSONObject(info);
		JSONObject data = obj.getJSONObject(donnees);
		JSONArray results = data.getJSONArray(tableau);
		Personnage pers=new Personnage();
		pers.setId(results.getJSONObject(0).getInt(identifiant));
		pers.setName(results.getJSONObject(0).getString(name));
		pers.setDescription(results.getJSONObject(0).getString(description));
		
		// pour avoir le lien de l'image du personnage il faut combiner path et extension
		pers.setLien_image(results.getJSONObject(0).getJSONObject(thumbnail).getString(path)+"."+results.getJSONObject(0).getJSONObject(thumbnail).getString(extension));
		
		return pers;
	}
	
	/**
	 * Méthode pour avoir des informations sur un comic en fonction de son identifiant
	 * 
	 * @param id
	 * @return comics
	 * @throws IOException
	 * @throws JSONException
	 * @throws NoSuchAlgorithmException
	 */
	public static Comics infoComicsId(String id) throws  IOException, JSONException, NoSuchAlgorithmException
	{
		
		//generation du md5:
		md5hash = MessageDigest.getInstance("MD5");
		md5hash.update(StandardCharsets.UTF_8.encode(ts+privateKey+publicKey));
		String md5=String.format("%032x", new BigInteger(1, md5hash.digest()));
		
		//on envoie la requete http
		info = HttpConnect.readUrl(reqTitleId+Integer.parseInt(id)+"?"+timestp+ts+apikey+publicKey+hash+md5);
		
		JSONObject obj = new JSONObject(info);
		JSONObject data = obj.getJSONObject(donnees);
		JSONArray results = data.getJSONArray(tableau);
		
		// creation d'un objet Comics
		Comics comics=new Comics();		
		
		//Recuperation de l'identifiant, du titre et de la description.
		comics.setId(results.getJSONObject(0).getInt(identifiant));
		comics.setTitle(results.getJSONObject(0).getString(titre));
		
		
		// Recuperation de la description si elle existe
		if (results.getJSONObject(0).isNull(description)){
			comics.setDescription("Aucune description / No description available.");
		}else {
			comics.setDescription(results.getJSONObject(0).getString(description));
		}
		
		// Recuperation des createurs 
		int nbCreators=results.getJSONObject(0).getJSONObject(creators).getInt("available");
		if (nbCreators ==0) {
			comics.setCreators("Aucune information / No information.");
			comics.setPremierCreateur("Aucune information");
		}
		else{
			comics.setPremierCreateur(results.getJSONObject(0).getJSONObject(creators).getJSONArray(items).getJSONObject(0).getString(name));
			for (int j=0; j<nbCreators; j++)
			{
				comics.setCreators(results.getJSONObject(0).getJSONObject(creators).getJSONArray(items).getJSONObject(j).getString(role).toUpperCase()+" : "+results.getJSONObject(0).getJSONObject(creators).getJSONArray(items).getJSONObject(j).getString(name));
			}
		}
		
		// Recuperation des personnages
		int nbCharacters=results.getJSONObject(0).getJSONObject(characters).getInt("available");
		if (nbCharacters ==0) {
			comics.setCharacters("Aucune information / No information.");
		}
		else{
			for (int j=0; j<nbCharacters; j++)
			{			
				comics.setCharacters(results.getJSONObject(0).getJSONObject(characters).getJSONArray(items).getJSONObject(j).getString(name));
			}
		}
		
		// pour avoir le lien de l'image du personnage il faut combiner path et extension
		comics.setLien_image(results.getJSONObject(0).getJSONObject(thumbnail).getString(path)+"."+results.getJSONObject(0).getJSONObject(thumbnail).getString(extension));
		
		return comics;
	}
	
	/*public static void main(String[] args) throws NoSuchAlgorithmException, IOException, JSONException {
		Personnage perso = new Personnage();
		perso = infoPersonnage("thor");
		perso.afficher();
		Comics comics = new Comics();
		comics =Parse.infoComicsId("21171");
		comics.afficher(); 
	
		Database.database();
	} */
}
