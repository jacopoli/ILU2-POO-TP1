package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche= new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
//		System.out.println(marche.trouverEtalLibre()+vendeur.getNom());
		marche.utiliserEtal(marche.trouverEtalLibre(), vendeur, produit, nbProduit);
		StringBuilder chainePresentation = new StringBuilder();
		chainePresentation.append(String.format("%s  cherche un endroit pour vendre %s %d.\n", vendeur.getNom(),produit, nbProduit));
		chainePresentation.append("Le vendeur "+ vendeur.getNom() +" vend des fleurs � l'�tal n�"+ marche.trouverEtalLibre() +".\n");
		return chainePresentation.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] vendeurs = marche.trouverEtals(produit);
		StringBuilder chaine = new StringBuilder();
		chaine.append(String.format("Les vendeurs qui proposent des %s sont :\n", produit));
		for (int i=0; i<vendeurs.length; i++) {
			chaine.append(String.format("- %s\n", vendeurs[i].getVendeur().getNom()));
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(marche.trouverVendeur(vendeur).libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		return marche.afficherMarche();
	}

	
	private static class Marche {
		private Etal[] etals;
		
		private Marche (int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i=0; i<nombreEtals; i++) {
				etals[i]=new Etal();
			}
			
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i=0; i<etals.length; i++) {
				if (! etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			
			int nbUtilise=0;
			for (int i=0; i<etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
				nbUtilise++;
				}
			}
			Etal[] etalsUtilises= new Etal[nbUtilise];
			int iEtals=0;
			for (int i=0; iEtals<etalsUtilises.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsUtilises[iEtals]=etals[i];
					iEtals++;
				}
			}
			

			return etalsUtilises; 
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0; i<etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			int nbEtalsOcc=0;
			StringBuilder chaine= new StringBuilder();
			for (int i=0; i<etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(String.format("%s", etals[i].afficherEtal()));
					nbEtalsOcc++;
				}
			}
			chaine.append(String.format("Il reste %d étals non utilisés dans le marché.", etals.length-nbEtalsOcc));
			return chaine.toString();
		}
	}
	
}