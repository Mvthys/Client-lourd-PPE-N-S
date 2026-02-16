package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controleur.NSEvent;

public class VueGeneral extends JFrame implements ActionListener, KeyListener{
	//creation des boutons
	private JPanel panelMenu = new JPanel();
	private JButton btClients = new JButton("Clients");
	private JButton btProprietaires = new JButton("Proprietaires");
	private JButton btHabitations = new JButton("Habitations");
	private JButton btReservations = new JButton("Reservations");
	private JButton btStats = new JButton("Stats");
	private JButton btQuitter = new JButton("Quitter");	
	//Creation des panneaux
	private static PanelClients unPanelClients = new PanelClients("Gestion des clients");
	private static PanelProprietaires unPanelProprietaires = new PanelProprietaires("Gestion des proprietaires");
	private static PanelHabitations unPanelHabitations = new PanelHabitations("Gestion des habitations");
	private static PanelReservations unPanelReservations = new PanelReservations("Gestion des reservations");
	private static PanelStats unPanelStats = new PanelStats("Gestion des statistiques");
	
	
	
	public VueGeneral() {
		//Changer titre fenêtre
		this.setTitle("Neige et Soleil");
		//Definir les dimensions de la fenêtre
		this.setBounds(200,20,1100,800);
		//fermer l'application au clic de la croix
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Refuser le dimensionnement(agrandir, retrecir) de la fenêtre
		this.setResizable(false);
		//changer de couleur du panel de la fenetre
		this.getContentPane().setBackground(new Color(242,242,242));
		//supp le layout par defaut
		this.setLayout(null);
		
		//Placement panel menu
		this.panelMenu.setBounds(100,10,900,40);
		this.panelMenu.setBackground(new Color(168,214,116));
		this.panelMenu.setLayout(new GridLayout(1,6));
		this.panelMenu.add(this.btClients);
		this.panelMenu.add(this.btProprietaires);
		this.panelMenu.add(this.btHabitations);
		this.panelMenu.add(this.btReservations);
		this.panelMenu.add(this.btStats);
		this.panelMenu.add(this.btQuitter);
		this.add(this.panelMenu);
		
		//bt ecoutables
		this.btClients.addActionListener(this);
		this.btProprietaires.addActionListener(this);
		this.btHabitations.addActionListener(this);
		this.btReservations.addActionListener(this);
		this.btStats.addActionListener(this);
		this.btQuitter.addActionListener(this);
		
		
		//ajout panels
		this.add(unPanelClients);
		this.add(unPanelProprietaires);
		this.add(unPanelHabitations);
		this.add(unPanelReservations);
		this.add(unPanelStats);
		
		this.setVisible(true);
	}
	
	public void afficherPanel (int choix) {
		unPanelClients.setVisible(false);
		unPanelProprietaires.setVisible(false);
		unPanelHabitations.setVisible(false);
		unPanelReservations.setVisible(false);
		unPanelStats.setVisible(false);
		switch(choix) {
			case 1 : unPanelClients.setVisible(true);break;	
			case 2 : unPanelProprietaires.setVisible(true);break;
			case 3 : unPanelHabitations.setVisible(true);break;	
			case 4 : unPanelReservations.setVisible(true);break;
			case 5 : unPanelStats.setVisible(true);break;
		}
	}
	
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btQuitter) {
			int retour = JOptionPane.showConfirmDialog(this, 
					"voulez-vous quitter l'appli ?", "Quitter l'appli", 
					JOptionPane.YES_NO_OPTION);
			if(retour == 0) {
				NSEvent.creerVueGenerale(false);
				NSEvent.rendreVisibleConnexion(true);		
			}
		}
		else if(e.getSource()==this.btClients) {
			this.afficherPanel(1);
		}else if(e.getSource()==this.btProprietaires) {
			this.afficherPanel(2);
		}else if(e.getSource()==this.btHabitations) {
			this.afficherPanel(3);
		}else if(e.getSource()==this.btReservations) {
			this.afficherPanel(4);
		}else if(e.getSource() == this.btStats) {
			this.afficherPanel(5);
		}
	}

}
