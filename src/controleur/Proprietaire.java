package controleur;

public class Proprietaire extends Utilisateur {
	
	private String adresse,cp,ville,rib;
	
	public Proprietaire(int id_user,String nom,String prenom,String email,String mdp,String tel,String role,String adresse,String cp,
				  String ville,String rib){
		super(id_user,nom,prenom,email,mdp,tel,role);
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.rib = rib;
	}
	public Proprietaire(int id_user,String nom,String prenom,String email,String mdp,String tel,String adresse,String cp,
			  String ville,String rib){
	super(id_user,nom,prenom,email,mdp,tel);
	this.adresse = adresse;
	this.cp = cp;
	this.ville = ville;
	this.rib = rib;
}
	public Proprietaire(String nom,String prenom,String email,String mdp,String tel,String role,String adresse,String cp,
			  String ville,String rib){
		super(nom,prenom,email,mdp,tel,role);
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.rib = rib;
	}
	public Proprietaire(String nom,String prenom,String email,String mdp,String tel,String adresse,String cp,
			  String ville,String rib){
		super(nom,prenom,email,mdp,tel);
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.rib = rib;
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
	
	public String getRib() {
		return rib;
	}
	public void setRib(String rib) {
		this.rib = rib;
	}

}
