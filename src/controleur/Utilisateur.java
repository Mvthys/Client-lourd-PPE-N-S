package controleur;

public class Utilisateur {
    private int id_user;
    private String nom, prenom, email, mdp, tel, role;

    public Utilisateur(int id_user, String nom, String prenom, String email, String mdp, String tel, String role) {
    	super();
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.role = role;
    }
    public Utilisateur(int id_user, String nom, String prenom, String email, String mdp, String tel) {
    	super();
        this.id_user = id_user;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
    }
    
    public Utilisateur(String nom, String prenom, String email, String mdp, String tel, String role) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.role = role;
    }
    
    public Utilisateur(String nom, String prenom, String email, String mdp, String tel) {
        super();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
    }
    
    public int getId_user() {
		return id_user;
	}
    public void setId_user(int id_user) {
		this.id_user = id_user;
	}
    
    public String getNom() {
		return nom;
	}
    public void setNom(String nom) {
		this.nom = nom;
	}
    
    public String getPrenom() {
		return prenom;
	}
    public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
    
    public String getEmail() {
		return email;
	}
    public void setEmail(String email) {
		this.email = email;
	}
    
    public String getMdp() {
		return mdp;
	}
    public void setMdp(String mdp) {
		this.mdp = mdp;
	}
    
    public String getTel() {
		return tel;
	}
    public void setTel(String tel) {
		this.tel = tel;
	}
    
    public String getRole() {
		return role;
	}
    public void setRole(String role) {
		this.role = role;
	}
}

