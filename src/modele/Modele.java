package modele;

import java.io.File;
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
	
/***************************************************************     UTILISATEURS     ***************************************************************/

	// --- SELECT WHERE --- //
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
			e.printStackTrace();
		}
		return unUtilisateur;
	}









/***************************************************************     ADMIN     ***************************************************************/
	
	// --- SELECT WHERE --- //
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
			e.printStackTrace();
		}
		return unAdmin;
	}









/***************************************************************     CLIENTS     ***************************************************************/
	
	// --- INSERT --- //
	public static void insertClient(Client unClient) {
	    String reqUser = "INSERT INTO utilisateur (nom, prenom, email, mdp, tel, role) VALUES (?, ?, ?, ?, ?, 'client')";
	    String reqClient = "INSERT INTO client (id_c, adresse, cp, ville, RIB) VALUES (?, ?, ?, ?, ?)";

	    try {
	        uneBdd.seConnecter();
	        
	        PreparedStatement psUser = uneBdd.getMaConnexion().prepareStatement(reqUser, Statement.RETURN_GENERATED_KEYS);
	     
	        psUser.setString(1, unClient.getNom());
	        psUser.setString(2, unClient.getPrenom());
	        psUser.setString(3, unClient.getEmail());
	        psUser.setString(4, unClient.getMdp());
	        psUser.setString(5, unClient.getTel());
	        
	        psUser.executeUpdate();

	        int lastId = 0;
	        ResultSet rs = psUser.getGeneratedKeys();
	        if (rs.next()) {
	            lastId = rs.getInt(1);
	        }
	        psUser.close();

	        if (lastId > 0) {
	            PreparedStatement psClient = uneBdd.getMaConnexion().prepareStatement(reqClient);
	            psClient.setInt(1, lastId);
	            psClient.setString(2, unClient.getAdresse());
	            psClient.setString(3, unClient.getCp());
	            psClient.setString(4, unClient.getVille());
	            psClient.setString(5, unClient.getRib());
	            
	            psClient.executeUpdate();
	            psClient.close();
	            System.out.println("Insertion réussie en BDD pour l'ID : " + lastId);
	        }

	        uneBdd.seDeconnecter();
	        System.out.println("Ajout réussie du client : " + lastId);
	        
	    } catch (SQLException e) {
	        System.out.println("Erreur SQL lors de l'insertion : " + e.getMessage());
	        e.printStackTrace(); // voir erreur dans console 
	    }
	}
	
	// --- UPDATE --- //
	public static void updateClient(Client unClient) {
	    String reqUser = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, mdp = ?, tel = ? WHERE id_user = ?";
	    String reqClient = "UPDATE client set adresse = ?, cp = ?, ville = ?, RIB = ? WHERE id_c = ?";

	    try {
	        uneBdd.seConnecter();
	        
	        PreparedStatement psUser = uneBdd.getMaConnexion().prepareStatement(reqUser);
	        psUser.setString(1, unClient.getNom());
	        psUser.setString(2, unClient.getPrenom());
	        psUser.setString(3, unClient.getEmail());
	        psUser.setString(4, unClient.getMdp());
	        psUser.setString(5, unClient.getTel());
	        psUser.setInt(6, unClient.getId_user());
	        psUser.executeUpdate();
	        psUser.close();
	        
	        PreparedStatement psClient = uneBdd.getMaConnexion().prepareStatement(reqClient);
	        psClient.setString(1, unClient.getAdresse());
	        psClient.setString(2, unClient.getCp());
	        psClient.setString(3, unClient.getVille());
	        psClient.setString(4, unClient.getRib());
	        psClient.setInt(5, unClient.getId_user());
	        psClient.executeUpdate();
	        psClient.close();
	        
	        uneBdd.seDeconnecter();
	        System.out.println("Mise à jour réussie pour le client : " + unClient.getId_user());

	    } catch (SQLException e) {
	        System.out.println("Erreur SQL lors de l'update : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	// --- DELETE --- //
	public static void deleteClient(int idClient) {
		String requete = "DELETE FROM utilisateur WHERE id_user ='"+idClient+"';";
		executerRequete(requete);
	}
	
	// --- SELECT ALL --- //
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
			e.printStackTrace();
		}
		return lesClients;
	}
	
	// --- SELECT WHERE --- //
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
	        e.printStackTrace();
	    }

	    return leClient;
	}
	
	
	
	

	
	
	
	
/***************************************************************     PROPRIOS    ***************************************************************/
	
	// --- INSERT --- //
	public static void insertProprietaire(Proprietaire unProprietaire) {
		String reqUser = "INSERT INTO utilisateur (nom,prenom,email,mdp,tel,role) VALUES (?,?,?,?,?,'proprietaire')";
		String reqProprio = "INSERT INTO proprietaire (id_p,adresse,cp,ville,RIB) values (?,?,?,?,?)";
		
		try {
			uneBdd.seConnecter();
			
			PreparedStatement psUser = uneBdd.getMaConnexion().prepareStatement(reqUser,Statement.RETURN_GENERATED_KEYS);
			
			psUser.setString(1, unProprietaire.getNom());
			psUser.setString(2, unProprietaire.getPrenom());
			psUser.setString(3, unProprietaire.getEmail());
			psUser.setString(4, unProprietaire.getMdp());
			psUser.setString(5, unProprietaire.getTel());
			
			psUser.executeUpdate();
			
			int lastId = 0;
			ResultSet rs = psUser.getGeneratedKeys();
			if(rs.next()) {
				lastId = rs.getInt(1);
			}
			
			psUser.close();
			
			if(lastId > 0) {
				PreparedStatement psProprio = uneBdd.getMaConnexion().prepareStatement(reqProprio);
				
				psProprio.setInt(1, lastId);
				psProprio.setString(2, unProprietaire.getAdresse());
				psProprio.setString(3, unProprietaire.getCp());
				psProprio.setString(4, unProprietaire.getVille());
				psProprio.setString(5, unProprietaire.getRib());
				
				psProprio.executeUpdate();
				psProprio.close();
				System.out.println("Ajout réussie du propriétaire : " + lastId);
			}
			
			uneBdd.seDeconnecter();
			
		}catch(SQLException e) {
			System.out.println("Erreur requête :" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// --- UPDATE --- //
	public static void updateProprietaire(Proprietaire unProprietaire) {
	    String reqUser = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ?, mdp = ?, tel = ? WHERE id_user = ?";
	    String reqProprio = "UPDATE proprietaire SET adresse = ?, cp = ?, ville = ?, RIB = ? WHERE id_p = ?";
	    
	    try {
	    	uneBdd.seConnecter();
	    	
	    	PreparedStatement psUser = uneBdd.getMaConnexion().prepareStatement(reqUser);
	    	psUser.setString(1, unProprietaire.getNom());
	    	psUser.setString(2, unProprietaire.getPrenom());
	    	psUser.setString(3, unProprietaire.getEmail());
	    	psUser.setString(4, unProprietaire.getMdp());
	    	psUser.setString(5, unProprietaire.getTel());
	    	psUser.setInt(6, unProprietaire.getId_user());
	    	psUser.executeUpdate();
	    	psUser.close();
	    	
	    	PreparedStatement psProprio = uneBdd.getMaConnexion().prepareStatement(reqProprio);
	    	psProprio.setString(1, unProprietaire.getAdresse());
	    	psProprio.setString(2, unProprietaire.getCp());
	    	psProprio.setString(3, unProprietaire.getVille());
	    	psProprio.setString(4, unProprietaire.getRib());
	    	psProprio.setInt(5, unProprietaire.getId_user());
	    	psProprio.executeUpdate();
	    	psProprio.close();
	    	
	    	uneBdd.seDeconnecter();
	    	
	    	System.out.println("Mise à jour réussie du propriétaire : " + unProprietaire.getId_user());
	    	
	    }catch(SQLException e) {
	    	System.out.println("Erreur requête : " + e.getMessage());
	    	e.printStackTrace();
	    }
	}
	
	// --- DELETE --- //
	public static void deleteProprietaire(int idProprietaire) {
		String requete = "DELETE FROM utilisateur WHERE id_user ='"+idProprietaire+"';";
		executerRequete(requete);
	}
	
	// --- SELECT ALL --- //
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
				/*Instantiation class Proprietaire*/
				Proprietaire unProprietaire = new Proprietaire(desResultats.getInt("id_user"),
						desResultats.getString("nom"),desResultats.getString("prenom"),desResultats.getString("email"),
						desResultats.getString("mdp"),desResultats.getString("tel"),desResultats.getString("role"),
						desResultats.getString("adresse"),desResultats.getString("cp"),desResultats.getString("ville"),
						desResultats.getString("RIB"));
				
				lesProprietaires.add(unProprietaire);
			}
			
		}catch(SQLException e) {
			System.out.println("requete incorrect : " + requete);
			e.printStackTrace();
		}
		return lesProprietaires;
	}
	
	// --- SELECT WHERE --- //
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
	
	
	
	

	
	
	
	
/***************************************************************     HABITATIONS    ***************************************************************/
	
	// --- INSERT --- //
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
	        System.out.println("Ajout réussie de l'habitation : " + idGenere);

	    } catch (SQLException e) {
	        System.out.println("Erreur INSERT : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return idGenere;
	}
	
	// ---  UPDATE --- //
	public static void updateHabitation(Habitation uneHabitation) {
		String reqHab = "UPDATE habitation SET type_hab = ?, adr_hab = ?, cp_hab = ?, ville_hab = ?, tarif_hab_bas = ?, tarif_hab_moy = ?,"
						+ " tarif_hab_hau = ?, surface = ?, id_p = ?, description_hab = ?, titre_hab = ?, capacite_hab = ? WHERE ref_hab = ? ";
		try {
			uneBdd.seConnecter();
			
			PreparedStatement psHab = uneBdd.getMaConnexion().prepareStatement(reqHab);
			psHab.setString(1, uneHabitation.getType());
			psHab.setString(2, uneHabitation.getAdresse());
			psHab.setString(3, uneHabitation.getCp());
			psHab.setString(4, uneHabitation.getVille());
			psHab.setString(5, uneHabitation.getTarifBas());
			psHab.setString(6, uneHabitation.getTarifMoy());
			psHab.setString(7, uneHabitation.getTarifHaut());
			psHab.setString(8, uneHabitation.getSurface());
			psHab.setInt(9, uneHabitation.getIdProprietaire());
			psHab.setString(10, uneHabitation.getDescription());
			psHab.setString(11, uneHabitation.getTitre());
			psHab.setInt(12, uneHabitation.getCapacite());
			psHab.setInt(13, uneHabitation.getRef_hab());
			
			psHab.executeUpdate();
			psHab.close();
			uneBdd.seDeconnecter();
			
			System.out.println("Mise à jour réussie pour l'habitation : " + uneHabitation.getRef_hab());
			
		}catch(SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// ---  DELETE --- //
	public static void deleteHabitation(int ref_hab) {
		String requete = "DELETE FROM habitation WHERE ref_hab ='"+ref_hab+"';";
		executerRequete(requete);
	}
	
	// ---  SELECT ALL --- //
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
			e.printStackTrace();
		}
		return lesHabitations;
	}
	
	// ---  SELECT WHERE --- //
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
	
	// ---  SELECT CAPACITE HABITATION --- //
	public static int selectCapaciteHab(int ref_hab) {
		String requete = "SELECT capacite_hab FROM habitation WHERE ref_hab = ?";
		int capacite = 0;
		
		try {
			uneBdd.seConnecter();
			PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(requete);
			pst.setInt(1, ref_hab);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				capacite = rs.getInt("capacite_hab");
			}
			
			rs.close();
			pst.close();
			uneBdd.seDeconnecter();
			
		}catch(SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
		return capacite;
	}
	
	
	

	
	
	
	
/***************************************************************     MAISONS     ***************************************************************/	
	
	// ---  INSERT --- //
	public static int insertMaison(Maison uneMaison) {
	    int idGenere = 0;

	    String requete = "INSERT INTO maison " + 
	    				 "(type_hab, adr_hab, cp_hab, ville_hab, tarif_hab_bas, tarif_hab_moy, tarif_hab_hau, surface, id_p, description_hab, titre_hab, capacite_hab, carac_m) " 
	    				 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
	        uneBdd.seConnecter();
	        PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

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

	        // récup ID auto-incrémenté
	        ResultSet rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            idGenere = rs.getInt(1);
	            uneMaison.setRef_hab(idGenere);
	        }

	        pst.close();
	        uneBdd.seDeconnecter();
	        System.out.println("Ajout réussie de la maison : " + idGenere);

	    } catch (SQLException e) {
	        System.out.println("Erreur INSERT : " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return idGenere;
	}
	
	// ---  UPDATE --- //
	public static void updateMaison(Maison uneMaison) {
		String req = "UPDATE maison SET adr_hab = ?, cp_hab = ?, ville_hab = ?, tarif_hab_bas = ?, tarif_hab_moy = ?, "
					+ "tarif_hab_hau = ?, surface = ?, id_p = ?, description_hab = ?, titre_hab = ?, capacite_hab = ?, carac_m = ? WHERE ref_hab = ?";
		
		try {
			uneBdd.seConnecter();
			
			PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(req);
			pst.setString(1, uneMaison.getAdresse());
			pst.setString(2, uneMaison.getCp());
			pst.setString(3, uneMaison.getVille());
			pst.setString(4, uneMaison.getTarifBas());
			pst.setString(5, uneMaison.getTarifMoy());
			pst.setString(6, uneMaison.getTarifHaut());
			pst.setString(7, uneMaison.getSurface());
			pst.setInt(8, uneMaison.getIdProprietaire());
			pst.setString(9, uneMaison.getDescription());
			pst.setString(10, uneMaison.getTitre());
			pst.setInt(11, uneMaison.getCapacite());
			pst.setString(12, uneMaison.getCaracteristique());
			pst.setInt(13, uneMaison.getRef_hab());
			
			pst.executeUpdate();
			pst.close();
			uneBdd.seDeconnecter();
			System.out.println("Mise à jour réussie pour la maison : " + uneMaison.getRef_hab());
			
		} catch(SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}	
	}
	
	// ---  DELETE --- //
	public static void deleteMaison(int ref_hab) {
		String reqU = "UPDATE contrat SET status_c = 'Annule' WHERE ref_hab = ?";
		String reqD1 = "DELETE FROM contrat WHERE ref_hab = ?";
		String reqD2 = "DELETE FROM maison WHERE ref_hab = ?";
		
		try {
			uneBdd.seConnecter();
			
			//update contrat
			PreparedStatement pstU = uneBdd.getMaConnexion().prepareStatement(reqU);
			pstU.setInt(1, ref_hab);
			pstU.executeUpdate();
			pstU.close();
			
			//delete contrat
			PreparedStatement pstD1 = uneBdd.getMaConnexion().prepareStatement(reqD1);
			pstD1.setInt(1, ref_hab);
			pstD1.executeUpdate();
			pstD1.close();
			
			//delete maison
			PreparedStatement pstD2 = uneBdd.getMaConnexion().prepareStatement(reqD2);
			pstD2.setInt(1, ref_hab);
			pstD2.executeUpdate();
			pstD2.close();
			
			uneBdd.seDeconnecter();
			System.out.println("Suppression réussie pour la maison : " + ref_hab);
			
		}catch(SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// ---  SELECT ALL --- //
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
						desResultats.getString("description_hab"),desResultats.getString("titre_hab"),desResultats.getInt("capacite_hab"),
						desResultats.getString("carac_m"));
				lesMaisons.add(uneMaison);
			}
			
		}catch(SQLException e) {
		System.out.println("Erreur SELECT * : "+ e.getMessage());
		}
		return lesMaisons;
	}
	
	// ---  SELECT WHERE --- //
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
	
	
	
	
	
	
	
	
	
/***************************************************************     APPARTEMENTS     ***************************************************************/
	
	// ---  INSERT --- //
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
	        System.out.println("Insertion réussie pour l'appartement : " + idGenere);

	    } catch (SQLException e) {
	        System.out.println("Erreur INSERT : " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return idGenere;
	}
	
	// ---  UPDATE --- //
	public static void updateAppartement(Appartement unAppartement) {
		String req = "UPDATE appartement SET adr_hab = ?, cp_hab = ?, ville_hab = ?, tarif_hab_bas = ?, tarif_hab_moy = ?, tarif_hab_hau = ?,"
					+ " surface = ?, id_p = ?, description_hab = ?, titre_hab = ?, capacite_hab = ?, etage_ap = ?, type_ap = ? WHERE ref_hab = ?";
		
		try {
			uneBdd.seConnecter();
			
			PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(req);
			pst.setString(1, unAppartement.getAdresse());
			pst.setString(2, unAppartement.getCp());
			pst.setString(3, unAppartement.getVille());
			pst.setString(4, unAppartement.getTarifBas());
			pst.setString(5, unAppartement.getTarifMoy());
			pst.setString(6, unAppartement.getTarifHaut());
			pst.setString(7, unAppartement.getSurface());
			pst.setInt(8, unAppartement.getIdProprietaire());
			pst.setString(9, unAppartement.getDescription());
			pst.setString(10, unAppartement.getTitre());
			pst.setInt(11, unAppartement.getCapacite());
			pst.setInt(12, unAppartement.getEtage());
			pst.setString(13, unAppartement.getTypeap());
			pst.setInt(14, unAppartement.getRef_hab());
			
			pst.executeUpdate();
			pst.close();
			uneBdd.seDeconnecter();			
			System.out.println("Mise à jour réussie pour l'appartement : " + unAppartement.getRef_hab());
			
		}catch(SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// ---  DELETE --- //
	public static void deleteAppartement(int ref_hab) {
		String reqU = "UPDATE contrat SET status_c = 'Annule' WHERE ref_hab = ?";
		String reqD1 = "DELETE FROM contrat WHERE ref_hab = ?";
		String reqD2 = "DELETE FROM appartement WHERE ref_hab = ? ";
		
		try {
			uneBdd.seConnecter();
			
			//update
			PreparedStatement pstU = uneBdd.getMaConnexion().prepareStatement(reqU);
			pstU.setInt(1, ref_hab);
			pstU.executeUpdate();
			pstU.close();
			
			//delete contrat
			PreparedStatement pstD1 = uneBdd.getMaConnexion().prepareStatement(reqD1);
			pstD1.setInt(1, ref_hab);
			pstD1.executeUpdate();
			pstD1.close();
			
			//delete appart
			PreparedStatement pstD2 = uneBdd.getMaConnexion().prepareStatement(reqD2);
			pstD2.setInt(1, ref_hab);
			pstD2.executeUpdate();
			pstD2.close();
			
			uneBdd.seDeconnecter();
			System.out.println("Suppression réussie pour l'appartement : " + ref_hab);
			
		}catch(SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// ---  SELECT ALL --- //
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
	
	// ---  SELECT WHERE --- //
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

	
	
	
	
	
	
	
	
	
/***************************************************************     PHOTOS     ***************************************************************/	
	
	public static void insertPhotos(int ref_hab, String url_photo, int is_principal) {
	    String requete = "INSERT INTO photos (ref_hab, url_photo, is_principal) VALUES (?, ?, ?)";
	    try {
	        uneBdd.seConnecter();
	        
	        PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(requete);
	        pst.setInt(1, ref_hab);
	        pst.setString(2, url_photo);
	        pst.setInt(3, is_principal);
	        pst.executeUpdate(); 
	        pst.close();
	        
	        uneBdd.seDeconnecter();
	        System.out.println("Ajout des photos réussi pour l'habitation : " + ref_hab);
	        
	    } catch (SQLException e) {
	        System.out.println("Erreur SQL lors de l'insertion de l'image : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	public static void deletePhotos(int ref_hab) {
		String reqDelete = "DELETE FROM photos WHERE ref_hab = ?";
		String reqSelect = "SELECT url_photo FROM photos WHERE ref_hab = ?";
		String dossier = "C:/wamp64/www/PPE-BTS-SIO/Neige et Soleil/images/habitations/";
		
		try {
			uneBdd.seConnecter();
			
			//supp du dossier
			PreparedStatement pstS = uneBdd.getMaConnexion().prepareStatement(reqSelect);
			pstS.setInt(1, ref_hab);
			ResultSet rs = pstS.executeQuery();
			if(rs.next()) {
				String nomFichier = rs.getString("url_photo");
				File fichierSupp = new File(dossier+nomFichier);
				if(fichierSupp.exists()) {
					fichierSupp.delete();
				}
			}
			rs.close();
			pstS.close();
			
			//supp de la bdd
			PreparedStatement pstD = uneBdd.getMaConnexion().prepareStatement(reqDelete);
			pstD.setInt(1, ref_hab);
			pstD.executeUpdate();
			pstD.close();
			uneBdd.seDeconnecter();
			System.out.println("Suppressions photos réussie pour la réf : " + ref_hab);
			
		}catch(SQLException e) {
			System.out.println("Erreur SQL : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
/***************************************************************     RESERVATIONS     ***************************************************************/
	
	// ---  INSERT --- //
	public static int insertReservation(Reservation uneReservation) {
	    int idGenere = 0;

	    String requete = "INSERT INTO reservation " + 
				 "(date_res, nb_perso, date_debut, date_fin, etat_res, id_c, ref_hab) " 
				 + "VALUES (curdate(), ?, ?, ?, ?, ?, ?)";
	    
	    try {
	        uneBdd.seConnecter();
	        PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);

	        pst.setInt(1, uneReservation.getNb_perso());
	        pst.setString(2, uneReservation.getDate_debut());
	        pst.setString(3, uneReservation.getDate_fin());
	        pst.setString(4, uneReservation.getEtat_res());
	        pst.setInt(5, uneReservation.getId_c());
	        pst.setInt(6, uneReservation.getRef_hab());

	        pst.executeUpdate();

	        // récup ID auto-incrémenté
	        ResultSet rs = pst.getGeneratedKeys();
	        if (rs.next()) {
	            idGenere = rs.getInt(1);
	            uneReservation.setRef_res(idGenere);
	        }

	        pst.close();
	        uneBdd.seDeconnecter();
	        System.out.println("Ajoute réussie pour la réservation : " + idGenere);

	    } catch (SQLException e) {
	        System.out.println("Erreur INSERT : " + e.getMessage());
	        e.printStackTrace();
	    }

	    return idGenere;
	}
	
	// ---  UPDATE --- //
	public static void updateReservation(Reservation uneReservation) {
		String req = "UPDATE reservation SET nb_perso = ?, date_debut = ?, date_fin = ?, etat_res = ?, id_c = ?, ref_hab = ?"
					+ " WHERE ref_res = ?";
		
		try {
			uneBdd.seConnecter();
			
			PreparedStatement pst = uneBdd.getMaConnexion().prepareStatement(req);
			
			pst.setInt(1, uneReservation.getNb_perso());
			pst.setString(2, uneReservation.getDate_debut());
			pst.setString(3, uneReservation.getDate_fin());
			pst.setString(4, uneReservation.getEtat_res());
			pst.setInt(5, uneReservation.getId_c());
			pst.setInt(6, uneReservation.getRef_hab());
			pst.setInt(7,uneReservation.getRef_res());
			
			pst.executeUpdate();
			pst.close();
			uneBdd.seDeconnecter();
			
			System.out.println("Mise à jour réussie pour la réservation : " + uneReservation.getRef_hab());
			
		}catch(SQLException e) {
			System.err.println("Erreur SQl : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// ---  DELETE --- //
	public static void deleteReservation(int refRes) {
		String requete = "DELETE FROM reservation WHERE ref_res ='"+refRes+"';";
		executerRequete(requete);
	}
	
	// ---  SELECT ALL --- //
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
	
	// ---  SELECT WHERE --- //
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
	
	// ---  ANNULER RESERVATION --- //
	public static void annulerReservation(int ref_res) {
		String reqUpdate = "UPDATE reservation SET etat_res = 'Annulee' WHERE ref_res = ?";
		String reqDelete = "DELETE FROM reservation WHERE ref_res = ?";
		try {
		uneBdd.seConnecter();
		
		//update
		PreparedStatement pstU = uneBdd.getMaConnexion().prepareStatement(reqUpdate);
		pstU.setInt(1, ref_res);
		pstU.executeUpdate();
		pstU.close();
		
		//delete
		PreparedStatement pstD = uneBdd.getMaConnexion().prepareStatement(reqDelete);
		pstD.setInt(1, ref_res);
		pstD.executeUpdate();
		pstD.close();
		
		uneBdd.seDeconnecter();
		System.out.println("Réussite annulation réservation : " + ref_res);
		
		}catch(SQLException e) {
			System.out.println("Erreur SQl : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	


	

	
	
	
/*****************************************************     METHODES COMMUNES     *********************************************************************/
	
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


