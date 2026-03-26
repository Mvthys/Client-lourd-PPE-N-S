package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controleur.Proprietaire;
import controleur.Controleur;
import controleur.Tableau;

public class PanelProprietaires extends PanelPrincipal implements ActionListener {
	
	private JPanel panelForm = new JPanel();
	private JTextField txtNom = new JTextField();
	private JTextField txtPrenom = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTextField txtMdp = new JTextField();
	private JTextField txtAdresse = new JTextField();
	private JTextField txtCp = new JTextField();
	private JTextField txtVille = new JTextField();
	private JTextField txtTel = new JTextField();
	private JTextField txtRIB = new JTextField();
	
	private JButton btnAnnuler = new JButton("annuler");
	private JButton btnValider = new JButton("valider");
	
	private JTable tableProprietaires;
	private JScrollPane scrollProprietaires;
	private Tableau unTableau;
	
	private JPanel panelFiltre = new JPanel();
	private JTextField txtFiltre = new JTextField();
	private JButton btFiltrer = new JButton("filtrer");
	
	private JButton btSupprimer = new JButton("Supprimer");
	private JButton btModifier = new JButton("Modifier");
	
	private JLabel lbNbProprietaires = new JLabel();
	
	public PanelProprietaires(String titre) {
		super(titre);
		// TODO Auto-generated constructor stub
		this.panelFiltre.setBounds(400,40,500,20);
		this.panelFiltre.setBackground(new Color(242,242,242));
		this.panelFiltre.setLayout(new GridLayout(1,3, 5, 5));
		this.panelFiltre.add(new JLabel("Filtrer les proprietaires par :"));
		this.panelFiltre.add(this.txtFiltre);
		this.panelFiltre.add(this.btFiltrer);
		
		this.add(this.panelFiltre);
		
		this.panelForm.setBounds(10,80,300,400);
		this.panelForm.setBackground(new Color(242,242,242));
		this.panelForm.setLayout(null);
		this.panelForm.setLayout(new GridLayout(11,2, 5, 5));
		
		this.panelForm.add(new JLabel("Nom : "));
		this.panelForm.add(this.txtNom);
		this.panelForm.add(new JLabel("Prenom : "));
		this.panelForm.add(this.txtPrenom);
		this.panelForm.add(new JLabel("Email : "));
		this.panelForm.add(this.txtEmail);
		this.panelForm.add(new JLabel("Mdp : "));
		this.panelForm.add(this.txtMdp);
		this.panelForm.add(new JLabel("Adresse : "));
		this.panelForm.add(this.txtAdresse);
		this.panelForm.add(new JLabel("Code postal : "));
		this.panelForm.add(this.txtCp);
		this.panelForm.add(new JLabel("Ville : "));
		this.panelForm.add(this.txtVille);
		this.panelForm.add(new JLabel("Tel : "));
		this.panelForm.add(this.txtTel);
		this.panelForm.add(new JLabel("RIB : "));
		this.panelForm.add(this.txtRIB);
		
		this.panelForm.add(this.btnAnnuler);
		this.panelForm.add(this.btnValider);
		this.panelForm.add(this.btSupprimer);
		this.panelForm.add(this.btModifier);
		
		this.btSupprimer.setEnabled(false);
		this.btModifier.setEnabled(false);
		
		this.add(this.panelForm);
		
		
		//placement JTable
		String nomsColonnes[] = {"ID","Nom","Prenom","Email","Mdp","Adresse","CP","Ville","Tel","RIB"};
		this.unTableau = new Tableau(this.obtenirDonnees(""), nomsColonnes);
		this.tableProprietaires = new JTable(this.unTableau);
		this.scrollProprietaires = new JScrollPane(this.tableProprietaires);
		this.scrollProprietaires.setBounds(400,80,500,400);
		this.scrollProprietaires.setBackground(Color.BLACK);
		this.add(this.scrollProprietaires);
		
		//Ajouter lbNbProprio
		this.lbNbProprietaires.setBounds(600,500,400,20);
		this.lbNbProprietaires.setText("Nombre de propriétaires : "+unTableau.getRowCount());
		this.add(this.lbNbProprietaires);
		
		//rendre btn ecoutables
		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
		this.btFiltrer.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btModifier.addActionListener(this);
		
		this.tableProprietaires.addMouseListener(new MouseListener() {
			
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
				int numLigne = tableProprietaires.getSelectedRow();
				txtNom.setText(unTableau.getValueAt(numLigne,1).toString());
				txtPrenom.setText(unTableau.getValueAt(numLigne,2).toString());
				txtEmail.setText(unTableau.getValueAt(numLigne,3).toString());
				txtMdp.setText(unTableau.getValueAt(numLigne,4).toString());
				txtAdresse.setText(unTableau.getValueAt(numLigne,5).toString());
				txtCp.setText(unTableau.getValueAt(numLigne,6).toString());
				txtVille.setText(unTableau.getValueAt(numLigne,7).toString());
				txtTel.setText(unTableau.getValueAt(numLigne,8).toString());
				txtRIB.setText(unTableau.getValueAt(numLigne,9).toString());
				
				btModifier.setEnabled(true);
				btSupprimer.setEnabled(true);
				btnValider.setEnabled(false);
				
			}
		});
		
	}

	
	public Object [] [] obtenirDonnees (String filtre){
		ArrayList<Proprietaire> lesProprietaires = Controleur.selectAllProprietaires(filtre);
		Object [] [] matrice = new Object [lesProprietaires.size()][10];
		int i = 0;
		for (Proprietaire unProprietaire : lesProprietaires) {
			matrice[i][0] = unProprietaire.getId_user();
			matrice[i][1] = unProprietaire.getNom();
			matrice[i][2] = unProprietaire.getPrenom();
			matrice[i][3] = unProprietaire.getEmail();
			matrice[i][4] = unProprietaire.getMdp();
			matrice[i][5] = unProprietaire.getAdresse();
			matrice[i][6] = unProprietaire.getCp();
			matrice[i][7] = unProprietaire.getVille();
			matrice[i][8] = unProprietaire.getTel();
			matrice[i][9] = unProprietaire.getRib();
			i++;
		}
		return matrice;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btnAnnuler) {
			this.viderChamps();
			btModifier.setEnabled(false);
			btSupprimer.setEnabled(false);
			btnValider.setEnabled(true);
		}else if (e.getSource() == this.btnValider){
			this.insertProprietaire();
		}else if(e.getSource() == this.btFiltrer) {
			this.unTableau.setDonnes(this.obtenirDonnees(this.txtFiltre.getText()));
			this.lbNbProprietaires.setText("Nombre de Proprietaires : "+unTableau.getRowCount());
		}else if(e.getSource() == this.btSupprimer) {
			this.deleteProprietaire();
		}else if(e.getSource() == this.btModifier) {
			this.updateProprietaire();
		}
	}
	
	
	public void deleteProprietaire() {
		int numLigne = this.tableProprietaires.getSelectedRow();
		int idProprietaire = Integer.parseInt(this.tableProprietaires.getValueAt(numLigne,0).toString());
		int retour = JOptionPane.showConfirmDialog(this,"Voulez vous supprimer ce proprietaire ?","suppression proprietaire",
				 JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			Controleur.deleteProprietaire(idProprietaire);
			JOptionPane.showMessageDialog(this,"Proprietaire supprimé avec succés.");
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbProprietaires.setText("Nombre de Proprietaires : "+unTableau.getRowCount());
		}
	}
	
	public void updateProprietaire() {
		int numLigne = this.tableProprietaires.getSelectedRow();
		int idProprietaire = Integer.parseInt(this.tableProprietaires.getValueAt(numLigne,0).toString());
		String nom = this.txtNom.getText();
		String prenom = this.txtPrenom.getText();
		String email = this.txtEmail.getText();
		String mdp = this.txtMdp.getText();
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tel = this.txtTel.getText();
		String RIB = this.txtRIB.getText();
		
		if(nom.equals("") || prenom.equals("") || email.equals("") || mdp.equals("") || adresse.equals("") || cp.equals("")
				   || ville.equals("") || tel.equals("") || RIB.equals("")) {
					JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
				}else {
					//instanciation nv Proprio
					Proprietaire unProprietaire = new Proprietaire(idProprietaire,nom,prenom,email,mdp,tel,adresse,cp,ville,tel,RIB);
					//appel de la methode du controleur pour insérer proprio
					Controleur.updateProprietaire(unProprietaire);
					JOptionPane.showMessageDialog(this,"Modification réussie du proprietaire");
					this.viderChamps();
					this.unTableau.setDonnes(this.obtenirDonnees(""));
					this.lbNbProprietaires.setText("Nombre de Proprietaires : "+unTableau.getRowCount());
				}
		}
	
	public void viderChamps() {
		this.txtNom.setText("");
		this.txtPrenom.setText("");
		this.txtEmail.setText("");
		this.txtMdp.setText("");
		this.txtAdresse.setText("");
		this.txtCp.setText("");
		this.txtVille.setText("");
		this.txtTel.setText("");
		this.txtRIB.setText("");
	}
	
	public void insertProprietaire() {
		String nom = this.txtNom.getText();
		String prenom = this.txtPrenom.getText();
		String email = this.txtEmail.getText();
		String mdp = this.txtMdp.getText();
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tel = this.txtTel.getText();
		String RIB = this.txtRIB.getText();
		
		if(nom.equals("") || prenom.equals("") || email.equals("") || mdp.equals("") || adresse.equals("") || cp.equals("")
		   || ville.equals("") || tel.equals("") || RIB.equals("")) {
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}else if (!Controleur.verifierMdp(mdp)) {
	        JOptionPane.showMessageDialog(this,
	                "Le mot de passe doit contenir :\n" +
	                "- Au moins 12 caractères\n" +
	                "- Une majuscule\n" +
	                "- Une minuscule\n" +
	                "- Un chiffre\n" +
	                "- Un caractère spécial (+-*/_?,.;/:!$)"
	            );
	        }
		else {
			//instanciation nv Proprio
			Proprietaire unProprietaire = new Proprietaire(nom,prenom,email,mdp,adresse,tel,cp,ville,tel,RIB);
			//appel de la methode du controleur pour insérer proprio
			Controleur.insertProprietaire(unProprietaire);
			JOptionPane.showMessageDialog(this,"Insertion réussie du proprietaire");
			//actualiser l'affichage
			unProprietaire = Controleur.selectWhereProprietaire(email);
			Object ligne [] = {unProprietaire.getId_user(),nom,prenom,email,mdp,adresse,cp,ville,tel,RIB};
			this.unTableau.ajoutLigne(ligne);
			//vider les champs
			this.viderChamps();
		}
	}
}
