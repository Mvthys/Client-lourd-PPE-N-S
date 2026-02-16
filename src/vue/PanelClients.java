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

import controleur.Client;
import controleur.Controleur;
import controleur.Tableau;

public class PanelClients extends PanelPrincipal implements ActionListener {
	
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
	
	private JTable tableClients;
	private JScrollPane scrollClients;
	private Tableau unTableau;
	
	private JPanel panelFiltre = new JPanel();
	private JTextField txtFiltre = new JTextField();
	private JButton btFiltrer = new JButton("filtrer");
	
	private JButton btSupprimer = new JButton("Supprimer");
	private JButton btModifier = new JButton("Modifier");
	
	private JLabel lbNbClients = new JLabel();
	
	public PanelClients(String titre) {
		super(titre);
		// TODO Auto-generated constructor stub
		this.panelFiltre.setBounds(450,40,400,30);
		this.panelFiltre.setBackground(new Color(242,242,242));
		this.panelFiltre.setLayout(new GridLayout(1,3, 5, 5));
		this.panelFiltre.add(new JLabel("Filtrer les clients par :"));
		this.panelFiltre.add(this.txtFiltre);
		this.panelFiltre.add(this.btFiltrer);
		
		this.add(this.panelFiltre);
		
		
		this.panelForm.setBounds(10,80,300,400);
		this.panelForm.setBackground(new Color(242,242,242));
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
		String nomsColonnes[] = {"ID client","Nom","Prenom","Email","mdp","Adresse","Code postal","Ville","tel","RIB"};
		this.unTableau = new Tableau(this.obtenirDonnees(""), nomsColonnes);
		this.tableClients = new JTable(this.unTableau);
		this.scrollClients = new JScrollPane(this.tableClients);
		this.scrollClients.setBounds(400,80,500,400);
		this.scrollClients.setBackground(Color.BLACK);
		this.add(this.scrollClients);
		
		//Ajouter lbNbClient
		this.lbNbClients.setBounds(600,500,400,20);
		this.lbNbClients.setText("Nombre de clients : "+unTableau.getRowCount());
		this.add(this.lbNbClients);
	
		//rendre btn ecoutables
		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
		this.btFiltrer.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btModifier.addActionListener(this);
		
		this.tableClients.addMouseListener(new MouseListener() {
			
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
				int numLigne = tableClients.getSelectedRow();
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
		ArrayList<Client> lesClients = Controleur.selectAllClients(filtre);
		Object [] [] matrice = new Object [lesClients.size()][10];
		int i = 0;
		for (Client unClient : lesClients) {
			matrice[i][0] = unClient.getId_c();
			matrice[i][1] = unClient.getNom();
			matrice[i][2] = unClient.getPrenom();
			matrice[i][3] = unClient.getEmail();
			matrice[i][4] = unClient.getMdp();
			matrice[i][5] = unClient.getAdresse();
			matrice[i][6] = unClient.getCp();
			matrice[i][7] = unClient.getVille();
			matrice[i][8] = unClient.getTel();
			matrice[i][9] = unClient.getRib();
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
			this.insertClient();
		}else if(e.getSource() == this.btFiltrer) {
			this.unTableau.setDonnes(this.obtenirDonnees(this.txtFiltre.getText()));
			this.lbNbClients.setText("Nombre de clients : "+unTableau.getRowCount());
		}else if(e.getSource() == this.btSupprimer) {
			this.deleteClient();
		}else if(e.getSource() == this.btModifier) {
			this.updateClient();
		}
	
	}
	
	public void deleteClient() {
		int numLigne = tableClients.getSelectedRow();
		int idClient = Integer.parseInt(unTableau.getValueAt(numLigne,0).toString());
		int retour = JOptionPane.showConfirmDialog(this,"Voulez vous supprimer ce client ?","suppression client",
					 JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			Controleur.deleteClient(idClient);
			JOptionPane.showMessageDialog(this,"client supprimmé avec succés.");
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbClients.setText("Nombre de clients : "+unTableau.getRowCount());
		}
	}
	
	public void updateClient() {
		int numLigne = tableClients.getSelectedRow();
		int idClient = Integer.parseInt(unTableau.getValueAt(numLigne,0).toString());
		String nom = this.txtNom.getText();
		String prenom = this.txtPrenom.getText();
		String email = this.txtEmail.getText();
		String mdp = this.txtMdp.getText();
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tel = this.txtTel.getText();
		String RIB = this.txtRIB.getText();
		
		if(nom.equals("") || prenom.equals("") || email.equals("") || mdp.equals("") || adresse.equals("") || cp.equals("") || 
				ville.equals("") || tel.equals("") || RIB.equals("")) {
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}else {
			//instanciation nv client
			Client unClient = new Client(idClient,nom,prenom,email,mdp,adresse,cp,ville,tel,RIB);
			//appel de la methode du controleur pour insérer client
			Controleur.updateClient(unClient);
			JOptionPane.showMessageDialog(this,"Modification réussie du client");
			//vider les champs
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbClients.setText("Nombre de clients : "+unTableau.getRowCount());
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
	
	public void insertClient() {
		String nom = this.txtNom.getText();
		String prenom = this.txtPrenom.getText();
		String email = this.txtEmail.getText();
		String mdp = this.txtMdp.getText();
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tel = this.txtTel.getText();
		String RIB = this.txtRIB.getText();
		
		if(nom.equals("") || prenom.equals("") || email.equals("") || mdp.equals("") || adresse.equals("") || cp.equals("") || 
				ville.equals("") || tel.equals("") || RIB.equals("")) {
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}else {
			//instanciation nv client
			Client unClient = new Client(nom,prenom,email,mdp,adresse,cp,ville,tel,RIB);
			//appel de la methode du controleur pour insérer client
			Controleur.insertClient(unClient);
			JOptionPane.showMessageDialog(this,"Insertion réussie du client");
			//actualiser l'affichage
			unClient = Controleur.selectWhereClient(email);
			Object ligne [] = {unClient.getId_c(),nom,prenom,email,mdp,adresse,cp,ville,tel,RIB};
			this.unTableau.ajoutLigne(ligne);
			//vider les champs
			this.viderChamps();
		}
	}
	
}


