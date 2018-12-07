package Controler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe qui gère tout ce qui est relatif à la base de données
 * @author Nico, Yves
 *
 */
public class Database {
	private static String protocol = "jdbc:derby:";
	private static String str;
	private static Scanner sc1;
	private static String pass;
	private static Scanner sc3;
	private static String dbName;
	private static Scanner sc4;
	private static Connection conn;
	private static ArrayList<Statement> statements = new ArrayList<Statement>();
	private static PreparedStatement psInsert;
	private static PreparedStatement psUpdate;
	private static Statement s;
	private static ResultSet rs;
	
	/**
	 * Classe qui permet de créer ou de se connecter à sa base données (pour les tests)
	 */
	public static void database() {
		System.out.println("Êtes vous déjà inscrit ? (o/n)");
		sc1 = new Scanner(System.in);
		do {
			str = sc1.nextLine();
			switch (str) {
			case "o":
				System.out.println("Indiquez le nom de votre bibliothèque :");
				sc4 = new Scanner(System.in);
				dbName = sc4.nextLine();
				System.out.println("Maintenant votre mot de passe :");
				sc3 = new Scanner(System.in);
				pass = sc3.nextLine();
				connectDatabase(dbName, pass);
				break;
			case "n":
				System.out.println("Quel nom souhaitez vous donner à votre bibliothèque ?");
				sc4 = new Scanner(System.in);
				dbName = sc4.nextLine();
				System.out.println("Maintenant un mot de passe :");
				sc3 = new Scanner(System.in);
				pass = sc3.nextLine();
				createDatabase(dbName, pass);
				break;
			default:
				System.out.println("Êtes vous déjà inscrit ? (o/n)");
				str = ""; // si le str est incorrect, on le force à vide
			}

		} while (str.isEmpty()); // tant que coup est vide on bouble (donc redemande)
	}

	/**
	 * Classe pour créer un compte dans la base de données
	 * @param dbName
	 * @param pass
	 * @return
	 */
	public static Boolean createDatabase(String dbName, String pass) {
		conn = null;
		rs = null;
		String userName = dbName;

		try {
			conn = DriverManager
					.getConnection(protocol + dbName + ";create=true ;user=" + userName + " ;password=" + pass);
			System.out.println(protocol + dbName + ";create=true ;user=" + userName + " ;password=" + pass);
			System.out.println(dbName + " créée avec succès !");
			System.out.println("Notez bien vos identifiants : "+ dbName);
			System.out.println("Mot de passe : " + pass);

			// We want to control transactions manually. Autocommit is on by
			// default in JDBC.
			conn.setAutoCommit(true);
			/*
			 * Creating a statement object that we can use for running various SQL
			 * statements commands against the database.
			 */
			s = conn.createStatement();
			statements.add(s);

			// We create a table...
			s.execute("create table library(id int," + " titre varchar(1000),"
					+ " description varchar(10000)," + "auteur varchar(1000))");

			/*
			 * We commit the transaction. Any changes will be persisted to the database now.
			 */
			conn.commit();
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return false;
	}

	/**
	 * Pour se connecter à un compte
	 * @param dbName
	 * @param pass
	 * @return
	 */
	public static Boolean connectDatabase(String dbName, String pass) {
		conn = null;
		rs = null;
		String userName = dbName;
		
		try {
			conn = DriverManager.getConnection(protocol + dbName + ";user=" + userName + " ;password=" + pass);
			System.out.println("Connexion à " + dbName + " réussie");

			// We want to control transactions manually. Autocommit is on by
			// default in JDBC.
			conn.setAutoCommit(false);
			return true;
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		return false;
	}
	
	/**
	 * Classe pour se déconnecter de la base de données
	 */
	public static void deconnection() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}

		// Statements and PreparedStatements
		int i = 0;
		while (!statements.isEmpty()) {
			// PreparedStatement extend Statement
			Statement st = (Statement) statements.remove(i);
			try {
				if (st != null) {
					st.close();
					st = null;
				}
			} catch (SQLException sqle) {
				System.out.println(sqle.getMessage());
			}
		}

		// Connection
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
	}
	
	/**
	 * Permer de selectionner par titre
	 * @param title
	 * @return
	 */
	public static Boolean selectByTitle(String title) {
		try {
			/*
			 * Creating a statement object that we can use for running various SQL
			 * statements commands against the database.
			 */
			// Commande de SELECT qui affiche une ligne
			s = conn.createStatement();
			statements.add(s);
			rs = s.executeQuery("SELECT * FROM library WHERE titre = "+title);
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			}
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
			// e.printStackTrace();

		}

	}
	
	/**
	 * Permet de sélectionner une ligne par id
	 * @param id
	 * @return
	 */
	public static Boolean selectLigne(int id) {
		try {

			/*
			 * Creating a statement object that we can use for running various SQL
			 * statements commands against the database.
			 */
			// Commande de SELECT qui affiche une ligne
			s = conn.createStatement();
			statements.add(s);
			rs = s.executeQuery("SELECT * FROM library where id ="+id);
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			}
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Permet de sélectionner par auteur
	 * @param author
	 * @return
	 */
	public static Boolean selectByAuthor(String author) {
		try {
			/*
			 * Creating a statement object that we can use for running various SQL
			 * statements commands against the database.
			 */
			// Commande de SELECT qui affiche une ligne
			s = conn.createStatement();
			statements.add(s);
			rs = s.executeQuery("SELECT * FROM library where auteur = "+author);
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
			}
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Permet de supprimer une ligne par identifiant
	 * @param id
	 * @return
	 */
	public static Boolean deleteLigne(int id) {
		try {
			psUpdate = conn.prepareStatement("delete from library where id=?");
			psUpdate.setInt(1, id);
			psUpdate.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * Pour insérer un comics dans la bilbiothèque
	 * @param id
	 * @param name
	 * @param description
	 * @param author
	 * @return
	 */
	public static Boolean insert(int id, String name, String description, String author) {
		try {
			// Commande pour insérer des valeurs
			psInsert = conn.prepareStatement("insert into library (id, titre,  description, auteur) values (?, ?, ?, ?)");
			statements.add(psInsert);

			psInsert.setInt(1, id);
			psInsert.setString(2, name);
			psInsert.setString(3, description);
			psInsert.setString(4, author);	

			psInsert.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Pour récupérer toutes les données de la librairie
	 * @return
	 * @throws SQLException
	 */
	public static List<String[]> getlibrary() throws SQLException {
		List<String[]> data = new ArrayList<String[]>();
		s = conn.createStatement();
		statements.add(s);

		rs = s.executeQuery("SELECT * FROM library");
		while (rs.next()) {
			String[] info = { Integer.toString(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4) };
			data.add(info);

		}
		conn.commit();

		return data;
	}
}
