package vue;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import controleur.Controleur;
import controleur.Habitation;

public class PanelHabitations extends PanelPrincipal implements ActionListener {
	
	private JPanel panelForm = new JPanel();
	
	private JButton btMaisons = new JButton("Maisons");
	private JButton btAppartements = new JButton("Appartements");
	private JLabel lbNbHabitations = new JLabel("");
	private List<Habitation> allHabitations = new ArrayList<>();
	
	private static PanelMaisons unPanelMaisons = new PanelMaisons("");
	private static PanelAppartements unPanelAppartements = new PanelAppartements("");

	public PanelHabitations(String titre) {
		super(titre);
		
		this.panelForm.setBounds(250,60,400,40);
		this.panelForm.setBackground(new Color(168,214,116));
		this.panelForm.setLayout(new GridLayout(1,2));
		
		//rendre btn ecoutables
		this.btMaisons.addActionListener(this);
		this.btAppartements.addActionListener(this);
		
		this.panelForm.add(this.btMaisons);
		this.panelForm.add(this.btAppartements);
		
		this.allHabitations = Controleur.selectAllHabitations("");
		
		this.lbNbHabitations.setBounds(370,100,300,20);
		this.lbNbHabitations.setText("Nombre total d'habitations : " + this.allHabitations.size());
		
		this.unPanelMaisons.setLocation(0,130);
		this.unPanelAppartements.setLocation(0,130);
		
		this.add(unPanelMaisons);
		this.add(unPanelAppartements);
		this.add(lbNbHabitations);
		
		this.add(this.panelForm);
	}
	
	public void afficherPanel (int choix) {
		unPanelMaisons.setVisible(false);
		unPanelAppartements.setVisible(false);
		switch(choix) {
			case 1 : unPanelMaisons.setVisible(true);break;	
			case 2 : unPanelAppartements.setVisible(true);break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btMaisons) {
			this.afficherPanel(1);
		}else if(e.getSource()==this.btAppartements) {
			this.afficherPanel(2);
		}
	}
}
