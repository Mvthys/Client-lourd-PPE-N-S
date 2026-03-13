package controleur;

public class Contrat {
	
	private int ref_c,idProprietaire,ref_hab;
	private String status,annee_sign,annee_fin;
	
	public Contrat(int ref_c, int idProprietaire, int ref_hab, String status, String annee_sign,
			String annee_fin) {
		super();
		this.ref_c = ref_c;
		this.idProprietaire = idProprietaire;
		this.ref_hab = ref_hab;
		this.status = status;
		this.annee_sign = annee_sign;
		this.annee_fin = annee_fin;
	}
	
	public Contrat(int idProprietaire, int ref_hab, String status, String annee_sign,
			String annee_fin) {
		super();
		this.ref_c = 0;
		this.idProprietaire = idProprietaire;
		this.ref_hab = ref_hab;
		this.status = status;
		this.annee_sign = annee_sign;
		this.annee_fin = annee_fin;
	}

	public int getRef_c() {
		return ref_c;
	}

	public void setRef_c(int ref_c) {
		this.ref_c = ref_c;
	}

	public int getIdProprietaire() {
		return idProprietaire;
	}

	public void setIdProprietaire(int idProprietaire) {
		this.idProprietaire = idProprietaire;
	}

	public int getRef_hab() {
		return ref_hab;
	}

	public void setRef_hab(int ref_hab) {
		this.ref_hab = ref_hab;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAnnee_sign() {
		return annee_sign;
	}

	public void setAnnee_sign(String annee_sign) {
		this.annee_sign = annee_sign;
	}

	public String getAnnee_fin() {
		return annee_fin;
	}

	public void setAnnee_fin(String annee_fin) {
		this.annee_fin = annee_fin;
	}
	

}
