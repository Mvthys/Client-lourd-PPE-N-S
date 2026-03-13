package controleur;

public class Habitation {
	
	private int ref_hab,idProprietaire,capacite;
	String type,adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,description,titre;
	
	public Habitation(int ref_hab,String type,String adresse, String cp, String ville,String tarifBas,String tarifMoy, String tarifHaut, 
			String surface, int idProprietaire, String description, String titre, int capacite) {
		super();
		this.ref_hab = ref_hab;
		this.type = type;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.tarifBas = tarifBas;
		this.tarifMoy = tarifMoy;
		this.tarifHaut = tarifHaut;
		this.surface = surface;
		this.idProprietaire = idProprietaire;
		this.description = description;
		this.titre = titre;
		this.capacite = capacite;
		
	}
	public Habitation(String type,String adresse, String cp, String ville,String tarifBas,String tarifMoy, String tarifHaut, 
			String surface, int idProprietaire, String description, String titre, int capacite) {
		super();
		this.ref_hab = 0;
		this.type = type;
		this.adresse = adresse;
		this.cp = cp;
		this.ville = ville;
		this.tarifBas = tarifBas;
		this.tarifMoy = tarifMoy;
		this.tarifHaut = tarifHaut;
		this.surface = surface;
		this.idProprietaire = idProprietaire;
		this.description = description;
		this.titre = titre;
		this.capacite = capacite;
	}
	
	
	
	
	public int getRef_hab() {
		return ref_hab;
	}
	public void setRef_hab(int ref_hab) {
		this.ref_hab = ref_hab;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public String getTarifBas() {
		return tarifBas;
	}
	public void setTarifBas(String tarifBas) {
		this.tarifBas = tarifBas;
	}
	
	public String getTarifMoy() {
		return tarifMoy;
	}
	public void setTarifMoy(String tarifMoy) {
		this.tarifMoy = tarifMoy;
	}
	
	public String getTarifHaut() {
		return tarifHaut;
	}
	public void setTarifHaut(String tarifHaut) {
		this.tarifHaut = tarifHaut;
	}
	
	public String getSurface() {
		return surface;
	}
	public void setSurface(String surface) {
		this.surface = surface;
	}
	
	public int getIdProprietaire() {
		return idProprietaire;
	}
	public void setIdProprietaire(int idProprietaire) {
		this.idProprietaire = idProprietaire;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
}
