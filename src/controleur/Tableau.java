package controleur;

import javax.swing.table.AbstractTableModel;

public class Tableau extends AbstractTableModel {
	
	private String enTetes[];
	private Object donnees[][];
	
	public Tableau(Object [][] donnees, String enTetes[]) {
		this.donnees = donnees;
		this.enTetes = enTetes;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.donnees.length;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.enTetes.length;
	}

	@Override
	public Object getValueAt(int i, int j) {
		// TODO Auto-generated method stub
		return this.donnees[i][j];
	}
	
	public void ajoutLigne(Object ligne[]) {
		Object matrice [] [] = new Object[this.donnees.length + 1][this.enTetes.length];
		//faire la copie de la matrice 
		for (int i = 0; i<this.donnees.length; i++) {
			matrice[i] = this.donnees[i];
		}
		//ajout ligne
		matrice[this.donnees.length] = ligne;
		//recopie la matrice dans les données
		this.donnees = matrice;
		//actualisation changement
		this.fireTableDataChanged();
	}
	
	public void setDonnes (Object [][]matrice) {
		this.donnees = matrice;
		this.fireTableDataChanged();//actualise affichage donnees
	}

	@Override
	public String getColumnName(int i) {
		// TODO Auto-generated method stub
		return this.enTetes[i];
	}

}
