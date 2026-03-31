package vue;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class PanelPrincipal extends JPanel {
	
	public PanelPrincipal(String titre) {
		this.setBounds(80,80,1000,800);
		this.setBackground(new Color(242,242,242));
		this.setLayout(null);
		
		JLabel lbTitre = new JLabel(titre);
		Font police = new Font("Arial", Font.ITALIC, 18);
		lbTitre.setFont(police);
		lbTitre.setBounds(350,10,250,20);
		this.add(lbTitre);
		
		//si il ya lieu d'une icone pour caractériser panel
		this.setVisible(false);
	}

}
