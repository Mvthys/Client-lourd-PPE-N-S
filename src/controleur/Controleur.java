package controleur;

import java.util.ArrayList;

import modele.Modele;

public class Controleur {
	
	public static Utilisateur selectWhereAdmin(String email, String mdp) {
		return Modele.selectWhereAdmin(email,mdp);
	}
	
	
	//Utilisateur
	public static Utilisateur selectWhereUtilisateur(String email, String mdp) {
		return Modele.selectWhereUtilisateur(email,mdp);
	}
	
	
	//Client
	public static void insertClient(Client unClient) {
		Modele.insertClient(unClient);
	}
	public static Client selectWhereClient(String email) {
		return Modele.selectWhereClient(email);
	}
	public static ArrayList<Client> selectAllClients(String filtre) {
		return Modele.selectAllClients(filtre);
	}
	public static void deleteClient(int idClient) {
		Modele.deleteClient(idClient);
	}
	public static void updateClient(Client unClient) {
		Modele.updateClient(unClient);
	}
	
	
	
	
	//Proprio
	public static void insertProprietaire(Proprietaire unProprietaire) {
		Modele.insertProprietaire(unProprietaire);
	}
	public static Proprietaire selectWhereProprietaire(String email) {
		return Modele.selectWhereProprietaire(email);
	}
	public static ArrayList<Proprietaire> selectAllProprietaires(String filtre) {
		return Modele.selectAllProprietaires(filtre);
	}
	public static void deleteProprietaire(int idProprietaire) {
		Modele.deleteProprietaire(idProprietaire);
	}
	public static void updateProprietaire(Proprietaire unProprietaire) {
		Modele.updateProprietaire(unProprietaire);
	}
	
	
	
	
	//habitations
	public static int insertHabitation(Habitation uneHabitation) {
		return Modele.insertHabitation(uneHabitation);
	}
	public static Habitation selectWhereHabitation(int ref_hab) {
		return Modele.selectWhereHabitation(ref_hab);
	}
	public static ArrayList<Habitation> selectAllHabitations(String filtre) {
		return Modele.selectAllHabitations(filtre);
	}
	public static void deleteHabitation(int refHab) {
		Modele.deleteHabitation(refHab);
	}
	public static void updateHabitation (Habitation uneHabitation){
		Modele.updateHabitation(uneHabitation);
	}
	
	
	
	//reservations
	public static int insertReservation(Reservation uneReservation) {
		return Modele.insertReservation(uneReservation);
	}
	public static Reservation selectWhereReservation(int ref_res) {
		return Modele.selectWhereReservation(ref_res);
	}
	public static ArrayList<Reservation> selectAllReservations(String filtre) {
		return Modele.selectAllReservations(filtre);
	}
	public static void deleteReservation (int refRes) {
		Modele.deleteReservation(refRes);
	}
	public static void updateReservation(Reservation uneReservation) {
		Modele.updateReservation(uneReservation);
	}
	
	
	
	
	/********** Autres méthodes **********/
	public static int selectCountUtilisateur(String role) {
		return Modele.selectCountUtilisateur(role);
	}
	
	public static int selectCount(String table) {
		return Modele.selectCount(table);
	}


}
