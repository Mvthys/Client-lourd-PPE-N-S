package modele;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.management.relation.Role;

import controleur.Admin;
import controleur.Client;
import controleur.Habitation;
import controleur.Proprietaire;
import controleur.Reservation;
import controleur.Utilisateur;

public class Modele {
	
	private static Bdd uneBdd = new Bdd("localhost","neigeetsoleil","root","");
	
	
	
/*** Admin ***/
public static Utilisateur selectWhereUtilisateur(String email, String mdp) {
	Utilisateur unUtilisateur = null;
	String requete = "select * from utilisateur where email ='"+ email +"' and mdp ='"+ mdp +"' and role = 'admin';";
		
	try {
		uneBdd.seConnecter();
		//comme le prepare en php
		Statement unStat = uneBdd.getMaConnexion().createStatement();
		//comme execute en phph
		ResultSet unResultat = unStat.executeQuery(requete);
		//comme le fetch
		if(unResultat.next()) {
			unUtilisateur = new Utilisateur(
									unResultat.getInt("Id_user"),
									unResultat.getString("nom"),
									unResultat.getString("prenom"),
									unResultat.getString("email"),
									unResultat.getString("mdp"),
									unResultat.getString("tel"),
									unResultat.getString("role")
								); 
			}
			uneBdd.seDeconnecter();
		}catch(SQLException e) {
			System.out.println("erreur requete : " + requete);
		}
		return unUtilisateur;
	}
	
	
	/*** Admin ***/
	public static Utilisateur selectWhereAdmin(String email, String mdp) {
		Utilisateur unAdmin = null;
		String requete = "select * from utilisateur where email ='"+ email +"' and mdp ='"+ mdp +"' and role = 'admin';";
		
		try {
			uneBdd.seConnecter();
			//comme le prepare en php
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			//comme execute en phph
			ResultSet unResultat = unStat.executeQuery(requete);
			//comme le fetch
			if(unResultat.next()) {
				unAdmin = new Utilisateur(
									unResultat.getInt("Id_user"),
									unResultat.getString("nom"),
									unResultat.getString("prenom"),
									unResultat.getString("email"),
									unResultat.getString("mdp"),
									unResultat.getString("tel"),
									unResultat.getString("role")
									); 
			}
			uneBdd.seDeconnecter();
		}catch(SQLException e) {
			System.out.println("erreur requete : " + requete);
		}
		return unAdmin;
	}
	
	
	
	
	
	
	
	
	
	/*** Clients ***/
	
	public static void insertClient(Client unClient) {
		String requete = "insert into client values (null,'"+unClient.getNom()+"','"+unClient.getPrenom()+"','"
						  +unClient.getEmail()+"','"+unClient.getMdp()+"','"+unClient.getAdresse()+"','"
						  +unClient.getCp()+"','"+unClient.getVille()+"','"+unClient.getTel()+"','"
						  +unClient.getRib()+"');";
		
		executerRequete(requete);
	}
	public static void updateClient(Client unClient) {
		String requete = "update client set nom_c ='"+unClient.getNom()+"',prenom_c ='"+unClient.getPrenom()+"',email_c ='"
						  +unClient.getEmail()+"', mdp_c ='"+unClient.getMdp()+"', adr_c ='"+unClient.getAdresse()+"',cp_c ='"
						  +unClient.getCp()+"',ville_c ='"+unClient.getVille()+"', tel_c ='"+unClient.getTel()+"', rib_c='"
						  +unClient.getRib()+"' where id_c ='"+unClient.getId_c()+"';";
		
		executerRequete(requete);
	}
	public static void deleteClient(int idClient) {
		String requete = "delete from client where id_c ='"+idClient+"';";
		executerRequete(requete);
	}
	public static ArrayList<Client> selectAllClients(String filtre){
		ArrayList<Client> lesClients = new ArrayList<Client>();
		String requete;
		
		if (filtre.equals("")){
			requete = "select * from client;"; 	
		}else {
			requete = "select * from client where nom_c like '%"+filtre+"%' or prenom_c like '%"+filtre+"%';";
		}
		
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class client*/
				Client unClient = new Client(desResultats.getInt("id_c"),
						desResultats.getString("nom_c"),desResultats.getString("prenom_c"),desResultats.getString("email_c"),
						desResultats.getString("mdp_c"),desResultats.getString("adr_c"),desResultats.getString("cp_c"),
						desResultats.getString("ville_c"),desResultats.getString("tel_c"),desResultats.getString("rib_c"));
				
				lesClients.add(unClient);
			}
			
		}catch(SQLException e) {
		System.out.println("requete incorrect");
		}
		return lesClients;
	}
	
	public static Client selectWhereClient(String email) {
	    Client leClient = null;
	    String requete = "SELECT * FROM client WHERE email_c = '" + email + "';";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            leClient = new Client(
	                rs.getInt("id_c"),
	                rs.getString("nom_c"),
	                rs.getString("prenom_c"),
	                rs.getString("email_c"),
	                rs.getString("mdp_c"),
	                rs.getString("adr_c"),
	                rs.getString("cp_c"),
	                rs.getString("ville_c"),
	                rs.getString("tel_c"),
	                rs.getString("rib_c")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SELECT WHERE : " + e.getMessage());
	    }

	    return leClient;
	}
	
	
	
	
	
	
	
	
	
	/*** Proprietaires ***/
	
	public static void insertProprietaire(Proprietaire unProprietaire) {
		String requete = "insert into proprietaire values (null,'"+unProprietaire.getNom()+"','"+unProprietaire.getPrenom()+"','"
						  +unProprietaire.getEmail()+"','"+unProprietaire.getMdp()+"','"+unProprietaire.getAdresse()+"','"
						  +unProprietaire.getCp()+"','"+unProprietaire.getVille()+"','"+unProprietaire.getTel()+"','"
						  +unProprietaire.getRib()+"');";
		
		executerRequete(requete);
	}
	public static void updateProprietaire(Proprietaire unProprietaire) {
		String requete = "update proprietaire set nom_p ='"+unProprietaire.getNom()+"',prenom_p ='"+unProprietaire.getPrenom()+
						 "',email_p ='"+unProprietaire.getEmail()+"', mdp_p ='"+unProprietaire.getMdp()+"', adr_p ='"
						 +unProprietaire.getAdresse()+"',cp_p ='"+unProprietaire.getCp()+"',ville_p ='"+unProprietaire.getVille()+
						 "', tel_p ='"+unProprietaire.getTel()+"', rib_p='"+unProprietaire.getRib()+"' where id_p = '"
						 +unProprietaire.getId_p()+"';";
		
		executerRequete(requete);
	}
	public static void deleteProprietaire(int idProprietaire) {
		String requete = "delete from proprietaire where id_p ='"+idProprietaire+"';";
		executerRequete(requete);
	}
	public static ArrayList<Proprietaire> selectAllProprietaires(String filtre){
		ArrayList<Proprietaire> lesProprietaires = new ArrayList<Proprietaire>();
		String requete; 
		if(filtre.equals("")) {
			requete = "select * from proprietaire";
		}else {
			requete = "select * from proprietaire where nom_p like '%"+filtre+"%' or prenom_p like '%"+filtre+"%';";
		}
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class proprio*/
				Proprietaire unProprietaire = new Proprietaire(desResultats.getInt("id_p"),
						desResultats.getString("nom_p"),desResultats.getString("prenom_p"),desResultats.getString("email_p"),
						desResultats.getString("mdp_p"),desResultats.getString("adr_p"),desResultats.getString("cp_p"),
						desResultats.getString("ville_p"),desResultats.getString("tel_p"),desResultats.getString("rib_p"));
				lesProprietaires.add(unProprietaire);
			}
			
		}catch(SQLException e) {
			System.out.println("requete incorrect");
		}
		return lesProprietaires;
	}
	
	public static Proprietaire selectWhereProprietaire(String email) {
	    Proprietaire leProprietaire = null;
	    String requete = "SELECT * FROM proprietaire WHERE email_p = '" + email + "';";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            leProprietaire = new Proprietaire(
	                rs.getInt("id_p"),
	                rs.getString("nom_p"),
	                rs.getString("prenom_p"),
	                rs.getString("email_p"),
	                rs.getString("mdp_p"),
	                rs.getString("adr_p"),
	                rs.getString("cp_p"),
	                rs.getString("ville_p"),
	                rs.getString("tel_p"),
	                rs.getString("rib_p")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SELECT WHERE : " + e.getMessage());
	    }

	    return leProprietaire;
	}
	
	
	
	
	
	
	
	
	
	/*** habitations ***/	
	
	public static void updateHabitation(Habitation uneHabitation) {
		String requete = "update habitation set type_hab ='"+uneHabitation.getType()+"',adr_hab ='"
						 +uneHabitation.getAdresse()+"',cp_hab ='"+uneHabitation.getCp()+"', ville_hab ='"
						 +uneHabitation.getVille()+"', tarif_hab_bas ='"+uneHabitation.getTarifBas()+"',tarif_hab_moy ='"
						 +uneHabitation.getTarifMoy()+"',tarif_hab_hau ='"+uneHabitation.getTarifHaut()+"', surface ='"
						 +uneHabitation.getSurface()+"', id_p='"+uneHabitation.getIdProprietaire()+"' where ref_hab = '"
						 +uneHabitation.getRef_hab()+"';";
		
		executerRequete(requete);
	}
	public static void deleteHabitation(int ref_hab) {
		String requete = "delete from habitation where ref_hab ='"+ref_hab+"';";
		executerRequete(requete);
	}
	public static ArrayList<Habitation> selectAllHabitations(String filtre){
		ArrayList<Habitation> lesHabitations = new ArrayList<Habitation>();
		String requete;
		
		if(filtre.equals("")) {
			requete = "select * from habitation";
		}else {
			requete = "select * from habitation where ville_hab like '%"+filtre+"%' or cp_hab like '%"+filtre+"%' or type_hab like '%"+filtre+"%';";
		}
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class habitation*/
				Habitation uneHabitation = new Habitation(desResultats.getInt("ref_hab"),
						desResultats.getString("type_hab"),desResultats.getString("adr_hab"),desResultats.getString("cp_hab"),
						desResultats.getString("ville_hab"),desResultats.getString("tarif_hab_bas"),desResultats.getString("tarif_hab_moy"),
						desResultats.getString("tarif_hab_hau"),desResultats.getString("surface"),desResultats.getInt("id_p"));
				lesHabitations.add(uneHabitation);
			}
			
		}catch(SQLException e) {
		System.out.println("Erreur SELECT * : "+ e.getMessage());
		}
		return lesHabitations;
	}
	
	public static Habitation selectWhereHabitation(int ref_hab) {
	    Habitation uneHabitation = null;
	    String requete = "SELECT * FROM habitation WHERE ref_hab =" + ref_hab + ";";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            uneHabitation = new Habitation(
	                rs.getInt("ref_hab"),
	                rs.getString("type_hab"),
	                rs.getString("adr_hab"),
	                rs.getString("cp_hab"),
	                rs.getString("ville_hab"),
	                rs.getString("tarif_hab_bas"),
	                rs.getString("tarif_hab_moy"),
	                rs.getString("tarif_hab_hau"),
	                rs.getString("surface"),
	                rs.getInt("id_p")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SELECT WHERE : " + e.getMessage());
	    }

	    return uneHabitation;
	}
	public static int insertHabitation(Habitation uneHabitation) {
	    int idGenere = 0;

	    String requete = "INSERT INTO habitation " + 
	    				 "(type_hab, adr_hab, cp_hab, ville_hab, tarif_hab_bas, tarif_hab_moy, tarif_hab_hau, surface, id_p) " 
	    				 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	        uneBdd.seConnecter();
	        PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(
	            requete,
	            Statement.RETURN_GENERATED_KEYS
	        );

	        pst.setString(1, uneHabitation.getType());
	        pst.setString(2, uneHabitation.getAdresse());
	        pst.setString(3, uneHabitation.getCp());
	        pst.setString(4, uneHabitation.getVille());
	        pst.setString(5, uneHabitation.getTarifBas());
	        pst.setString(6, uneHabitation.getTarifMoy());
	        pst.setString(7, uneHabitation.getTarifHaut());
	        pst.setString(8, uneHabitation.getSurface());
	        pst.setInt(9, uneHabitation.getIdProprietaire());

	        pst.executeUpdate();

	        // Récupération de l’ID auto-incrémenté
	        ResultSet rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            idGenere = rs.getInt(1);
	            uneHabitation.setRef_hab(idGenere);
	        }

	        pst.close();
	        uneBdd.seDeconnecter();

	    } catch (SQLException e) {
	        System.out.println("Erreur INSERT : " + e.getMessage());
	    }

	    return idGenere;
	}
	
	
	
	
	
	
	
	
	
	/*** reservations ***/
	
	public static void updateReservation(Reservation uneReservation) {
		String requete = "update reservation set date_res ='"+uneReservation.getDate_res()+"',nb_perso ='"
						 +uneReservation.getNb_perso()+"',date_debut ='"+uneReservation.getDate_debut()+"', date_fin ='"
						 +uneReservation.getDate_fin()+"', etat_res ='"+uneReservation.getEtat_res()+"',id_c ='"
						 +uneReservation.getId_c()+"',ref_hab ='"+uneReservation.getRef_hab()+"' where ref_res ='"+
						 uneReservation.getRef_res()+"' ;";
		executerRequete(requete);
	}
	public static void deleteReservation(int refRes) {
		String requete = "delete from reservation where ref_res ='"+refRes+"';";
		executerRequete(requete);
	}
	public static ArrayList<Reservation> selectAllReservations(String filtre){
		ArrayList<Reservation> lesReservations = new ArrayList<Reservation>();
		String requete;
		
		if(filtre.equals("")) {
			requete = "select * from reservation";
		}else {
			requete = "select * from reservation where etat_res like '%"+filtre+"%';";
		}
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class reservation*/
				Reservation uneReservation = new Reservation(desResultats.getInt("ref_res"),
						desResultats.getString("date_res"),desResultats.getInt("nb_perso"),desResultats.getString("date_debut"),
						desResultats.getString("date_fin"),desResultats.getString("etat_res"),desResultats.getInt("id_c"),
						desResultats.getInt("ref_hab"));
				lesReservations.add(uneReservation);
			}
			
		}catch(SQLException e) {
		System.out.println("Erreur SELECT * : "+ e.getMessage());
		}
		return lesReservations;
	}
	
	public static Reservation selectWhereReservation(int ref_res) {
	    Reservation uneReservation = null;
	    String requete = "SELECT * FROM reservation WHERE ref_res =" + ref_res+ ";";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            uneReservation = new Reservation(
	                rs.getInt("ref_res"),
	                rs.getString("date_res"),
	                rs.getInt("nb_perso"),
	                rs.getString("date_debut"),
	                rs.getString("date_fin"),
	                rs.getString("etat_res"),
	                rs.getInt("id_c"),
	                rs.getInt("ref_hab")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SELECT WHERE : " + e.getMessage());
	    }

	    return uneReservation;
	}
	
	public static int insertReservation(Reservation uneReservation) {
	    int idGenere = 0;

	    String requete = "INSERT INTO reservation " + 
				 "(date_res, nb_perso, date_debut, date_fin, etat_res, id_c, ref_hab) " 
				 + "VALUES (?, ?, ?, ?, ?, ?, ?)";


	    try {
	        uneBdd.seConnecter();
	        PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(
	            requete,
	            Statement.RETURN_GENERATED_KEYS
	        );

	        pst.setString(1, uneReservation.getDate_res());
	        pst.setInt(2, uneReservation.getNb_perso());
	        pst.setString(3, uneReservation.getDate_debut());
	        pst.setString(4, uneReservation.getDate_fin());
	        pst.setString(5, uneReservation.getEtat_res());
	        pst.setInt(6, uneReservation.getId_c());
	        pst.setInt(7, uneReservation.getRef_hab());

	        pst.executeUpdate();

	        // Récupération de l’ID auto-incrémenté
	        ResultSet rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            idGenere = rs.getInt(1);
	            uneReservation.setRef_res(idGenere);
	        }

	        pst.close();
	        uneBdd.seDeconnecter();

	    } catch (SQLException e) {
	        System.out.println("Erreur INSERT : " + e.getMessage());
	    }

	    return idGenere;
	}


	
	
	
	
	
	
	
	/*********************************** Methodes communes ***************************************************/
	public static void executerRequete(String requete) {
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			unStat.execute(requete);
			unStat.close();
			uneBdd.seDeconnecter();
			
		}catch(SQLException e) {
			System.out.println("erreur requete : " + requete);
			e.printStackTrace();
		}
	}
}


