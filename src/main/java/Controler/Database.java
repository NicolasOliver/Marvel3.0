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
 * @author Nico
 *
 */
public class Database {
	private static String framework = "embedded";
	private static String protocol = "jdbc:derby:";
	private static String str;
	private static Scanner sc1;
	private static Scanner sc2;
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
			System.out.println(dbName + " créée avec succès !");
			System.out.println("Notez bien vos identifiants : "+ dbName);
			System.out.println("Mot de passe : " + pass);

			// We want to control transactions manually. Autocommit is on by
			// default in JDBC.
			conn.setAutoCommit(false);
			/*
			 * Creating a statement object that we can use for running various SQL
			 * statements commands against the database.
			 */
			s = conn.createStatement();
			statements.add(s);

			// We create a table...
			s.execute("create table library(id int primary key not null," + " titre varchar(100),"
					+ " auteur varchar(100)," + "etat varchar(100)," + " bookmark int," + " note int,"
					+ " commentaire varchar(500))");

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
	 * @param userName
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
			rs = s.executeQuery("SELECT COUNT(*) FROM library where title = ?");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4)
						+ " " + rs.getString(5) + " " + rs.getInt(6) + " " + rs.getInt(7) + " " + rs.getString(8));
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
	 * @param dbName
	 * @param userName
	 * @param pass
	 * @return
	 */
	public static Boolean selectLigne(int id, String dbName, String userName, String pass) {
		try {

			/*
			 * Creating a statement object that we can use for running various SQL
			 * statements commands against the database.
			 */
			// Commande de SELECT qui affiche une ligne
			s = conn.createStatement();
			statements.add(s);
			rs = s.executeQuery("SELECT * FROM library where id = ?");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4)
						+ " " + rs.getString(5) + " " + rs.getInt(6) + " " + rs.getInt(7) + " " + rs.getString(8));
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
	 * @param userNames
	 * @param pass
	 * @return
	 */
	public static Boolean selectByAuthor(String author, String userNames, String pass) {
		try {
			/*
			 * Creating a statement object that we can use for running various SQL
			 * statements commands against the database.
			 */
			// Commande de SELECT qui affiche une ligne
			s = conn.createStatement();
			statements.add(s);
			rs = s.executeQuery("SELECT * FROM library where author = ?");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4)
						+ " " + rs.getString(5) + " " + rs.getInt(6) + " " + rs.getInt(7) + " " + rs.getString(8));
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
	 * @param dbName
	 * @param userName
	 * @param pass
	 * @return
	 */
	public static Boolean deleteLigne(int id, String dbName, String userName, String pass) {
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
	 * Pour pouvoir ajouter des comics à la bibliothèque
	 * @param dbName
	 * @param userName
	 * @param pass
	 * @param id
	 * @param title
	 * @param author
	 * @param etat
	 * @param bookmark
	 * @param note
	 * @param com
	 * @return
	 */
	public static Boolean insert(String dbName, String userName, String pass, int id, String title, String author,
			String etat, int bookmark, int note, String com) {
		try {
			// Commande pour insérer des valeurs
			psInsert = conn.prepareStatement("insert into library values (?, ?, ?, ?, ?, ?, ?)");
			statements.add(psInsert);

			psInsert.setInt(1, id);
			psInsert.setString(2, title);
			psInsert.setString(3, author);
			psInsert.setString(4, etat);
			psInsert.setInt(5, bookmark);
			psInsert.setInt(6, note);
			psInsert.setString(7, com);

			psInsert.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static List<String[]> getlibrary() throws SQLException {
		List<String[]> data = new ArrayList<String[]>();
		s = conn.createStatement();
		statements.add(s);

		rs = s.executeQuery("SELECT * FROM library");
		while (rs.next()) {
			String[] info = { Integer.toString(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4),
					Integer.toString(rs.getInt(5)), Integer.toString(rs.getInt(6)), rs.getString(7) };
			data.add(info);

		}
		conn.commit();

		return data;
	}
}
