package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controleur.Controleur;
import controleur.Maison;
import controleur.Proprietaire;
import controleur.Tableau;

public class PanelMaisons extends PanelPrincipal implements ActionListener {
	
	//création élements panel
	private JPanel panelForm = new JPanel();
	private JTextField txtAdresse = new JTextField();
	private JTextField txtCp = new JTextField();
	private JTextField txtVille = new JTextField();
	private JTextField txtTarifBas = new JTextField();
	private JTextField txtTarifMoy = new JTextField();
	private JTextField txtTarifHaut = new JTextField();
	private JTextField txtSurface = new JTextField();
	private static JComboBox<String> txtIdProprietaire = new JComboBox<String>();
	private JTextField txtDescription = new JTextField();
	private JTextField txtTitre = new JTextField();
	private JTextField txtCapacite = new JTextField();
	private JTextField txtCarac = new JTextField();
	
	private JButton btPhotos = new JButton("Choisir (3 minimum)");
	private File[] fichierPhotos = null;//tableau pour les photos
	
	private JLabel lbTitreMaison = new JLabel("Maisons");
	private Font police = new Font("Arial", Font.ITALIC, 18);
	private JLabel lbInsertMaison = new JLabel("Insérer/Modifier maison");
	
	private JButton btnAnnuler = new JButton("Annuler");
	private JButton btnValider = new JButton("Valider");
	
	private JTable tableHabitations;//Jtable
	private JScrollPane scrollHabitations;
	private Tableau unTableau;
	
	private JPanel panelFiltre = new JPanel();
	private JTextField txtFiltre = new JTextField();
	private JButton btFiltrer = new JButton("Filtrer");
	
	private JButton btSupprimer = new JButton("Supprimer");
	private JButton btModifier = new JButton("Modifier");
	
	private JLabel lbNbHabitations = new JLabel();

	
	
	public PanelMaisons(String titre) {
		super(titre);
		// TODO Auto-generated constructor stub
		//Placement titre
		this.lbTitreMaison.setBounds(600,10,500,20);
		this.lbTitreMaison.setFont(this.police);
		this.add(this.lbTitreMaison);
		
		//Placement panel filtre
		this.panelFiltre.setBounds(400,50,500,20);
		this.panelFiltre.setBackground(new Color(242,242,242));
		this.panelFiltre.setLayout(new GridLayout(1,3, 5, 5));
		this.panelFiltre.add(new JLabel("Filtrer par :"));
		this.panelFiltre.add(this.txtFiltre);
		this.panelFiltre.add(this.btFiltrer);
		this.add(this.panelFiltre);
		
		//Placement titre insérer/modif appart
		this.lbInsertMaison.setBounds(120,50,200,20);
		this.add(this.lbInsertMaison);
		
		//Placement panel insert
		this.panelForm.setBounds(10,80,350,400);
		this.panelForm.setBackground(new Color(242,242,242));
		this.panelForm.setLayout(null);
		this.panelForm.setLayout(new GridLayout(15,2, 5, 5));
		//Placement élements
		this.panelForm.add(new JLabel("Adresse : "));
		this.panelForm.add(this.txtAdresse);
		this.panelForm.add(new JLabel("Code Postal : "));
		this.panelForm.add(this.txtCp);
		this.panelForm.add(new JLabel("Ville : "));
		this.panelForm.add(this.txtVille);
		this.panelForm.add(new JLabel("Tarif bas : "));
		this.panelForm.add(this.txtTarifBas);
		this.panelForm.add(new JLabel("Tarif moyen : "));
		this.panelForm.add(this.txtTarifMoy);
		this.panelForm.add(new JLabel("Tarif haut : "));
		this.panelForm.add(this.txtTarifHaut);
		this.panelForm.add(new JLabel("Surface : "));
		this.panelForm.add(this.txtSurface);
		this.panelForm.add(new JLabel("Propriétaire : "));
		this.panelForm.add(txtIdProprietaire);
		this.panelForm.add(new JLabel("Photos :"));
		this.panelForm.add(btPhotos);
		this.panelForm.add(new JLabel("Description : "));
		this.panelForm.add(this.txtDescription);
		this.panelForm.add(new JLabel("Titre : "));
		this.panelForm.add(this.txtTitre);
		this.panelForm.add(new JLabel("Capacité : "));
		this.panelForm.add(this.txtCapacite);
		this.panelForm.add(new JLabel("Caratéristiques : "));
		this.panelForm.add(this.txtCarac);
		//Ajout btn
		this.panelForm.add(this.btnAnnuler);
		this.panelForm.add(this.btnValider);
		this.panelForm.add(this.btSupprimer);
		this.panelForm.add(this.btModifier);
		//Placement borders
		this.panelForm.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 1),
				BorderFactory.createEmptyBorder(10,10,10,10)
				));
		//Desactivation btns
		this.btSupprimer.setEnabled(false);
		this.btModifier.setEnabled(false);
		//Actualisation départ pptrs
		PanelMaisons.actualiserProprietaires();
		//AJOUT DU TOUT
		this.add(this.panelForm);
		
		
		//placement JTable
		String nomsColonnes[] = {"ID","Type","Adresse","Cp","Ville","TarifBas","TarifMoy","TarifHaut","Surface",
								 "ID Proprietaire","Description","Titre","Capacite","Caractéristiques"};
		this.unTableau = new Tableau(this.obtenirDonnees(""), nomsColonnes);
		this.tableHabitations = new JTable(this.unTableau);
		this.scrollHabitations = new JScrollPane(this.tableHabitations);
		this.scrollHabitations.setBounds(400,80,500,400);
		this.scrollHabitations.setBackground(Color.BLACK);
		this.add(this.scrollHabitations);
		
		//Ajouter lbNbHab
		this.lbNbHabitations.setBounds(600,480,400,20);
		this.lbNbHabitations.setText("Nombre de maisons : "+unTableau.getRowCount());
		this.add(this.lbNbHabitations);
		
		//rendre btn ecoutables
		this.btnAnnuler.addActionListener(this);
		this.btnValider.addActionListener(this);
		this.btFiltrer.addActionListener(this);
		this.btSupprimer.addActionListener(this);
		this.btModifier.addActionListener(this);
		this.btPhotos.addActionListener(this);
		
		
		
		
		/*******************************************************     MOUSE LISTENER     *******************************************************/
		this.tableHabitations.addMouseListener(new MouseListener() {
			
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
				int numLigne = tableHabitations.getSelectedRow();
				int idHabitation = Integer.parseInt(tableHabitations.getValueAt(numLigne, 0).toString());
				txtAdresse.setText(unTableau.getValueAt(numLigne,2).toString());
				txtCp.setText(unTableau.getValueAt(numLigne,3).toString());
				txtVille.setText(unTableau.getValueAt(numLigne,4).toString());
				txtTarifBas.setText(unTableau.getValueAt(numLigne,5).toString());
				txtTarifMoy.setText(unTableau.getValueAt(numLigne,6).toString());
				txtTarifHaut.setText(unTableau.getValueAt(numLigne,7).toString());
				txtSurface.setText(unTableau.getValueAt(numLigne,8).toString());
				String idProprietaire = unTableau.getValueAt(numLigne, 9).toString();
				for (int i = 0; i < txtIdProprietaire.getItemCount(); i++) {
				    String item = txtIdProprietaire.getItemAt(i).toString();
				    if (item.split("-")[0].equals(idProprietaire)) {
				        txtIdProprietaire.setSelectedIndex(i);
				        break;
				        }
				    }
				txtDescription.setText(unTableau.getValueAt(numLigne,10).toString());
				txtTitre.setText(unTableau.getValueAt(numLigne,11).toString());
				txtCapacite.setText(unTableau.getValueAt(numLigne,12).toString());
				txtCarac.setText(unTableau.getValueAt(numLigne,13).toString());
				
				
				btModifier.setEnabled(true);
				btSupprimer.setEnabled(true);
				btnValider.setEnabled(false);
				
			}
		});
	}
	
	
	
	
	/*******************************************************     ACTUALISER PROPRIO     *******************************************************/
	public static void actualiserProprietaires() {
		//remplir les client dans comboBox
			txtIdProprietaire.removeAllItems();
			txtIdProprietaire.addItem(" Sélectionner ");
			ArrayList<Proprietaire> lesProprietaires = Controleur.selectAllProprietaires("");
			for(Proprietaire unProprietaire : lesProprietaires) {
					txtIdProprietaire.addItem(unProprietaire.getId_user() + "-" + unProprietaire.getNom() + " " + unProprietaire.getPrenom());
				}
		}
	
	
	
	
	/*******************************************************     OBTENIR DONNEES     *******************************************************/
	public Object [] [] obtenirDonnees (String filtre){
		ArrayList<Maison> lesMaisons = Controleur.selectAllMaisons(filtre);
		Object [] [] matrice = new Object [lesMaisons.size()][15];
		int i = 0;
		for (Maison uneMaison : lesMaisons) {
			matrice[i][0] = uneMaison.getRef_hab();
			matrice[i][1] = uneMaison.getType();
			matrice[i][2] = uneMaison.getAdresse();
			matrice[i][3] = uneMaison.getCp();
			matrice[i][4] = uneMaison.getVille();
			matrice[i][5] = uneMaison.getTarifBas();
			matrice[i][6] = uneMaison.getTarifMoy();
			matrice[i][7] = uneMaison.getTarifHaut();
			matrice[i][8] = uneMaison.getSurface();
			matrice[i][9] = uneMaison.getIdProprietaire();
			matrice[i][10] = uneMaison.getDescription();
			matrice[i][11] = uneMaison.getTitre();
			matrice[i][12] = uneMaison.getCapacite();
			matrice[i][13] = uneMaison.getCaracteristique();
			i++;
		}
		return matrice;
	}
	
	
	
	
	/*******************************************************     ACTION EVENT    *******************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btnAnnuler) {
			this.viderChamps();
			this.btModifier.setEnabled(false);
			this.btSupprimer.setEnabled(false);
			this.btnValider.setEnabled(true);
		}else if(e.getSource() == this.btFiltrer) {
			this.unTableau.setDonnes(this.obtenirDonnees(this.txtFiltre.getText()));
			this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
		}else if (e.getSource() == this.btnValider){
			this.insertMaison();
		}else if(e.getSource() == this.btSupprimer) {
			this.deleteMaison();
		}else if(e.getSource() == this.btModifier) {
			this.updateMaison();
		}
		//gestion photos
		else if(e.getSource() == this.btPhotos) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(true);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Images (JPG,PNG,AVIF)", "jpg", "jpeg", "png", "avif");
		    fileChooser.setFileFilter(filter);
		    int resultat = fileChooser.showOpenDialog(this);
		    if (resultat == JFileChooser.APPROVE_OPTION) {
		        File[] fichiersSelectionnes = fileChooser.getSelectedFiles();
		        // On force l'utilisateur à en prendre EXACTEMENT 3 !
		        if (fichiersSelectionnes.length != 3) {
		            JOptionPane.showMessageDialog(this, "Sélectionnez exactement 3 photos (utiliser Ctrl + Clic).");
		            this.fichierPhotos = null;
		        } else {
		            this.fichierPhotos = fichiersSelectionnes;
		            this.btPhotos.setText("3 photos ✅ : " 
		                + fichierPhotos[0].getName() + ", ... ");
		        }
		    }
		}
	}
	
	
	
	
	/*******************************************************     VIDER CHAMPS     *******************************************************/
	public void viderChamps() {
		this.txtAdresse.setText("");
		this.txtCp.setText("");
		this.txtVille.setText("");
		this.txtTarifBas.setText("");
		this.txtTarifMoy.setText("");
		this.txtTarifHaut.setText("");
		this.txtSurface.setText("");
		this.txtDescription.setText("");
		this.txtTitre.setText("");
		this.txtCapacite.setText("");
		this.txtCarac.setText("");
		this.txtIdProprietaire.setSelectedIndex(0);
		this.fichierPhotos = null;
		this.btPhotos.setText("Choisir (3 minimum)");
	}
	
	
	
	
	/*******************************************************     DELETE     *******************************************************/
	public void deleteMaison() {
		int numLigne = this.tableHabitations.getSelectedRow();
		int refHab = Integer.parseInt(this.tableHabitations.getValueAt(numLigne,0).toString());
		int retour = JOptionPane.showConfirmDialog(this,"Voulez vous supprimer cette habitation ?","Suppression habitation",
				JOptionPane.YES_NO_OPTION);
		if(retour == 0) {
			Controleur.deleteMaison(refHab);
			JOptionPane.showMessageDialog(this, "Suppression habitation effectuée avec succés");
			this.viderChamps();
			this.btSupprimer.setEnabled(false);
			this.btModifier.setEnabled(false);
			this.btnValider.setEnabled(true);
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
		}
	}
	
	
	
	
	/*******************************************************     UPDATE     *******************************************************/
	public void updateMaison() {
		int numLigne = this.tableHabitations.getSelectedRow();
		int refHab = Integer.parseInt(this.tableHabitations.getValueAt(numLigne, 0).toString());
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tarifBas = this.txtTarifBas.getText();
		String tarifMoy = this.txtTarifMoy.getText();
		String tarifHaut = this.txtTarifHaut.getText();
		String surface = this.txtSurface.getText();
		String description = this.txtDescription.getText();
		String titre = this.txtTitre.getText();
		String capaciteStr = this.txtCapacite.getText();
		String carac = this.txtCarac.getText();
		
		if(adresse.equals("") || cp.equals("") || ville.equals("") || tarifBas.equals("") || 
			tarifMoy.equals("") || tarifHaut.equals("") || surface.equals("") || 
			txtIdProprietaire.getSelectedItem().toString().trim().equals("Sélectionner") || capaciteStr.equals("")){
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}
		else if(this.fichierPhotos != null && this.fichierPhotos.length != 3){
			JOptionPane.showMessageDialog(this, "Erreur : Vous devez sélectionner exactement 3 photos.");
			this.fichierPhotos = null;
			return;
		}else if(!Controleur.regexAdresse(adresse)) {
			JOptionPane.showMessageDialog(this,"Format ADRESSE incorrect !");
			this.txtAdresse.setText("");
		}else if(!Controleur.regexCP(cp)) {
			JOptionPane.showMessageDialog(this,"Format CODE POSTAL incorrect ! \n(ex: 75017 - 5 chiffres)");
			this.txtCp.setText("");
		}else if(!Controleur.regexVille(ville)) {
			JOptionPane.showMessageDialog(this, "Format VILLE incorrect !");
			this.txtVille.setText("");
		}else if(!Controleur.regexTarifs(tarifBas)) {
			JOptionPane.showMessageDialog(this, "Format TARIF BAS incorrect ! \n(vueillez saisir un chiffre/nombre rond)");
			this.txtTarifBas.setText("");
		}else if(!Controleur.regexTarifs(tarifMoy)) {
			JOptionPane.showMessageDialog(this, "Format TARIF MOYEN incorrect ! \n(vueillez saisir un chiffre/nombre rond)");
			this.txtTarifMoy.setText("");
		}else if(!Controleur.regexTarifs(tarifHaut)) {
			JOptionPane.showMessageDialog(this, "Format TARIF HAUT incorrect ! \n(vueillez saisir un chiffre/nombre rond)");
			this.txtTarifHaut.setText("");
		}else if(!Controleur.regexChiffres(surface)) {
			JOptionPane.showMessageDialog(this, "Format SURFACE incorrect ! \n(vueillez saisir un chiffre/nombre rond)");
			this.txtSurface.setText("");
		}else if(!Controleur.regexChiffres(capaciteStr)) {
			JOptionPane.showMessageDialog(this, "Format CAPACITE incorrect ! \n(vueillez saisir un chiffre/nombre rond)");
			this.txtCapacite.setText("");
		}
		else {
			int capacite = Integer.parseInt(this.txtCapacite.getText());
			String chaine = txtIdProprietaire.getSelectedItem().toString();
			String tab[] = chaine.split("-");
			int idProprietaire = Integer.parseInt(tab[0]);
			
			//Verif taille photos
			if(this.fichierPhotos != null && this.fichierPhotos.length == 3) {
				long tailleMax = 4 * 1024 * 1024; // 4 Mo
			    for (File photo : fichierPhotos) {
			        if (photo.length() > tailleMax) {
			            JOptionPane.showMessageDialog(this, "La photo " + photo.getName() + " dépasse les 4 Mo autorisés.");
			            return;
			        }
			    }
			}
			
			//instanciation maison
			Maison uneMaison = new Maison(refHab,"Maison",adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire,description,titre,capacite,carac);
			//appel de la methode du controleur pour update maison
			Controleur.updateMaison(uneMaison);
			
			
			//Gestion lourde photos
			if(this.fichierPhotos != null && this.fichierPhotos.length == 3) {
				Controleur.deletePhotos(refHab);
				String dossierDestination ="C:/wamp64/www/PPE-BTS-SIO/Neige et Soleil/images/habitations/";
		        for (int i = 0; i < fichierPhotos.length; i++) {
		            File photoSource = fichierPhotos[i];
		            
		            String nomOriginal = photoSource.getName();
		            String extension = nomOriginal.substring(nomOriginal.lastIndexOf(".") + 1).toLowerCase();
		            //génere chaîne unique aléatoire
		            String nouveauNom = "photo_" + UUID.randomUUID().toString().substring(0, 13) + "." + extension;
		            File fichierCible = new File(dossierDestination + nouveauNom);
		            try {
		                //copie vers le dossier du serveur)
		                Files.copy(photoSource.toPath(), fichierCible.toPath(), StandardCopyOption.REPLACE_EXISTING);
		                //gestion flag is_principal
		                int isPrincipal = (i == 0) ? 1 : 0;
		                // Appel au modèle JDBC pour enregistrer la ligne dans la table `photo`
		                Controleur.insertPhotos(refHab, nouveauNom, isPrincipal);
		            }catch (IOException ex) {
		                System.out.println("Erreur lors du transfert de la photo : " + photoSource.getName());
		                ex.printStackTrace();
		            }
		        }
				
			}
			
			JOptionPane.showMessageDialog(this,"Modification réussie de la maison");
			//actualiser l'affichage
			Object ligne [] = {uneMaison.getRef_hab(),"Maison",adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire,description,titre,capacite,carac};
			//vider les champs
			this.viderChamps();
			this.btSupprimer.setEnabled(false);
			this.btModifier.setEnabled(false);
			this.btnValider.setEnabled(true);
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbHabitations.setText("Nombre de maison : "+unTableau.getRowCount());
		}
	}
	
	
	
	
	/*******************************************************     INSERT     *******************************************************/
	public void insertMaison() {
		String adresse = this.txtAdresse.getText();
		String cp = this.txtCp.getText();
		String ville = this.txtVille.getText();
		String tarifBas = this.txtTarifBas.getText();
		String tarifMoy = this.txtTarifMoy.getText();
		String tarifHaut = this.txtTarifHaut.getText();
		String surface = this.txtSurface.getText();
		String capaciteStr = this.txtCapacite.getText();
		String description = this.txtDescription.getText();
		String titre = this.txtTitre.getText();
		String carac = this.txtCarac.getText();

		//verif saisi champs
		if(adresse.equals("") || cp.equals("") || ville.equals("") || tarifBas.equals("") || 
		   tarifMoy.equals("") || tarifHaut.equals("") || surface.equals("") || 
		   txtIdProprietaire.getSelectedItem().toString().trim().equals("Sélectionner") || capaciteStr.equals("")){
			JOptionPane.showMessageDialog(this,"Veuillez remplir touts les champs");
		}
		else if(this.fichierPhotos == null || this.fichierPhotos.length != 3){
			JOptionPane.showMessageDialog(this, "Erreur : Vous devez sélectionner exactement 3 photos.");
			this.fichierPhotos = null;
			return;
		}
		else if(!Controleur.regexAdresse(adresse)) {
			JOptionPane.showMessageDialog(this,"Format ADRESSE incorrect !");
			this.txtAdresse.setText("");
		}else if(!Controleur.regexCP(cp)) {
			JOptionPane.showMessageDialog(this,"Format CODE POSTAL incorrect ! \n(ex: 75017 - 5 chiffres)");
			this.txtCp.setText("");
		}else if(!Controleur.regexVille(ville)) {
			JOptionPane.showMessageDialog(this, "Format VILLE incorrect !");
			this.txtVille.setText("");
		}else if(!Controleur.regexTarifs(tarifBas)) {
			JOptionPane.showMessageDialog(this, "Format TARIF BAS incorrect !");
			this.txtTarifBas.setText("");
		}else if(!Controleur.regexTarifs(tarifMoy)) {
			JOptionPane.showMessageDialog(this, "Format TARIF MOYEN incorrect !");
			this.txtTarifMoy.setText("");
		}else if(!Controleur.regexTarifs(tarifHaut)) {
			JOptionPane.showMessageDialog(this, "Format TARIF HAUT incorrect !");
			this.txtTarifHaut.setText("");
		}else if(!Controleur.regexChiffres(surface)) {
			JOptionPane.showMessageDialog(this, "Format SURFACE incorrect ! \n(vueillez saisir un chiffre/nombre rond)");
			this.txtSurface.setText("");
		}else if(!Controleur.regexChiffres(capaciteStr)) {
			JOptionPane.showMessageDialog(this, "Format CAPACITE incorrect ! \n(vueillez saisir un chiffre/nombre rond)");
			this.txtCapacite.setText("");
		}
		else {
			int capacite = Integer.parseInt(this.txtCapacite.getText());
			String chaine = txtIdProprietaire.getSelectedItem().toString();
			String tab[] = chaine.split("-");
			int idProprietaire = Integer.parseInt(tab[0]);
			
			//Verif taille photos
			long tailleMax = 4 * 1024 * 1024; // 4 Mo
		    for (File photo : fichierPhotos) {
		        if (photo.length() > tailleMax) {
		            JOptionPane.showMessageDialog(this, "La photo " + photo.getName() + " dépasse les 4 Mo autorisés.");
		            return;
		        }
		    }
		    
			//instanciation nv maison
			Maison uneMaison = new Maison("Maison",adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire,description,titre,capacite,carac);
			//appel de la methode du controleur pour insérer maison
			int idGenere = Controleur.insertMaison(uneMaison);
			
			
			//Gestion lourde photos
			if (idGenere > 0) {
		        // Le dossier cible serveur web où site PHP va chercher images
		        String dossierDestination ="C:/wamp64/www/PPE-BTS-SIO/Neige et Soleil/images/habitations/";
		        for (int i = 0; i < fichierPhotos.length; i++) {
		            File photoSource = fichierPhotos[i];
		            
		            String nomOriginal = photoSource.getName();
		            String extension = nomOriginal.substring(nomOriginal.lastIndexOf(".") + 1).toLowerCase();
		            //génere chaîne unique aléatoire
		            String nouveauNom = "photo_" + UUID.randomUUID().toString().substring(0, 13) + "." + extension;
		            File fichierCible = new File(dossierDestination + nouveauNom);
		            try {
		                //copie vers le dossier du serveur)
		                Files.copy(photoSource.toPath(), fichierCible.toPath(), StandardCopyOption.REPLACE_EXISTING);
		                //gest flag is_principal
		                int isPrincipal = (i == 0) ? 1 : 0;
		                // Appel au modèle JDBC pour enregistrer la ligne dans la table `photo`
		                Controleur.insertPhotos(idGenere, nouveauNom, isPrincipal);
		            }catch (IOException ex) {
		                System.out.println("Erreur lors du transfert de la photo : " + photoSource.getName());
		                ex.printStackTrace();
		            }
		        }
		    
		     JOptionPane.showMessageDialog(this,"Insertion réussie de la maison");
			//actualiser l'affichage
			Object ligne [] = {uneMaison.getRef_hab(),"Maison",adresse,cp,ville,tarifBas,tarifMoy,tarifHaut,surface,idProprietaire,description,titre,capacite,carac};
			this.unTableau.ajoutLigne(ligne);
			//vider les champs
			this.viderChamps();
			this.unTableau.setDonnes(this.obtenirDonnees(""));
			this.lbNbHabitations.setText("Nombre d'habitations : "+unTableau.getRowCount());
		}
	}
  }
}
