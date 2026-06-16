package controleur;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modele.Modele;

public class Controleur {
	
	//Admin
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

	
	//Habitations
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
	public static int selectCapaciteHab(int ref_hab) {
		return Modele.selectCapaciteHab(ref_hab);
	}
	
	
	//Reservations
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
	public static void annulerReservation(int ref_res) {
		Modele.annulerReservation(ref_res);
	}
	
	
	//Maisons
	public static int insertMaison(Maison uneMaison) {
		return Modele.insertMaison(uneMaison);
	}
	public static Maison selectWhereMaison(int ref_hab) {
		return Modele.selectWhereMaison(ref_hab);
	}
	public static ArrayList<Maison> selectAllMaisons(String filtre) {
		return Modele.selectAllMaisons(filtre);
	}
	public static void deleteMaison(int refHab) {
		Modele.deleteMaison(refHab);
	}
	public static void updateMaison (Maison uneMaison){
		Modele.updateMaison(uneMaison);
	}
	
	
	//Appartement
	public static int insertAppartement(Appartement unAppartement) {
		return Modele.insertAppartement(unAppartement);
	}
	public static Appartement selectWhereAppartement(int ref_hab) {
		return Modele.selectWhereAppartement(ref_hab);
	}
	public static ArrayList<Appartement> selectAllAppartements(String filtre) {
		return Modele.selectAllAppartements(filtre);
	}
	public static void deleteAppartement(int refHab) {
		Modele.deleteAppartement(refHab);
	}
	public static void updateAppartement (Appartement unAppartement){
		Modele.updateAppartement(unAppartement);
	}
	
	
	//Photos
	public static void insertPhotos(int ref_hab, String url_photo, int is_principal) {
		Modele.insertPhotos(ref_hab, url_photo, is_principal);
	}
	
	public static void deletePhotos(int ref_hab) {
		Modele.deletePhotos(ref_hab);
	}
	
	
	
	
	
	/********************************************************     AUTRES METHODES     ********************************************************/
	
	public static int selectCountUtilisateur(String role) {
		return Modele.selectCountUtilisateur(role);
	}
	public static int selectCount(String table) {
		return Modele.selectCount(table);
	}
	
	//REGEXS
	public static boolean regexNomPrenom (String chaine) {
		Pattern regex = Pattern.compile("^\\p{L}+[\\p{L} '-]*$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexEmail(String chaine) {
		Pattern regex = Pattern.compile("^[\\p{L}\\d._-]+@[\\p{L}\\d.-]+\\.[\\p{L}]{2,6}$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexMdp(String chaine) {
		Pattern regex = Pattern.compile("^[\\d\\p{L},?.;$!@_-]+$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexAdresse(String chaine) {
		Pattern regex = Pattern.compile("^[\\d]{1,4} [\\p{L} '-]+$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexCP(String chaine) {
		Pattern regex = Pattern.compile("^\\d{5}$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexVille(String chaine) {
		Pattern regex = Pattern.compile("^[\\p{L} '-]+$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexTel(String chaine) {
		Pattern regex = Pattern.compile("^\\d{10}$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexRIB(String chaine) {
		Pattern regex = Pattern.compile("^FR\\d{2}[\\dA-Z]{23}$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexTarifs(String chaine) {
		Pattern regex = Pattern.compile("^\\d+([.,]\\d{0,2})?$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexChiffres(String chaine) {
		Pattern regex = Pattern.compile("^\\d+?$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
	public static boolean regexTypeAppart(String chaine) {
		Pattern regex = Pattern.compile("^[\\dA-Za-z]{1,3}$");
		Matcher matcher = regex.matcher(chaine);
		return matcher.matches();
	}
		

}
