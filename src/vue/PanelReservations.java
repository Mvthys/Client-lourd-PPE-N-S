package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;

import javax.swing.BorderFactory;
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
import controleur.Reservation;
import controleur.Tableau;

public class PanelReservations extends PanelPrincipal implements ActionListener  {
	
	//créa élemen panel
	private JPanel panelForm = new JPanel();
	private JTextField txtDateRes = new JTextField();
	private JTextField txtNbPerso = new JTextField();
	private JTextField txtDateDebut = new JTextField();
	private JTextField txtDateFin = new JTextField();
	private JDateChooser dateDebut = new JDateChooser();
	private JDateChooser dateFin = new JDateChooser();
	private JComboBox<String> txtEtatRes = new JComboBox<String>();
	private static JComboBox<String> txtIdC = new JComboBox();
	private static JComboBox<String> txtRefHab = new JComboBox();
	private JLabel lbInsertReservation = new JLabel("Insérer/Modifier réservation");
	
	private JButton btnAnnuler = new JButton("Annuler");
	private JButton btnValider = new JButton("Valider");
	
	private JTable tableReservations;//Jtable
	private JScrollPane scrollReservations;
	private Tableau unTableau;
	
	private JPanel panelFiltre = new JPanel();
	private JTextField txtFiltre = new JTextField();
	private JButton btFiltrer = new JButton("Filtrer");
	
	private JButton btSupprimer = new JButton("Supprimer");
	private JButton btModifier = new JButton("Modifier");
	
	private JLabel lbNbReservations = new JLabel();

	
	
	public PanelReservations(String titre) {
		super(titre);
		//Ajout item combo box etat_res
		this.txtEtatRes.addItem("Sélectionner");
		this.txtEtatRes.addItem("En attente");
		this.txtEtatRes.addItem("Validee");
		this.txtEtatRes.addItem("Annulee");
		
		// TODO Auto-generated constructor stub
		//Placement panel filtrage
		this.panelFiltre.setBounds(400,70,500,20);
		this.panelFiltre.setBackground(new Color(242,242,242));
		this.panelFiltre.setLayout(new GridLayout(1,3, 5, 5));
		this.panelFiltre.add(new JLabel("Filtrer par : "));
		this.panelFiltre.add(this.txtFiltre);
		this.panelFiltre.add(this.btFiltrer);
		this.add(this.panelFiltre);
		
		//placement ajouter/modifier resas
		this.lbInsertReservation.setBounds(120,70,200,20);
		this.add(this.lbInsertReservation);
		
		//Placement panel insert
		this.panelForm.setBounds(10,100,350,400);
		this.panelForm.setBackground(new Color(242,242,242));
		this.panelForm.setLayout(null);
		this.panelForm.setLayout(new GridLayout(8,2, 5, 5));
		//Placement élements
		this.panelForm.add(new JLabel("Client : "));
		this.panelForm.add(this.txtIdC);
		this.panelForm.add(new JLabel("Réf habitation : "));
		this.panelForm.add(txtRefHab);
		this.panelForm.add(new JLabel("Nombre personnes : "));
		this.panelForm.add(this.txtNbPerso);
		this.panelForm.add(new JLabel("Début séjour : "));
		this.panelForm.add(this.dateDebut);
		this.panelForm.add(new JLabel("Fin séjour : "));
		this.panelForm.add(this.dateFin);
		this.panelForm.add(new JLabel("Etat : "));
		this.panelForm.add(this.txtEtatRes);
		//Ajout btns
		this.panelForm.add(this.btnAnnuler);
		this.panelForm.add(this.btnValider);
		this.panelForm.add(this.btSupprimer);
		this.panelForm.add(this.btModifier);
		//Ajout borders
		this.panelForm.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(10,10,10,10)
				));
		//Desactivation btns
		this.btModifier.setEnabled(false);
		this.btSupprimer.setEnabled(false);
		//Actualisation départ habitations et clients
		PanelReservations.actualiserHabitations();
		PanelReservations.actualiserClients();
		//AJOUT DU TOUT
		this.add(this.panelForm);
		
		
		//placement JTable
		String nomsColonnes[] = {"Réf","Date rés","Personnes","Début","Fin","Etat","ID client","Réf Hab"};
		this.unTableau = new Tableau(this.obtenirDonnees(""), nomsColonnes);
		this.tableReservations = new JTable(this.unTableau);
		this.scrollReservations = new JScrollPane(this.tableReservations);
		this.scrollReservations.setBounds(400,100,500,400);
		this.scrollReservations.setBackground(Color.BLACK);
		this.add(this.scrollReservations);
		
		//Ajouter lbNbResa
		this.lbNbReservations.setBounds(600,500,400,20);
		this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
		this.add(this.lbNbReservations);
		
		//rendre btn ecoutables
		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
		this.btFiltrer.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btModifier.addActionListener(this);
		
		
		
		
		/*******************************************************     MOUSE LISTENER     *******************************************************/
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
				txtNbPerso.setText(unTableau.getValueAt(numLigne,2).toString());
				
				//Gestion obtention des dates en objet DATE a partir String obtenu de la bdd
				SimpleDateFormat formatAffichage = new SimpleDateFormat("yyyy-MM-dd");
				try {
					String strDateDebut = unTableau.getValueAt(numLigne,3).toString();
					String strDateFin = unTableau.getValueAt(numLigne,4).toString();
					
					java.util.Date dateD = formatAffichage.parse(strDateDebut);
					java.util.Date dateF = formatAffichage.parse(strDateFin);
					
					dateDebut.setDate(dateD);
					dateFin.setDate(dateF);
					
				}catch(Exception ex) {
					System.out.println("Erreur conversion date : " + ex.getMessage());
					ex.printStackTrace();
					dateDebut.setDate(null);
					dateFin.setDate(null);
				}
				
				txtEtatRes.setSelectedItem(unTableau.getValueAt(numLigne,5).toString());
				String idC = unTableau.getValueAt(numLigne,6).toString();
				for(int i = 0; i<txtIdC.getItemCount(); i++) {
					String item = txtIdC.getItemAt(i);
					if(item.split("-")[0].equals(idC)) {
						txtIdC.setSelectedIndex(i);
						break;
					}
				}
				String refHab = unTableau.getValueAt(numLigne,7).toString();
				for(int i = 0; i<txtRefHab.getItemCount(); i++) {
					String item = txtRefHab.getItemAt(i);
					if(item.split("-")[0].equals(refHab)) {
						txtRefHab.setSelectedIndex(i);
						break;
					}
				}
				
				btModifier.setEnabled(true);
				btSupprimer.setEnabled(true);
				btnValider.setEnabled(false);
				
			}
		});
	}
	
	
	
	
	/*******************************************************     ACTUALISATION HAB     *******************************************************/
	public static void actualiserHabitations() {
		//remplir les client dans comboBox
			txtRefHab.removeAllItems();
			txtRefHab.addItem(" Sélectionner ");
			ArrayList<Habitation> lesHabitations = Controleur.selectAllHabitations("");
			for(Habitation uneHabitation : lesHabitations) {
					txtRefHab.addItem(uneHabitation.getRef_hab() + "-" + uneHabitation.getType() + " " + uneHabitation.getVille());
				}
		}
	
	
	
	
	/*******************************************************     ACTUALISATION CLIENTS     *******************************************************/
	public static void actualiserClients() {
		//remplir les client dans comboBox
			txtIdC.removeAllItems();
			txtIdC.addItem(" Sélectionner ");
			ArrayList<Client> lesClients = Controleur.selectAllClients("");
			for(Client unClient : lesClients) {
					txtIdC.addItem(unClient.getId_user() + "-" + unClient.getNom() + " " + unClient.getPrenom());
				}
		}
	
	
	
	
	/*******************************************************     OBTENIR DONNEES     *******************************************************/
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
	
	
	
	
	/*******************************************************     ACTION EVENTS     *******************************************************/
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
	
	
	
	
	/*******************************************************     VIDER CHAMPS     *******************************************************/
	public void viderChamps() {
		this.txtDateRes.setText("");
		this.txtNbPerso.setText("");
		this.txtIdC.setSelectedIndex(0);
		this.txtRefHab.setSelectedIndex(0);
		this.dateDebut.setDate(null);
		this.dateFin.setDate(null);
	}
	
	
	
	
	/*******************************************************     DELETE     *******************************************************/
	public void deleteReservation() {
		int numLigne = this.tableReservations.getSelectedRow();
		int refRes = Integer.parseInt(this.tableReservations.getValueAt(numLigne,0).toString());
		int retour = JOptionPane.showConfirmDialog(this,"Voulez vous supprimer cette réservation ?","Suppression réservation",
				JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			Controleur.annulerReservation(refRes);
			JOptionPane.showMessageDialog(this, "Suppression réservation effectuée avec succés");
			this.viderChamps();
			this.btModifier.setEnabled(false);
			this.btSupprimer.setEnabled(false);
			this.btnValider.setEnabled(true);
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
		}
	}
	
	
	
	
	/*******************************************************     UPDATE     *******************************************************/
	public void updateReservation(){
		int numLigne = this.tableReservations.getSelectedRow();
		int refRes = Integer.parseInt(this.tableReservations.getValueAt(numLigne,0).toString());
		String chaineC = this.txtIdC.getSelectedItem().toString();
		String chaineH = txtRefHab.getSelectedItem().toString();
		String nbPersoStr = this.txtNbPerso.getText();
		Date dateDebutD = this.dateDebut.getDate();
		Date dateFinD = this.dateFin.getDate();
		String etatRes = this.txtEtatRes.getSelectedItem().toString();
		
		if(chaineC.equals("Sélectionner") || chaineH.equals("Sélectionner") || nbPersoStr.equals("") || 
			dateDebutD == null || dateFinD == null || etatRes.equals("")) {
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}
		else if(!Controleur.regexChiffres(nbPersoStr)) {
			JOptionPane.showMessageDialog(this,"Format NOMBRE PERSONNES incorrect !");
			this.txtNbPerso.setText("");
		}
		//Gestion cohérence dates resa
		else if(dateDebutD.before(new Date())){
			JOptionPane.showMessageDialog(this,"Date début de séjour incohérente ! \n(réservation au moins 1 jour à l'avance)");
			this.dateDebut.setDate(null);
		}else if(dateFinD.before(dateDebutD)) {
			JOptionPane.showMessageDialog(this,"Date fin de séjour incohérente !");
			this.dateFin.setDate(null);
		}
		else {
			String tabC[] = chaineC.split("-");
			int idC = Integer.parseInt(tabC[0]);
			
			//gestion capacité max habitation
			String tabH[] = chaineH.split("-");
			int refHab = Integer.parseInt(tabH[0]);
			int nbPerso = Integer.parseInt(this.txtNbPerso.getText());
			int capaciteMax = Controleur.selectCapaciteHab(refHab);
			if(nbPerso > capaciteMax) {
				JOptionPane.showMessageDialog(this,"Nombre de personnes supérieur à la capacité de l'habitation !");
				this.txtNbPerso.setText("");
				return;
			}
			
			//Formatage dates pour insertion en base
			SimpleDateFormat formatageSQL = new SimpleDateFormat("yyyy-MM-dd");		
			String dateDebut = formatageSQL.format(dateDebutD);
			String dateFin = formatageSQL.format(dateFinD);
					
			//instanciation nv Resa
			Reservation uneReservation = new Reservation(refRes,"",nbPerso,dateDebut,dateFin,etatRes,idC,refHab);
			//appel de la methode du controleur pour insérer resa
			Controleur.updateReservation(uneReservation);
			JOptionPane.showMessageDialog(this,"Modification réussie de la réservation");
			//actualiser l'affichage
			Object ligne [] = {uneReservation.getRef_hab(),"",nbPerso,dateDebut,dateFin,etatRes,idC,refHab};
			//vider les champs
			this.viderChamps();
			this.btModifier.setEnabled(false);
			this.btSupprimer.setEnabled(false);
			this.btnAnnuler.setEnabled(true);
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
		}
	}
	
	
	
	
	/*******************************************************     INSERT     *******************************************************/
	public void insertReservation() {
		String chaineC = this.txtIdC.getSelectedItem().toString();
		String chaineH = txtRefHab.getSelectedItem().toString();
		String nbPersoStr = this.txtNbPerso.getText();
		Date dateDebutD = this.dateDebut.getDate();
		Date dateFinD = this.dateFin.getDate();
		String etatRes = this.txtEtatRes.getSelectedItem().toString();
		
		//verif saisis champs
		if(chaineC.trim().equals("Sélectionner") || chaineH.trim().equals("Sélectionner") || nbPersoStr.equals("") || 
			dateDebutD == null || dateFinD == null || etatRes.trim().equals("Sélectionner")) {
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}
		else if(!Controleur.regexChiffres(nbPersoStr)) {
			JOptionPane.showMessageDialog(this,"Format NOMBRE PERSONNES incorrect !");
			this.txtNbPerso.setText("");
		}
		//Gestion cohérence dates resa
		else if(dateDebutD.before(new Date())){
			JOptionPane.showMessageDialog(this,"Date début de séjour incohérente ! \n(réservation au moins 1 jour à l'avance)");
			this.dateDebut.setDate(null);
		}
		else if(dateFinD.before(dateDebutD)) {
			JOptionPane.showMessageDialog(this,"Date fin de séjour incohérente !");
			this.dateFin.setDate(null);
		}
		else {
			int nbPerso = Integer.parseInt(this.txtNbPerso.getText());
			
			String tabC[] = chaineC.split("-");
			int idC = Integer.parseInt(tabC[0]);
			
			//gestion capacité max habitation
			String tabH[] = chaineH.split("-");
			int refHab = Integer.parseInt(tabH[0]);
			int capaciteMax = Controleur.selectCapaciteHab(refHab);
			if(nbPerso > capaciteMax) {
				JOptionPane.showMessageDialog(this,"Nombre de personnes supérieur à la capacité de l'habitation !");
				this.txtNbPerso.setText("");
				return;
			}
			
			//Formatage dates pour insertion en base
			SimpleDateFormat formatageSQL = new SimpleDateFormat("yyyy-MM-dd");
			String dateDebut = formatageSQL.format(dateDebutD);
			String dateFin = formatageSQL.format(dateFinD);
			
			//instanciation nv Resa
			Reservation uneReservation = new Reservation("",nbPerso,dateDebut,dateFin,etatRes,idC,refHab);
			//appel de la methode du controleur pour insérer habitation
			int idGenere = Controleur.insertReservation(uneReservation);
			JOptionPane.showMessageDialog(this,"Insertion réussie de la réservation");
			//actualiser l'affichage
			Object ligne [] = {idGenere,"",nbPerso,dateDebut,dateFin,etatRes,idC,refHab};
			//vider les champs
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbReservations.setText("Nombre de réservations : "+unTableau.getRowCount());
		}
	}
}
