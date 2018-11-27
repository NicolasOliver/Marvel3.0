package Controler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe qui g�re tout ce qui est relatif � la base de donn�es
 * @author Nico
 *
 */
public class Database {
	private static String framework = "embedded";
	private static String protocol = "jdbc:derby:";
	private static String str;
	private static Scanner sc1;
	private static String userName;
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
	 * Classe qui permet de cr�er ou de se connecter � sa base donn�es (pour les tests)
	 */
	public static void database() {
		System.out.println("�tes vous d�j� inscrit ? (o/n)");
		sc1 = new Scanner(System.in);
		do {
			str = sc1.nextLine();
			switch (str) {
			case "o":
				System.out.println("Indiquez le nom de votre biblioth�que :");
				sc4 = new Scanner(System.in);
				dbName = sc4.nextLine();
				System.out.println("Veuillez renseigner votre nom d'utilisateur :");
				sc2 = new Scanner(System.in);
				userName = sc2.nextLine();
				System.out.println("Maintenant votre mot de passe :");
				sc3 = new Scanner(System.in);
				pass = sc3.nextLine();
				connectDatabase(dbName, userName, pass);
				break;
			case "n":
				System.out.println("Quel nom souhaitez vous donner � votre biblioth�que ?");
				sc4 = new Scanner(System.in);
				dbName = sc4.nextLine();
				System.out.println("Veuillez renseigner un nom d'utilisateur :");
				sc2 = new Scanner(System.in);
				userName = sc2.nextLine();
				System.out.println("Maintenant un mot de passe :");
				sc3 = new Scanner(System.in);
				pass = sc3.nextLine();
				createDatabase(dbName, userName, pass);
				break;
			default:
				System.out.println("�tes vous d�j� inscrit ? (o/n)");
				str = ""; // si le str est incorrect, on le force � vide
			}

		} while (str.isEmpty()); // tant que coup est vide on bouble (donc redemande)
	}

	/**
	 * Classe pour cr�er un compte dans la base de donn�es
	 * @param dbName
	 * @param userName
	 * @param pass
	 * @return
	 */
	public static Boolean createDatabase(String dbName, String userName, String pass) {
		conn = null;
		rs = null;

		try {
			conn = DriverManager
					.getConnection(protocol + dbName + ";create=true ;user=" + userName + " ;password=" + pass);
			System.out.println(dbName + " cr��e avec succ�s !");
			System.out.println("Notez bien vos identifiants : ");
			System.out.println("Nom d'utililsateur : " + userName);
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
	 * Pour se connecter � un compte
	 * @param dbName
	 * @param userName
	 * @param pass
	 * @return
	 */
	public static Boolean connectDatabase(String dbName, String userName, String pass) {
		conn = null;
		rs = null;

		try {
			conn = DriverManager.getConnection(protocol + dbName + ";user=" + userName + " ;password=" + pass);
			System.out.println("Connexion � " + dbName + " r�ussie");

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
	 * Classe pour se d�connecter de la base de donn�es
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
	 * Pour pouvoir ajouter des comics � la biblioth�que
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
			// Commande pour ins�rer des valeurs
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
}