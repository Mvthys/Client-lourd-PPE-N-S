package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bdd {
	
	/* Bdd est une classe pour la connexion au serveur sql avec le pilote jbdc*/
	
	private String serveur, bdd, user, mdp;
	
	private Connection maConnexion;

	public Bdd(String serveur, String bdd, String user, String mdp) {
		super();
		this.serveur = serveur;
		this.bdd = bdd;
		this.user = user;
		this.mdp = mdp;
	}
	
public void chargerPilote() {
	
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
	}catch(ClassNotFoundException e){
		System.out.println("Abscence du pilote JDBC");
	}
		
}
	
public void seConnecter() {
	String url = "jdbc:mysql://" + this.serveur + "/" + this.bdd;
	this.chargerPilote();
		
	try {
		this.maConnexion = DriverManager.getConnection(url,user,mdp);
	}catch(SQLException e) {
		System.out.println("impossible de se connecter à : " + url);
	}
}

public void seDeconnecter() {
	try {
		if(this.maConnexion != null) {
			this.maConnexion.close();
		}
	}catch(SQLException e) {
		System.out.println("impossible de fermer la connexion");
}

	}
public Connection getMaConnexion() {
	return this.maConnexion;
}
	
}
