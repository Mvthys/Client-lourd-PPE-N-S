package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.Maison;
import controleur.Proprietaire;
import controleur.Tableau;

public class PanelMaisons extends PanelPrincipal implements ActionListener {
	
	private JPanel panelForm = new JPanel();
	private JTextField txtAdresse = new JTextField();
	private JTextField txtCp = new JTextField();
	private JTextField txtVille = new JTextField();
	private JTextField txtTarifBas = new JTextField();
	private JTextField txtTarifMoy = new JTextField();
	private JTextField txtTarifHaut = new JTextField();
	private JTextField txtSurface = new JTextField();
	private static JComboBox<String> txtIdProprietaire = new JComboBox<String>();
	private JTextField txtDescription = new JTextField();
	private JTextField txtTitre = new JTextField();
	private JTextField txtCapacite = new JTextField();
	private JTextField txtCarac = new JTextField();
	
	
	private JButton btnAnnuler = new JButton("annuler");
	private JButton btnValider = new JButton("valider");
	
	private JTable tableHabitations;
	private JScrollPane scrollHabitations;
	private Tableau unTableau;
	
	private JPanel panelFiltre = new JPanel();
	private JTextField txtFiltre = new JTextField();
	private JButton btFiltrer = new JButton("filtrer");
	
	private JButton btSupprimer = new JButton("Supprimer");
	private JButton btModifier = new JButton("Modifier");
	
	private JLabel lbNbHabitations = new JLabel();

	public PanelMaisons(String titre) {
		super(titre);
		// TODO Auto-generated constructor stub
		this.panelFiltre.setBounds(400,40,500,20);
		this.panelFiltre.setBackground(new Color(242,242,242));
		this.panelFiltre.setLayout(new GridLayout(1,3, 5, 5));
		this.panelFiltre.add(new JLabel("Filtrer les maisons par :"));
		this.panelFiltre.add(this.txtFiltre);
		this.panelFiltre.add(this.btFiltrer);
		
		this.add(this.panelFiltre);
		
		this.panelForm.setBounds(10,80,300,400);
		this.panelForm.setBackground(new Color(242,242,242));
		this.panelForm.setLayout(null);
		this.panelForm.setLayout(new GridLayout(14,2, 5, 5));
		
		this.panelForm.add(new JLabel("Adresse : "));
		this.panelForm.add(this.txtAdresse);
		this.panelForm.add(new JLabel("Code Postal : "));
		this.panelForm.add(this.txtCp);
		this.panelForm.add(new JLabel("Ville : "));
		this.panelForm.add(this.txtVille);
		this.panelForm.add(new JLabel("Tarif bas : "));
		this.panelForm.add(this.txtTarifBas);
		this.panelForm.add(new JLabel("Tarif moyen : "));
		this.panelForm.add(this.txtTarifMoy);
		this.panelForm.add(new JLabel("Tarif haut : "));
		this.panelForm.add(this.txtTarifHaut);
		this.panelForm.add(new JLabel("Surface : "));
		this.panelForm.add(this.txtSurface);
		this.panelForm.add(new JLabel("ID proprietaire : "));
		this.panelForm.add(txtIdProprietaire);
		this.panelForm.add(new JLabel("Description : "));
		this.panelForm.add(this.txtDescription);
		this.panelForm.add(new JLabel("Titre : "));
		this.panelForm.add(this.txtTitre);
		this.panelForm.add(new JLabel("Capacite : "));
		this.panelForm.add(this.txtCapacite);
		this.panelForm.add(new JLabel("Caratéristiques : "));
		this.panelForm.add(this.txtCarac);
		
		this.panelForm.add(this.btnAnnuler);
		this.panelForm.add(this.btnValider);
		this.panelForm.add(this.btSupprimer);
		this.panelForm.add(this.btModifier);
		
		this.btSupprimer.setEnabled(false);
		this.btModifier.setEnabled(false);
		
		PanelMaisons.actualiserProprietaires();
		
		this.add(this.panelForm);
		
		
		//placement JTable
		String nomsColonnes[] = {"ID","Type","Adresse","Cp","Ville","TarifBas","TarifMoy","TarifHaut","Surface",
				"ID Proprietaire","Description","Titre","Capacite","Caractéristiques"};
		this.unTableau = new Tableau(this.obtenirDonnees(""), nomsColonnes);
		this.tableHabitations = new JTable(this.unTableau);
		this.scrollHabitations = new JScrollPane(this.tableHabitations);
		this.scrollHabitations.setBounds(400,80,500,400);
		this.scrollHabitations.setBackground(Color.BLACK);
		this.add(this.scrollHabitations);
		
		//Ajouter lbNbHab
		this.lbNbHabitations.setBounds(600,500,400,20);
		this.lbNbHabitations.setText("Nombre de maison : "+unTableau.getRowCount());
		this.add(this.lbNbHabitations);
		
		//rendre btn ecoutables
		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
		this.btFiltrer.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btModifier.addActionListener(this);
		
		this.tableHabitations.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				int numLigne = tableHabitations.getSelectedRow();
				int idHabitation = Integer.parseInt(tableHabitations.getValueAt(numLigne, 0).toString());
				txtAdresse.setText(unTableau.getValueAt(numLigne,2).toString());
				txtCp.setText(unTableau.getValueAt(numLigne,3).toString());
				txtVille.setText(unTableau.getValueAt(numLigne,4).toString());
				txtTarifBas.setText(unTableau.getValueAt(numLigne,5).toString());
				txtTarifMoy.setText(unTableau.getValueAt(numLigne,6).toString());
				txtTarifHaut.setText(unTableau.getValueAt(numLigne,7).toString());
				txtSurface.setText(unTableau.getValueAt(numLigne,8).toString());
				txtIdProprietaire.setSelectedItem(unTableau.getValueAt(numLigne,9).toString());
				txtDescription.setText(unTableau.getValueAt(numLigne,10).toString());
				txtTitre.setText(unTableau.getValueAt(numLigne,11).toString());
				txtCapacite.setText(unTableau.getValueAt(numLigne,12).toString());
				txtCarac.setText(unTableau.getValueAt(numLigne,13).toString());
				
				
				btModifier.setEnabled(true);
				btSupprimer.setEnabled(true);
				btnValider.setEnabled(false);
				
			}
		});
	}
	
	
	public static void actualiserProprietaires() {
		//remplir les client dans comboBox
			txtIdProprietaire.removeAllItems();
			txtIdProprietaire.addItem(" Selectionner ");
			ArrayList<Proprietaire> lesProprietaires = Controleur.selectAllProprietaires("");
			for(Proprietaire unProprietaire : lesProprietaires) {
					txtIdProprietaire.addItem(unProprietaire.getId_user() + "-" + unProprietaire.getNom() + " " + unProprietaire.getPrenom());
				}
		}
	
	public Object [] [] obtenirDonnees (String filtre){
		ArrayList<Maison> lesMaisons = Controleur.selectAllMaisons(filtre);
		Object [] [] matrice = new Object [lesMaisons.size()][15];
		int i = 0;
		for (Maison uneMaison : lesMaisons) {
			matrice[i][0] = uneMaison.getRef_hab();
			matrice[i][1] = uneMaison.getType();
			matrice[i][2] = uneMaison.getAdresse();
			matrice[i][3] = uneMaison.getCp();
			matrice[i][4] = uneMaison.getVille();
			matrice[i][5] = uneMaison.getTarifBas();
			matrice[i][6] = uneMaison.getTarifMoy();
			matrice[i][7] = uneMaison.getTarifHaut();
			matrice[i][8] = uneMaison.getSurface();
			matrice[i][9] = uneMaison.getIdProprietaire();
			matrice[i][10] = uneMaison.getDescription();
			matrice[i][11] = uneMaison.getTitre();
			matrice[i][12] = uneMaison.getCapacite();
			matrice[i][13] = uneMaison.getCaracteristique();
			i++;
		}
		return matrice;
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btnAnnuler) {
			this.viderChamps();
			this.btModifier.setEnabled(false);
			this.btSupprimer.setEnabled(false);
			this.btnValider.setEnabled(true);
		}else if(e.getSource() == this.btFiltrer) {
			this.unTableau.setDonnes(this.obtenirDonnees(this.txtFiltre.getText()));
			this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
		}else if (e.getSource() == this.btnValider){
			this.insertMaison();
		}else if(e.getSource() == this.btSupprimer) {
			this.deleteMaison();
		}else if(e.getSource() == this.btModifier) {
			this.updateMaison();
		}
	}
	
	public void deleteMaison() {
		int numLigne = this.tableHabitations.getSelectedRow();
		int refHab = Integer.parseInt(this.tableHabitations.getValueAt(numLigne,0).toString());
		int retour = JOptionPane.showConfirmDialog(this,"Voulez vous modifier cette habitation ?","Suppression habitation",
				JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			Controleur.deleteMaison(refHab);
			JOptionPane.showMessageDialog(this, "Suppression habitation effectuée avec succés");
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
		}
	}
	
	public void updateMaison() {
		int numLigne = this.tableHabitations.getSelectedRow();
		int refHab = Integer.parseInt(this.tableHabitations.getValueAt(numLigne, 0).toString());
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tarifBas = this.txtTarifBas.getText();
		String tarifMoy = this.txtTarifMoy.getText();
		String tarifHaut = this.txtTarifHaut.getText();
		String surface = this.txtSurface.getText();
		String chaine = txtIdProprietaire.getSelectedItem().toString();
		String tab[] = chaine.split("-");
		int idProprietaire = Integer.parseInt(tab[0]);
		String description = this.txtDescription.getText();
		String titre = this.txtTitre.getText();
		int capacite = Integer.parseInt(this.tableHabitations.getValueAt(numLigne, 12).toString());
		String carac = this.txtCarac.getText();
		
		if(adresse.equals("") || cp.equals("") || ville.equals("") || tarifBas.equals("") || 
				   tarifMoy.equals("") || tarifHaut.equals("") || surface.equals("") || txtIdProprietaire.getSelectedItem().toString().isEmpty()){
					JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
				}else {
					//instanciation nv Proprio
					Maison uneMaison = new Maison(refHab,"maison",adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire,
																description,titre,capacite,carac);
					//appel de la methode du controleur pour insérer habitation
					Controleur.updateMaison(uneMaison);
					JOptionPane.showMessageDialog(this,"Modification réussie de la maison");
					//actualiser l'affichage
					Object ligne [] = {uneMaison.getRef_hab(),"maison",adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire,description,titre,capacite,carac};
					this.unTableau.ajoutLigne(ligne);
					//vider les champs
					this.viderChamps();
					this.unTableau.setDonnes(this.obtenirDonnees(""));
					this.lbNbHabitations.setText("Nombre de maison : "+unTableau.getRowCount());
				}
		}
	
	public void viderChamps() {
		this.txtAdresse.setText("");
		this.txtCp.setText("");
		this.txtVille.setText("");
		this.txtTarifBas.setText("");
		this.txtTarifMoy.setText("");
		this.txtTarifHaut.setText("");
		this.txtSurface.setText("");
		this.txtDescription.setText("");
		this.txtTitre.setText("");
		this.txtCapacite.setText("");
		this.txtCarac.setText("");
	}
	
	public void insertMaison() {
	    // SUPPRIME les lignes qui appellent getSelectedRow et getValueAt
	    
	    String adresse = this.txtAdresse.getText();
	    String cp = this.txtCp.getText();
	    String ville = this.txtVille.getText();
	    String tarifBas = this.txtTarifBas.getText();
	    String tarifMoy = this.txtTarifMoy.getText();
	    String tarifHaut = this.txtTarifHaut.getText();
	    String surface = this.txtSurface.getText();
	    
	    // Gestion du ComboBox Propriétaire
	    String chaine = txtIdProprietaire.getSelectedItem().toString();
	    if (chaine.equals(" Selectionner ")) {
	        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un propriétaire");
	        return;
	    }
	    String tab[] = chaine.split("-");
	    int idProprietaire = Integer.parseInt(tab[0]);
	    
	    String description = this.txtDescription.getText();
	    String titre = this.txtTitre.getText();
	    
	    // CORRECTION : Récupérer la capacité depuis le champ texte, pas la table !
	    int capacite = 0;
	    try {
	        capacite = Integer.parseInt(this.txtCapacite.getText());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "La capacité doit être un nombre");
	        return;
	    }
	    
	    String carac = this.txtCarac.getText();

	    if(adresse.equals("") || cp.equals("") || ville.equals("") || tarifBas.equals("") || 
	       tarifMoy.equals("") || tarifHaut.equals("") || surface.equals("")){
	        JOptionPane.showMessageDialog(this,"Veuillez remplir tous les champs");
	    } else {
	        Maison uneMaison = new Maison("maison", adresse, cp, ville, tarifBas, tarifMoy, 
	                                      tarifHaut, surface, idProprietaire, description, titre, capacite, carac);
	        
	        int idGenere = Controleur.insertMaison(uneMaison);
	        
	        if (idGenere != 0) {
	            JOptionPane.showMessageDialog(this, "Insertion réussie de la maison");
	            this.unTableau.setDonnes(this.obtenirDonnees("")); // Actualise toute la table
	            this.viderChamps();
	        } else {
	            JOptionPane.showMessageDialog(this, "Erreur lors de l'insertion en base de données");
	        }
	    }
	}

}
