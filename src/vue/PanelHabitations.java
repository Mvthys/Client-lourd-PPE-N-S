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

import controleur.Client;
import controleur.Controleur;
import controleur.Habitation;
import controleur.Proprietaire;
import controleur.Tableau;

public class PanelHabitations extends PanelPrincipal implements ActionListener {
	
	private JPanel panelForm = new JPanel();
	private JComboBox<String> txtType = new JComboBox<String>();
	private JTextField txtAdresse = new JTextField();
	private JTextField txtCp = new JTextField();
	private JTextField txtVille = new JTextField();
	private JTextField txtTarifBas = new JTextField();
	private JTextField txtTarifMoy = new JTextField();
	private JTextField txtTarifHaut = new JTextField();
	private JTextField txtSurface = new JTextField();
	private static JComboBox<String> txtIdProprietaire = new JComboBox<String>();
	
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

	public PanelHabitations(String titre) {
		super(titre);
		this.txtType.addItem("Appartement");
		this.txtType.addItem("Maison");
		// TODO Auto-generated constructor stub
		this.panelFiltre.setBounds(400,40,500,20);
		this.panelFiltre.setBackground(new Color(242,242,242));
		this.panelFiltre.setLayout(new GridLayout(1,3, 5, 5));
		this.panelFiltre.add(new JLabel("Filtrer les habitations par :"));
		this.panelFiltre.add(this.txtFiltre);
		this.panelFiltre.add(this.btFiltrer);
		
		this.add(this.panelFiltre);
		
		this.panelForm.setBounds(10,80,300,400);
		this.panelForm.setBackground(new Color(242,242,242));
		this.panelForm.setLayout(null);
		this.panelForm.setLayout(new GridLayout(11,2, 5, 5));
		
		this.panelForm.add(new JLabel("Type : "));
		this.panelForm.add(this.txtType);
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
		this.panelForm.add(this.txtIdProprietaire);
		
		this.panelForm.add(this.btnAnnuler);
		this.panelForm.add(this.btnValider);
		this.panelForm.add(this.btSupprimer);
		this.panelForm.add(this.btModifier);
		
		this.btSupprimer.setEnabled(false);
		this.btModifier.setEnabled(false);
		
		PanelHabitations.actualiserProprietaires();
		
		this.add(this.panelForm);
		
		
		//placement JTable
		String nomsColonnes[] = {"ID","Type","Adresse","Cp","Ville","TarifBas","TarifMoy","TarifHaut","Surface",
				"ID Proprietaire"};
		this.unTableau = new Tableau(this.obtenirDonnees(""), nomsColonnes);
		this.tableHabitations = new JTable(this.unTableau);
		this.scrollHabitations = new JScrollPane(this.tableHabitations);
		this.scrollHabitations.setBounds(400,80,500,400);
		this.scrollHabitations.setBackground(Color.BLACK);
		this.add(this.scrollHabitations);
		
		//Ajouter lbNbHab
		this.lbNbHabitations.setBounds(600,500,400,20);
		this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
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
				txtType.setSelectedItem(unTableau.getValueAt(numLigne,1).toString());
				txtAdresse.setText(unTableau.getValueAt(numLigne,2).toString());
				txtCp.setText(unTableau.getValueAt(numLigne,3).toString());
				txtVille.setText(unTableau.getValueAt(numLigne,4).toString());
				txtTarifBas.setText(unTableau.getValueAt(numLigne,5).toString());
				txtTarifMoy.setText(unTableau.getValueAt(numLigne,6).toString());
				txtTarifHaut.setText(unTableau.getValueAt(numLigne,7).toString());
				txtSurface.setText(unTableau.getValueAt(numLigne,8).toString());
				txtIdProprietaire.setSelectedItem(unTableau.getValueAt(numLigne,9).toString());
				
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
		ArrayList<Habitation> lesHabitations = Controleur.selectAllHabitations(filtre);
		Object [] [] matrice = new Object [lesHabitations.size()][10];
		int i = 0;
		for (Habitation uneHabitation : lesHabitations) {
			matrice[i][0] = uneHabitation.getRef_hab();
			matrice[i][1] = uneHabitation.getType();
			matrice[i][2] = uneHabitation.getAdresse();
			matrice[i][3] = uneHabitation.getCp();
			matrice[i][4] = uneHabitation.getVille();
			matrice[i][5] = uneHabitation.getTarifBas();
			matrice[i][6] = uneHabitation.getTarifMoy();
			matrice[i][7] = uneHabitation.getTarifHaut();
			matrice[i][8] = uneHabitation.getSurface();
			matrice[i][9] = uneHabitation.getIdProprietaire();
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
			this.insertHabitation();
		}else if(e.getSource() == this.btSupprimer) {
			this.deleteHabitation();
		}else if(e.getSource() == this.btModifier) {
			this.updateHabitation();
		}
	}
	
	public void deleteHabitation() {
		int numLigne = this.tableHabitations.getSelectedRow();
		int refHab = Integer.parseInt(this.tableHabitations.getValueAt(numLigne,0).toString());
		int retour = JOptionPane.showConfirmDialog(this,"Voulez vous modifier cette habitation ?","Suppression habitation",
				JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			Controleur.deleteHabitation(refHab);
			JOptionPane.showMessageDialog(this, "Suppression habitation effectuée avec succés");
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
		}
	}
	
	public void updateHabitation() {
		int numLigne = this.tableHabitations.getSelectedRow();
		int refHab = Integer.parseInt(this.tableHabitations.getValueAt(numLigne, 0).toString());
		String type = this.txtType.getSelectedItem().toString();
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tarifBas = this.txtTarifBas.getText();
		String tarifMoy = this.txtTarifMoy.getText();
		String tarifHaut = this.txtTarifHaut.getText();
		String surface = this.txtSurface.getText();
		String chaine = this.txtIdProprietaire.getSelectedItem().toString();
		String tab[] = chaine.split("-");
		int idProprietaire = Integer.parseInt(tab[0]);
		
		if(type.equals("") || adresse.equals("") || cp.equals("") || ville.equals("") || tarifBas.equals("") || 
				   tarifMoy.equals("") || tarifHaut.equals("") || surface.equals("") || this.txtIdProprietaire.getSelectedItem().toString().isEmpty()){
					JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
				}else {
					//instanciation nv Proprio
					Habitation uneHabitation = new Habitation(refHab,type,adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire);
					//appel de la methode du controleur pour insérer habitation
					Controleur.updateHabitation(uneHabitation);
					JOptionPane.showMessageDialog(this,"Modification réussie de l'habitation");
					//actualiser l'affichage
					Object ligne [] = {uneHabitation.getRef_hab(),type,adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire};
					this.unTableau.ajoutLigne(ligne);
					//vider les champs
					this.viderChamps();
					this.unTableau.setDonnes(this.obtenirDonnees(""));
					this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
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
	}
	
	public void insertHabitation() {
		String type = this.txtType.getSelectedItem().toString();
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tarifBas = this.txtTarifBas.getText();
		String tarifMoy = this.txtTarifMoy.getText();
		String tarifHaut = this.txtTarifHaut.getText();
		String surface = this.txtSurface.getText();
		String chaine = this.txtIdProprietaire.getSelectedItem().toString();
		String tab[] = chaine.split("-");
		int idProprietaire = Integer.parseInt(tab[0]);

		
		if(type.equals("") || adresse.equals("") || cp.equals("") || ville.equals("") || tarifBas.equals("") || 
		   tarifMoy.equals("") || tarifHaut.equals("") || surface.equals("") || this.txtIdProprietaire.getSelectedItem().toString().isEmpty()){
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}else {
			//instanciation nv Proprio
			Habitation uneHabitation = new Habitation(type,adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire);
			//appel de la methode du controleur pour insérer habitation
			int idGenere = Controleur.insertHabitation(uneHabitation);
			JOptionPane.showMessageDialog(this,"Insertion réussie de l'habitation");
			//actualiser l'affichage
			Object ligne [] = {uneHabitation.getRef_hab(),type,adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire};
			this.unTableau.ajoutLigne(ligne);
			//vider les champs
			this.viderChamps();
		}
	}

}
