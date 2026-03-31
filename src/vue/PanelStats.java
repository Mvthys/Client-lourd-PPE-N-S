package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;

public class PanelStats extends PanelPrincipal implements ActionListener  {
	
	private JLabel lbClients = new JLabel();
	private JLabel lbHabitations = new JLabel();
	private JLabel lbProprietaires = new JLabel();
	private JLabel lbReservations = new JLabel();
	
	private JPanel panelCount = new JPanel();


	public PanelStats(String titre) {
		super(titre);
		// TODO Auto-generated constructor stub
		
		this.panelCount.setBounds(350,70,200,200);
		this.panelCount.setBackground(new Color(242,242,242,242));
		this.panelCount.setLayout(new GridLayout(4,1, 5, 5));
		
		this.panelCount.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)
				));
		
		this.panelCount.add(this.lbClients);
		this.panelCount.add(this.lbHabitations);
		this.panelCount.add(this.lbProprietaires);
		this.panelCount.add(this.lbReservations);
		
		this.add(this.panelCount);
		
		int nbClients = Controleur.selectCountUtilisateur("client");
		int nbHabitations = Controleur.selectCount("habitation");
		int nbProprietaires = Controleur.selectCountUtilisateur("proprietaire");
		int nbReservations = Controleur.selectCount("reservation");
		
		this.lbClients.setText("Nombre clients : " + nbClients);
		this.lbHabitations.setText("Nombre habitations : " + nbHabitations);
		this.lbProprietaires.setText("Nombre propriétaires : " + nbProprietaires);
		this.lbReservations.setText("Nombre réservations : " + nbReservations);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
