package controleur;

public class Proprietaire {
	
	int id_p;
	String nom,prenom,email,mdp,adresse,cp,ville,tel,rib;
	
	public Proprietaire(int id_p,String nom,String prenom,String email,String mdp,String adresse,String cp,
				  String ville,String tel,String rib){
		super();
		this.id_p = id_p;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.tel = tel;
		this.rib = rib;
	}
	public Proprietaire(String nom,String prenom,String email,String mdp,String adresse,String cp,
			  String ville,String tel,String rib){
		super();
		this.id_p = 0;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.tel = tel;
		this.rib = rib;
	}
	
	
	public int getId_p() {
		return id_p;
	}
	public void setId_c(int id_c) {
		this.id_p = id_p;
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
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getRib() {
		return rib;
	}
	public void setRib(String rib) {
		this.rib = rib;
	}

}
