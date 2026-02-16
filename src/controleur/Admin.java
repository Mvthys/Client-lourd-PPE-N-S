package controleur;

public class Admin {
	private int Id_a;
	private String nom_a,prenom_a,email_a,mdp_a,role_a;
	
	public Admin(int Id_a, String nom_a, String prenom_a, String email_a, String mdp_a, String role_a) {
		super();
		this.Id_a = Id_a; 
		this.nom_a = nom_a;
		this.prenom_a = prenom_a;
		this.email_a = email_a;
		this.mdp_a = mdp_a;
		this.role_a = role_a;
	}
	
	public Admin(String nom_a, String prenom_a, String email_a, String mdp_a, String role_a) {
		super();
		this.Id_a = 0;
		this.nom_a = nom_a;
		this.prenom_a = prenom_a;
		this.email_a = email_a;
		this.mdp_a = mdp_a;
		this.role_a = role_a;
	}

	public int getIdAdmin() {
		return Id_a;
	}

	public void setIdAdmin(int Id_a) {
		this.Id_a = Id_a;
	}

	public String getNom() {
		return nom_a;
	}

	public void setNom(String nom) {
		this.nom_a = nom;
	}

	public String getPrenom() {
		return prenom_a;
	}

	public void setPrenom(String prenom) {
		this.prenom_a = prenom;
	}

	public String getEmail() {
		return email_a;
	}

	public void setEmail(String email) {
		this.email_a = email;
	}

	public String getMdp() {
		return mdp_a;
	}

	public void setMdp(String mdp) {
		this.mdp_a = mdp;
	}

	public String getRole() {
		return role_a;
	}

	public void setRole(String role) {
		this.role_a = role;
	}
}
