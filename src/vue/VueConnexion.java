package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controleur.Controleur;
import controleur.NSEvent;
import controleur.Utilisateur;
import controleur.Admin;

public class VueConnexion extends JFrame implements ActionListener,KeyListener {
	
	private JPanel panelIcon = new JPanel();
	private JButton btAnnuler = new JButton("Annuler");
	private JButton btValider = new JButton("Connexion");
	private JTextField txtEmail = new JTextField();
	private JPasswordField txtMdp = new JPasswordField();
	private JLabel Email = new JLabel("Email");
	private JLabel Mdp = new JLabel("Mdp");
	
	
	
	public VueConnexion() {
		
		//Changer titre fenêtre
		this.setTitle("Neige et Soleil");
		//Definir les dimensions de la fenêtre
		this.setBounds(400,20,800,400);
		//fermer l'application au clic de la croix
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Refuser le dimensionnement(agrandir, retrecir) de la fenêtre
		this.setResizable(false);
		//changer de couleur du panel de la fenetre
		this.getContentPane().setBackground(new Color(17,44,80));
		//supp le layout par defaut
		this.setLayout(null);
		
		ImageIcon uneImage = new ImageIcon("src/img/neige-soleil-presentation2.png");
		JLabel lbImage = new JLabel(uneImage);
		lbImage.setBounds(30,20,uneImage.getIconWidth(), uneImage.getIconHeight());
		this.add(lbImage);
		
		
		this.panelIcon.setBounds(450,120,250,100);
		this.panelIcon.setBackground(new Color(17,44,80));
		this.panelIcon.setLayout(new GridLayout(3,2, 5, 5));
		
		this.Email.setForeground(Color.white);
		this.Mdp.setForeground(Color.white);
		
		this.panelIcon.add(this.Email);
		this.panelIcon.add(this.txtEmail);
		this.panelIcon.add(this.Mdp);
		this.panelIcon.add(this.txtMdp);
		this.panelIcon.add(this.btValider);
		this.panelIcon.add(this.btAnnuler);
		
		this.add(this.panelIcon);
		
		this.btAnnuler.addActionListener(this);
		this.btValider.addActionListener(this);
		
		//rendre les champs txt ecoutables a la frappe entrée
		this.txtEmail.addKeyListener(this);
		this.txtMdp.addKeyListener(this);
		
		this.setVisible(true);
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btAnnuler) {
			this.txtEmail.setText("");
			this.txtMdp.setText("");
		}else if(e.getSource() == this.btValider) {
			this.traitement();
		}
		
	}
	
	
	public void traitement() {
		String email = this.txtEmail.getText();
		String mdp  = new String (this.txtMdp.getPassword());
		
		if(email.equals("") || mdp.equals("")) {
			JOptionPane.showMessageDialog(this, "Veuillez remplir touts les champs");
		}else {
			//On appel le controleur pour verif presence admin dans la bdd
			Utilisateur unAdmin = Controleur.selectWhereAdmin(email, mdp);
			if(unAdmin == null) {
				JOptionPane.showMessageDialog(this, "Identifiants incorrects");
				this.txtEmail.setText("");
				this.txtMdp.setText("");
			}else {
				JOptionPane.showMessageDialog(this, "Bienvenue, "+ unAdmin.getNom()+" "+unAdmin.getPrenom()
				+" \n votre rôle est "+ unAdmin.getRole());
				
				
				//Ouvrir la vue général du logiciel
				NSEvent.rendreVisibleConnexion(false);
				NSEvent.creerVueGenerale(true);
			}
		}
	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.traitement();
		}
	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
