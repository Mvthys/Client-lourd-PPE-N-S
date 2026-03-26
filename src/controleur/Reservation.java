package controleur;

public class Reservation {
	
	private int ref_res,nb_perso,id_c,ref_hab;
	private float prix;
	private String date_res,date_debut,date_fin,etat_res;
	
	
	public Reservation(int ref_res,String date_res,int nb_perso, String date_debut,String date_fin,String etat_res,
					   int id_c, int ref_hab, float prix) {
		this.ref_res = ref_res;
		this.date_res = date_res;
		this.nb_perso = nb_perso;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.etat_res = etat_res;
		this.id_c = id_c;
		this.ref_hab = ref_hab;
		this.prix = prix;
	}
	public Reservation(String date_res,int nb_perso, String date_debut,String date_fin,String etat_res,
			   		   int id_c, int ref_hab, float prix) {
		this.ref_res = 0;
		this.date_res = date_res;
		this.nb_perso = nb_perso;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.etat_res = etat_res;
		this.id_c = id_c;
		this.ref_hab = ref_hab;
		this.prix = prix;
	}
	
	
	public int getRef_res() {
		return ref_res;
	}
	public void setRef_res(int ref_res) {
		this.ref_res = ref_res;
	}
	
	public String getDate_res() {
		return date_res;
	}
	public void setDate_res(String date_res) {
		this.date_res = date_res;
	}
	
	public int getNb_perso() {
		return nb_perso;
	}
	public void setNb_perso(int nb_perso) {
		this.nb_perso = nb_perso;
	}
	
	public String getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}
	
	public String getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}
	
	public String getEtat_res() {
		return etat_res;
	}
	public void setEtat_res(String etat_res) {
		this.etat_res = etat_res;
	}
	
	public int getId_c() {
		return id_c;
	}
	public void setId_c(int id_c) {
		this.id_c = id_c;
	}
	
	public int getRef_hab() {
		return ref_hab;
	}
	public void setRef_hab(int ref_hab) {
		this.ref_hab = ref_hab;
	}
	
	public float getPrix() {
		return prix;
	}
	public void setPrix(float prix) {
		this.prix = prix;
	}

}
