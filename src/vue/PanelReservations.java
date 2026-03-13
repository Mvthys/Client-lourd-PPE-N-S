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
import controleur.Habitation;
import controleur.Proprietaire;
import controleur.Reservation;
import controleur.Tableau;

public class PanelReservations extends PanelPrincipal implements ActionListener  {
	
	private JPanel panelForm = new JPanel();
	private JTextField txtDateRes = new JTextField();
	private JTextField txtNbPerso = new JTextField();
	private JTextField txtDateDebut = new JTextField();
	private JTextField txtDateFin = new JTextField();
	private JComboBox<String> txtEtatRes = new JComboBox<String>();
	private JTextField txtIdC = new JTextField();
	private static JComboBox<String> txtRefHab = new JComboBox();
	
	private JButton btnAnnuler = new JButton("annuler");
	private JButton btnValider = new JButton("valider");
	
	private JTable tableReservations;
	private JScrollPane scrollReservations;
	private Tableau unTableau;
	
	private JPanel panelFiltre = new JPanel();
	private JTextField txtFiltre = new JTextField();
	private JButton btFiltrer = new JButton("filtrer");
	
	private JButton btSupprimer = new JButton("Supprimer");
	private JButton btModifier = new JButton("Modifier");
	
	private JLabel lbNbReservations = new JLabel();

	public PanelReservations(String titre) {
		super(titre);
		this.txtEtatRes.addItem("En attente");
		this.txtEtatRes.addItem("Validee");
		this.txtEtatRes.addItem("Annulee");
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
		this.panelForm.setLayout(new GridLayout(9,2, 5, 5));
		
		this.panelForm.add(new JLabel("Date réservation : "));
		this.panelForm.add(this.txtDateRes);
		this.panelForm.add(new JLabel("Nombre personnes : "));
		this.panelForm.add(this.txtNbPerso);
		this.panelForm.add(new JLabel("Début : "));
		this.panelForm.add(this.txtDateDebut);
		this.panelForm.add(new JLabel("Fin : "));
		this.panelForm.add(this.txtDateFin);
		this.panelForm.add(new JLabel("Etat : "));
		this.panelForm.add(this.txtEtatRes);
		this.panelForm.add(new JLabel("ID client : "));
		this.panelForm.add(this.txtIdC);
		this.panelForm.add(new JLabel("Réf habitation : "));
		this.panelForm.add(txtRefHab);
		
		this.panelForm.add(this.btnAnnuler);
		this.panelForm.add(this.btnValider);
		this.panelForm.add(this.btSupprimer);
		this.panelForm.add(this.btModifier);
		
		this.btModifier.setEnabled(false);
		this.btSupprimer.setEnabled(false);
		
		PanelReservations.actualiserHabitations();
		
		this.add(this.panelForm);
		
		
		//placement JTable
		String nomsColonnes[] = {"Réf","Date réservation","Nombre personnes","Début","Fin","Etat","ID client","Réf Habitation"};
		this.unTableau = new Tableau(this.obtenirDonnees(""), nomsColonnes);
		this.tableReservations = new JTable(this.unTableau);
		this.scrollReservations = new JScrollPane(this.tableReservations);
		this.scrollReservations.setBounds(400,80,500,400);
		this.scrollReservations.setBackground(Color.BLACK);
		this.add(this.scrollReservations);
		
		//Ajouter lbNbHab
		this.lbNbReservations.setBounds(600,500,400,20);
		this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
		this.add(this.lbNbReservations);
		
		//rendre btn ecoutables
		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
		this.btFiltrer.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btModifier.addActionListener(this);
		
		this.tableReservations.addMouseListener(new MouseListener() {
			
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
				int numLigne = tableReservations.getSelectedRow();
				txtDateRes.setText(unTableau.getValueAt(numLigne,1).toString());
				txtNbPerso.setText(unTableau.getValueAt(numLigne,2).toString());
				txtDateDebut.setText(unTableau.getValueAt(numLigne,3).toString());
				txtDateFin.setText(unTableau.getValueAt(numLigne,4).toString());
				txtEtatRes.setSelectedItem(unTableau.getValueAt(numLigne,5).toString());
				txtIdC.setText(unTableau.getValueAt(numLigne,6).toString());
				txtRefHab.setSelectedItem(unTableau.getValueAt(numLigne,7).toString());
				
				btModifier.setEnabled(true);
				btSupprimer.setEnabled(true);
				btnValider.setEnabled(false);
				
			}
		});
	}
	
	public static void actualiserHabitations() {
		//remplir les client dans comboBox
			txtRefHab.removeAllItems();
			txtRefHab.addItem(" Sélectionner ");
			ArrayList<Habitation> lesHabitations = Controleur.selectAllHabitations("");
			for(Habitation uneHabitation : lesHabitations) {
					txtRefHab.addItem(uneHabitation.getRef_hab() + "-" + uneHabitation.getType() + " " + uneHabitation.getVille());
				}
		}
	
	public Object [] [] obtenirDonnees (String filtre){
		ArrayList<Reservation> lesReservations = Controleur.selectAllReservations(filtre);
		Object [] [] matrice = new Object [lesReservations.size()][8];
		int i = 0;
		for (Reservation uneReservation : lesReservations) {
			matrice[i][0] = uneReservation.getRef_res();
			matrice[i][1] = uneReservation.getDate_res();
			matrice[i][2] = uneReservation.getNb_perso();
			matrice[i][3] = uneReservation.getDate_debut();
			matrice[i][4] = uneReservation.getDate_fin();
			matrice[i][5] = uneReservation.getEtat_res();
			matrice[i][6] = uneReservation.getId_c();
			matrice[i][7] = uneReservation.getRef_hab();
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
		}else if (e.getSource() == this.btnValider){
			this.insertReservation();
		}else if(e.getSource() == this.btFiltrer) {
			this.unTableau.setDonnes(this.obtenirDonnees(this.txtFiltre.getText()));
			this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
		}
		else if(e.getSource() == this.btSupprimer) {
			this.deleteReservation();
		}else if(e.getSource() == this.btModifier) {
			this.updateReservation();
		}
	}
	
	
	public void deleteReservation() {
		int numLigne = this.tableReservations.getSelectedRow();
		int refRes = Integer.parseInt(this.tableReservations.getValueAt(numLigne,0).toString());
		int retour = JOptionPane.showConfirmDialog(this,"Voulez vous supprimer cette réservation ?","Suppression réservation",
				JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			Controleur.deleteReservation(refRes);
			JOptionPane.showMessageDialog(this, "Suppression réservation effectuée avec succés");
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
		}
	}
	
	public void updateReservation(){
		int numLigne = this.tableReservations.getSelectedRow();
		int refRes = Integer.parseInt(this.tableReservations.getValueAt(numLigne,0).toString());
		String dateRes = this.txtDateRes.getText();
		int nbPerso = Integer.parseInt(this.txtNbPerso.getText());
		String dateDebut = this.txtDateDebut.getText();
		String dateFin = this.txtDateFin.getText();
		String etatRes = this.txtEtatRes.getSelectedItem().toString();
		int idC = Integer.parseInt(this.txtIdC.getText());
		String chaine = txtRefHab.getSelectedItem().toString();
		String tab[] = chaine.split("-");
		int refHab = Integer.parseInt(tab[0]);
		
		if(dateRes.equals("") || this.txtNbPerso.getText().isEmpty() || dateDebut.equals("") || dateFin.equals("") || 
				   etatRes.equals("") || this.txtIdC.getText().isEmpty() || txtRefHab.getSelectedItem().toString().isEmpty()) {
					JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
				}else {
					//instanciation nv Resa
					Reservation uneReservation = new Reservation(refRes,dateRes,nbPerso,dateDebut,dateFin,etatRes,idC,refHab);
					//appel de la methode du controleur pour insérer habitation
					Controleur.updateReservation(uneReservation);
					JOptionPane.showMessageDialog(this,"Modification réussie de la réservation");
					//actualiser l'affichage
					Object ligne [] = {uneReservation.getRef_hab(),dateRes,nbPerso,dateDebut,dateFin,etatRes,idC,refHab};
					this.unTableau.ajoutLigne(ligne);
					//vider les champs
					this.viderChamps();
					this.unTableau.setDonnes(this.obtenirDonnees(""));
					this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
				}
		
		
	}
	
	
	public void viderChamps() {
		this.txtDateRes.setText("");
		this.txtNbPerso.setText("");
		this.txtDateDebut.setText("");
		this.txtDateFin.setText("");
		this.txtIdC.setText("");
	}
	
	public void insertReservation() {
		String dateRes = this.txtDateRes.getText();
		int nbPerso = Integer.parseInt(this.txtNbPerso.getText());
		String dateDebut = this.txtDateDebut.getText();
		String dateFin = this.txtDateFin.getText();
		String etatRes = this.txtEtatRes.getSelectedItem().toString();
		int idC = Integer.parseInt(this.txtIdC.getText());
		String chaine = txtRefHab.getSelectedItem().toString();
		String tab[] = chaine.split("-");
		int refHab = Integer.parseInt(tab[0]);

		
		if(dateRes.equals("") || this.txtNbPerso.getText().isEmpty() || dateDebut.equals("") || dateFin.equals("") || 
		   etatRes.equals("") || this.txtIdC.getText().isEmpty() || txtRefHab.getSelectedItem().toString().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}else {
			//instanciation nv Resa
			Reservation uneReservation = new Reservation(dateRes,nbPerso,dateDebut,dateFin,etatRes,idC,refHab);
			//appel de la methode du controleur pour insérer habitation
			int idGenere = Controleur.insertReservation(uneReservation);
			JOptionPane.showMessageDialog(this,"Insertion réussie de la réservation");
			//actualiser l'affichage
			Object ligne [] = {uneReservation.getRef_res(),dateRes,nbPerso,dateDebut,dateFin,etatRes,idC,refHab};
			this.unTableau.ajoutLigne(ligne);
			//vider les champs
			this.viderChamps();
		}
	}
	

}
