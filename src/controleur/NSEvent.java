package controleur;

import vue.VueConnexion;
import vue.VueGeneral;

public class NSEvent {
	private static VueConnexion uneVueConnexion ;
	private static VueGeneral uneVueGeneral;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		uneVueConnexion  = new VueConnexion();
	}
	
	public static void rendreVisibleConnexion(boolean action) {
		uneVueConnexion.setVisible(action);
	}
	
	public static void creerVueGenerale (boolean action) {
		if(action == true) {
			uneVueGeneral = new VueGeneral();
		}else {
			uneVueGeneral.dispose();
		}
	}

}
