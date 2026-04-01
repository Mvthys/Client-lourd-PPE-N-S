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
import controleur.Maison;
import controleur.Appartement;

public class Modele {
	
	private static Bdd uneBdd = new Bdd("localhost","neigeetsoleil","root","");
	
	
	
/*** Utilisateur ***/
public static Utilisateur selectWhereUtilisateur(String email, String mdp) {
	Utilisateur unUtilisateur = null;
	String requete = "select * from utilisateur where email ='"+ email +"' and mdp ='"+ mdp +"';";
		
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
	    String reqUser = "insert into utilisateur values (null, '"
	            + unClient.getNom() + "', '" + unClient.getPrenom() + "', '"
	            + unClient.getEmail() + "', '" + unClient.getMdp() + "', '"
	            + unClient.getTel() + "', 'client');";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        
	        unStat.executeUpdate(reqUser, Statement.RETURN_GENERATED_KEYS);

	        int lastId = 0;
	        ResultSet rs = unStat.getGeneratedKeys();
	        if (rs.next()) {
	            lastId = rs.getInt(1);
	        }

	        String reqClient = "insert into client values ("
	                + lastId + ", '" + unClient.getAdresse() + "', '"
	                + unClient.getCp() + "', '" + unClient.getVille() + "', '"
	                + unClient.getRib() + "');";

	        unStat.executeUpdate(reqClient);

	        unStat.close();
	        uneBdd.seDeconnecter();
	        
	        System.out.println("Insertion réussie pour l'ID : " + lastId);

	    } catch (SQLException e) {
	        System.out.println("Erreur SQL : " + e.getMessage());
	    }
	}
	
	public static void updateClient(Client unClient) {
	    // 1. Mise à jour de la table parente (Utilisateur)
	    String reqUser = "update utilisateur set nom = '" + unClient.getNom() + "', "
	            + "prenom = '" + unClient.getPrenom() + "', "
	            + "email = '" + unClient.getEmail() + "', "
	            + "mdp = '" + unClient.getMdp() + "', "
	            + "tel = '" + unClient.getTel() + "' "
	            + "where id_user = " + unClient.getId_user() + ";";

	    // 2. Mise à jour de la table fille (Client)
	    String reqClient = "update client set adresse = '" + unClient.getAdresse() + "', "
	            + "cp = '" + unClient.getCp() + "', "
	            + "ville = '" + unClient.getVille() + "', "
	            + "RIB = '" + unClient.getRib() + "' "
	            + "where id_c = " + unClient.getId_user() + ";";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        
	        // On exécute les deux mises à jour
	        unStat.executeUpdate(reqUser);
	        unStat.executeUpdate(reqClient);

	        unStat.close();
	        uneBdd.seDeconnecter();
	        
	        System.out.println("Mise à jour réussie pour l'ID : " + unClient.getId_user());

	    } catch (SQLException e) {
	        System.out.println("Erreur SQL lors de l'update : " + e.getMessage());
	    }
	}
	
	public static void deleteClient(int idClient) {
		String requete = "delete from utilisateur where id_user ='"+idClient+"';";
		executerRequete(requete);
	}
	
	public static ArrayList<Client> selectAllClients(String filtre){
		ArrayList<Client> lesClients = new ArrayList<Client>();
		String requete;
		
		if (filtre.equals("")){
			requete = "select * from utilisateur U inner join client C on U.id_user = C.id_c where U.role = 'client';"; 	
		}else {
			requete = "select * from utilisateur U"
			        + " inner join client C"
			        + " on C.id_c = U.id_user"
			        + " where (U.nom like '%"+filtre+"%'" 
			        + " or U.prenom like '%"+filtre+"%'"
			        + " or C.cp like '%"+filtre+"%');";
			}
		
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class client*/
				Client unClient = new Client(desResultats.getInt("id_user"),
						desResultats.getString("nom"),desResultats.getString("prenom"),desResultats.getString("email"),
						desResultats.getString("mdp"),desResultats.getString("tel"),desResultats.getString("role"),
						desResultats.getString("adresse"),desResultats.getString("cp"),desResultats.getString("ville"),
						desResultats.getString("RIB"));
				
				lesClients.add(unClient);
			}
			
		}catch(SQLException e) {
		System.out.println("requete incorrect : " + requete);
		}
		return lesClients;
	}
	
	public static Client selectWhereClient(String email) {
		Client leClient = null;
	    String requete = "select * from utilisateur U"
		        + " inner join client C"
		        + " on C.id_c = U.id_user"
		        + " where U.email = '"+email+"';";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            leClient = new Client(
	                rs.getInt("id_user"),
	                rs.getString("nom"),
	                rs.getString("prenom"),
	                rs.getString("email"),
	                rs.getString("mdp"),
	                rs.getString("tel"),
	                rs.getString("role"),
	                rs.getString("adresse"),
	                rs.getString("cp"),
	                rs.getString("ville"),
	                rs.getString("RIB")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SELECT WHERE : " + e.getMessage());
	    }

	    return leClient;
	}
		
	
	
	
	
	
	
	
	
	/*** Proprietaires ***/
	
	public static void insertProprietaire(Proprietaire unProprietaire) {
		String reqUser = "insert into utilisateur values (null, '"
	            + unProprietaire.getNom() + "', '" + unProprietaire.getPrenom() + "', '"
	            + unProprietaire.getEmail() + "', '" + unProprietaire.getMdp() + "', '"
	            + unProprietaire.getTel() + "', 'proprietaire');";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        
	        unStat.executeUpdate(reqUser, Statement.RETURN_GENERATED_KEYS);

	        int lastId = 0;
	        ResultSet rs = unStat.getGeneratedKeys();
	        if (rs.next()) {
	            lastId = rs.getInt(1);
	        }

	        String reqProprietaire = "insert into proprietaire (id_p,adresse,cp,ville,RIB) values ("
	                + lastId + ", '" + unProprietaire.getAdresse() + "', '"
	                + unProprietaire.getCp() + "', '" + unProprietaire.getVille() + "', '"
	                + unProprietaire.getRib() + "');";

	        unStat.executeUpdate(reqProprietaire);

	        unStat.close();
	        uneBdd.seDeconnecter();
	        
	        System.out.println("Insertion réussie pour l'ID : " + lastId);

	    } catch (SQLException e) {
	        System.out.println("Erreur SQL : " + e.getMessage());
	    }
	}
	
	public static void updateProprietaire(Proprietaire unProprietaire) {
	    // 1. Mise à jour de la table parente (Utilisateur)
	    String reqUser = "update utilisateur set nom = '" + unProprietaire.getNom() + "', "
	            + "prenom = '" + unProprietaire.getPrenom() + "', "
	            + "email = '" + unProprietaire.getEmail() + "', "
	            + "mdp = '" + unProprietaire.getMdp() + "', "
	            + "tel = '" + unProprietaire.getTel() + "' "
	            + "where id_user = " + unProprietaire.getId_user() + ";";

	    // 2. Mise à jour de la table fille (Client)
	    String reqProprietaire = "update proprietaire set adresse = '" + unProprietaire.getAdresse() + "', "
	            + "cp = '" + unProprietaire.getCp() + "', "
	            + "ville = '" + unProprietaire.getVille() + "', "
	            + "RIB = '" + unProprietaire.getRib() + "' "
	            + "where id_p = " + unProprietaire.getId_user() + ";";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        
	        // On exécute les deux mises à jour
	        unStat.executeUpdate(reqUser);
	        unStat.executeUpdate(reqProprietaire);

	        unStat.close();
	        uneBdd.seDeconnecter();
	        
	        System.out.println("Mise à jour réussie pour l'ID : " + unProprietaire.getId_user());

	    } catch (SQLException e) {
	        System.out.println("Erreur SQL lors de l'update : " + e.getMessage());
	    }
	}
	
	public static void deleteProprietaire(int idProprietaire) {
		String requete = "delete from utilisateur where id_user ='"+idProprietaire+"';";
		executerRequete(requete);
	}
	
	public static ArrayList<Proprietaire> selectAllProprietaires(String filtre){
		ArrayList<Proprietaire> lesProprietaires = new ArrayList<Proprietaire>();
		String requete;
		
		if (filtre.equals("")){
			requete = "select * from utilisateur U inner join proprietaire P on U.id_user = P.id_p where U.role = 'proprietaire';"; 	
		}else {
			requete = "select * from utilisateur U"
			        + " inner join proprietaire P"
			        + " on P.id_p = U.id_user"
			        + " where (U.nom like '%"+filtre+"%'" 
			        + " or U.prenom like '%"+filtre+"%'"
			        + " or P.cp like '%"+filtre+"%');";
			}
		
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class client*/
				Proprietaire unProprietaire = new Proprietaire(desResultats.getInt("id_user"),
						desResultats.getString("nom"),desResultats.getString("prenom"),desResultats.getString("email"),
						desResultats.getString("mdp"),desResultats.getString("tel"),desResultats.getString("role"),
						desResultats.getString("adresse"),desResultats.getString("cp"),desResultats.getString("ville"),
						desResultats.getString("RIB"));
				
				lesProprietaires.add(unProprietaire);
			}
			
		}catch(SQLException e) {
		System.out.println("requete incorrect : " + requete);
		}
		return lesProprietaires;
	}
	
	public static Proprietaire selectWhereProprietaire(String email) {
	    Proprietaire leProprietaire = null;
	    String requete = "select * from utilisateur U"
		        + " inner join proprietaire P"
		        + " on P.id_p = U.id_user"
		        + " where U.email = '"+email+"';";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            leProprietaire = new Proprietaire(
	                rs.getInt("id_user"),
	                rs.getString("nom"),
	                rs.getString("prenom"),
	                rs.getString("email"),
	                rs.getString("mdp"),
	                rs.getString("tel"),
	                rs.getString("role"),
	                rs.getString("adresse"),
	                rs.getString("cp"),
	                rs.getString("ville"),
	                rs.getString("RIB")
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
						 +uneHabitation.getSurface()+"', id_p='"+uneHabitation.getIdProprietaire()+"', description_hab ='"
						 +uneHabitation.getDescription()+"', titre_hab = '"+uneHabitation.getTitre()+"', capacite_hab ='"
						 +uneHabitation.getCapacite()+"' where ref_hab = '"+uneHabitation.getRef_hab()+"';";
		
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
						desResultats.getString("tarif_hab_hau"),desResultats.getString("surface"),desResultats.getInt("id_p"),
						desResultats.getString("description_hab"),desResultats.getString("titre_hab"),desResultats.getInt("capacite_hab"));
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
	                rs.getInt("id_p"),
	                rs.getString("description"),
	                rs.getString("titre"),
	                rs.getInt("capacite")
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


	
	//maison	
	
	public static void updateMaison(Maison uneMaison) {
		String requete = "update maison set type_hab ='"+uneMaison.getType()+"', adr_hab ='"
						 +uneMaison.getAdresse()+"', cp_hab ='"+uneMaison.getCp()+"', ville_hab ='"
						 +uneMaison.getVille()+"', tarif_hab_bas ='"+uneMaison.getTarifBas()+"', tarif_hab_moy ='"
						 +uneMaison.getTarifMoy()+"', tarif_hab_hau ='"+uneMaison.getTarifHaut()+"', surface ='"
						 +uneMaison.getSurface()+"', id_p='"+uneMaison.getIdProprietaire()+"', description_hab = '"
						 +uneMaison.getDescription()+"', titre_hab = '"+uneMaison.getTitre()+"', capacite_hab = '"
						 +uneMaison.getCapacite()+"', carac_m = '"+uneMaison.getCaracteristique()+"' where ref_hab = '"
						 +uneMaison.getRef_hab()+"';";
		
		executerRequete(requete);
	}
	public static void deleteMaison(int ref_hab) {
		String requete = "delete from maison where ref_hab ='"+ref_hab+"';";
		executerRequete(requete);
	}
	public static ArrayList<Maison> selectAllMaisons(String filtre){
		ArrayList<Maison> lesMaisons = new ArrayList<Maison>();
		String requete;
		
		if(filtre.equals("")) {
			requete = "select * from maison";
		}else {
			requete = "select * from maison where ville_hab like '%"+filtre+"%' or cp_hab like '%"+filtre+"%' or type_hab like '%"+filtre+"%';";
		}
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class habitation*/
				Maison uneMaison = new Maison(desResultats.getInt("ref_hab"),
						desResultats.getString("type_hab"),desResultats.getString("adr_hab"),desResultats.getString("cp_hab"),
						desResultats.getString("ville_hab"),desResultats.getString("tarif_hab_bas"),desResultats.getString("tarif_hab_moy"),
						desResultats.getString("tarif_hab_hau"),desResultats.getString("surface"),desResultats.getInt("id_p"),
						desResultats.getString("description_hab"),desResultats.getString("titre_hab"),desResultats.getInt("capacite_hab"),desResultats.getString("carac_m"));
				lesMaisons.add(uneMaison);
			}
			
		}catch(SQLException e) {
		System.out.println("Erreur SELECT * : "+ e.getMessage());
		}
		return lesMaisons;
	}
	
	public static Maison selectWhereMaison(int ref_hab) {
	    Maison uneMaison = null;
	    String requete = "SELECT * FROM maison WHERE ref_hab =" + ref_hab + ";";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            uneMaison = new Maison(
	                rs.getInt("ref_hab"),
	                rs.getString("type_hab"),
	                rs.getString("adr_hab"),
	                rs.getString("cp_hab"),
	                rs.getString("ville_hab"),
	                rs.getString("tarif_hab_bas"),
	                rs.getString("tarif_hab_moy"),
	                rs.getString("tarif_hab_hau"),
	                rs.getString("surface"),
	                rs.getInt("id_p"),
	                rs.getString("description"),
	                rs.getString("titre"),
	                rs.getInt("capacite"),
	                rs.getString("caracteristique")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SELECT WHERE : " + e.getMessage());
	    }

	    return uneMaison;
	}
	public static int insertMaison(Maison uneMaison) {
	    int idGenere = 0;

	    String requete = "INSERT INTO maison " + 
	    				 "(type_hab, adr_hab, cp_hab, ville_hab, tarif_hab_bas, tarif_hab_moy, tarif_hab_hau, surface, id_p, description_hab, titre_hab, capacite_hab, carac_m) " 
	    				 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	        uneBdd.seConnecter();
	        PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(
	            requete,
	            Statement.RETURN_GENERATED_KEYS
	        );

	        pst.setString(1, uneMaison.getType());
	        pst.setString(2, uneMaison.getAdresse());
	        pst.setString(3, uneMaison.getCp());
	        pst.setString(4, uneMaison.getVille());
	        pst.setString(5, uneMaison.getTarifBas());
	        pst.setString(6, uneMaison.getTarifMoy());
	        pst.setString(7, uneMaison.getTarifHaut());
	        pst.setString(8, uneMaison.getSurface());
	        pst.setInt(9, uneMaison.getIdProprietaire());
	        pst.setString(10, uneMaison.getDescription());
	        pst.setString(11, uneMaison.getTitre());
	        pst.setInt(12, uneMaison.getCapacite());	        
	        pst.setString(13, uneMaison.getCaracteristique());

	        pst.executeUpdate();

	        // Récupération de l’ID auto-incrémenté
	        ResultSet rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            idGenere = rs.getInt(1);
	            uneMaison.setRef_hab(idGenere);
	        }

	        pst.close();
	        uneBdd.seDeconnecter();

	    } catch (SQLException e) {
	        System.out.println("Erreur INSERT : " + e.getMessage());
	    }

	    return idGenere;
	}
	
	
	//appartements
	
	public static void updateAppartement(Appartement unAppartement) {
		String requete = "update Appartement set type_hab ='"+unAppartement.getType()+"', adr_hab ='"
						 +unAppartement.getAdresse()+"', cp_hab ='"+unAppartement.getCp()+"', ville_hab ='"
						 +unAppartement.getVille()+"', tarif_hab_bas ='"+unAppartement.getTarifBas()+"', tarif_hab_moy ='"
						 +unAppartement.getTarifMoy()+"', tarif_hab_hau ='"+unAppartement.getTarifHaut()+"', surface ='"
						 +unAppartement.getSurface()+"', id_p='"+unAppartement.getIdProprietaire()+"', description_hab = '"
						 +unAppartement.getDescription()+"', titre_hab = '"+unAppartement.getTitre()+"', capacite_hab = '"
						 +unAppartement.getCapacite()+"', etage_ap = '"+unAppartement.getEtage()+"', type_ap = '"
						 +unAppartement.getTypeap()+"' where ref_hab = '"+unAppartement.getRef_hab()+"';";
		
		executerRequete(requete);
	}
	public static void deleteAppartement(int ref_hab) {
		String requete = "delete from appartement where ref_hab ='"+ref_hab+"';";
		executerRequete(requete);
	}
	public static ArrayList<Appartement> selectAllAppartements(String filtre){
		ArrayList<Appartement> lesAppartements = new ArrayList<Appartement>();
		String requete;
		
		if(filtre.equals("")) {
			requete = "select * from Appartement";
		}else {
			requete = "select * from Appartement where ville_hab like '%"+filtre+"%' or cp_hab like '%"+filtre+"%' or type_hab like '%"+filtre+"%';";
		}
		
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			
			while (desResultats.next()) {
				/*Instantiation class habitation*/
				Appartement unAppartement = new Appartement(desResultats.getInt("ref_hab"),
						desResultats.getString("type_hab"),desResultats.getString("adr_hab"),desResultats.getString("cp_hab"),
						desResultats.getString("ville_hab"),desResultats.getString("tarif_hab_bas"),desResultats.getString("tarif_hab_moy"),
						desResultats.getString("tarif_hab_hau"),desResultats.getString("surface"),desResultats.getInt("id_p"),
						desResultats.getString("description_hab"),desResultats.getString("titre_hab"),desResultats.getInt("capacite_hab"),desResultats.getInt("etage_ap"),
						desResultats.getString("type_ap"));
				lesAppartements.add(unAppartement);
			}
			
		}catch(SQLException e) {
		System.out.println("Erreur SELECT * : "+ e.getMessage());
		}
		return lesAppartements;
	}
	
	public static Appartement selectWhereAppartement(int ref_hab) {
		Appartement unAppartement = null;
	    String requete = "SELECT * FROM appartement WHERE ref_hab =" + ref_hab + ";";

	    try {
	        uneBdd.seConnecter();
	        Statement unStat = uneBdd.getMaConnexion().createStatement();
	        ResultSet rs = unStat.executeQuery(requete);

	        if (rs.next()) {
	            unAppartement = new Appartement(
	                rs.getInt("ref_hab"),
	                rs.getString("type_hab"),
	                rs.getString("adr_hab"),
	                rs.getString("cp_hab"),
	                rs.getString("ville_hab"),
	                rs.getString("tarif_hab_bas"),
	                rs.getString("tarif_hab_moy"),
	                rs.getString("tarif_hab_hau"),
	                rs.getString("surface"),
	                rs.getInt("id_p"),
	                rs.getString("description"),
	                rs.getString("titre"),
	                rs.getInt("capacite"),
	                rs.getInt("etage"),
	                rs.getString("type_ap")
	            );
	        }

	    } catch (SQLException e) {
	        System.out.println("Erreur SELECT WHERE : " + e.getMessage());
	    }

	    return unAppartement;
	}
	public static int insertAppartement(Appartement unAppartement) {
	    int idGenere = 0;

	    String requete = "INSERT INTO appartement " + 
	    				 "(type_hab, adr_hab, cp_hab, ville_hab, tarif_hab_bas, tarif_hab_moy, tarif_hab_hau, surface, id_p, description_hab, titre_hab, capacite_hab, etage_ap, type_ap) " 
	    				 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	        uneBdd.seConnecter();
	        PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(
	            requete,
	            Statement.RETURN_GENERATED_KEYS
	        );

	        pst.setString(1, unAppartement.getType());
	        pst.setString(2, unAppartement.getAdresse());
	        pst.setString(3, unAppartement.getCp());
	        pst.setString(4, unAppartement.getVille());
	        pst.setString(5, unAppartement.getTarifBas());
	        pst.setString(6, unAppartement.getTarifMoy());
	        pst.setString(7, unAppartement.getTarifHaut());
	        pst.setString(8, unAppartement.getSurface());
	        pst.setInt(9, unAppartement.getIdProprietaire());
	        pst.setString(10, unAppartement.getDescription());
	        pst.setString(11, unAppartement.getTitre());
	        pst.setInt(12, unAppartement.getCapacite());
	        pst.setInt(13, unAppartement.getEtage());
	        pst.setString(14, unAppartement.getTypeap());

	        pst.executeUpdate();

	        // Récupération de l’ID auto-incrémenté
	        ResultSet rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            idGenere = rs.getInt(1);
	            unAppartement.setRef_hab(idGenere);
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
	
	public static int selectCountUtilisateur(String role) {
		int nb=0;
		String requete = "select count(*) as nb from utilisateur where role = '"+ role +"';";
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet unRes = unStat.executeQuery(requete);
			if(unRes.next()) {
				nb = unRes.getInt("nb");
			}
			unStat.close();
			uneBdd.seDeconnecter();
			
		}catch(SQLException e) {
			System.out.println("erreur requete : " + requete);
		}
		return nb;
	}
	
	public static int selectCount(String table) {
		int nb=0;
		String requete = "select count(*) as nb from "+ table +";";
		try {
			uneBdd.seConnecter();
			Statement unStat = uneBdd.getMaConnexion().createStatement();
			ResultSet unRes = unStat.executeQuery(requete);
			if(unRes.next()) {
				nb = unRes.getInt("nb");
			}
			unStat.close();
			uneBdd.seDeconnecter();
			
		}catch(SQLException e) {
			System.out.println("erreur requete : " + requete);
		}
		return nb;
	}
	
	

}


