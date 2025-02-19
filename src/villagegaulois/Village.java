package villagegaulois;

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
		marche = new Marche(nbEtals);
	}
	
	
	private class Marche {
		private Etal etals[];
		private int nbEtalsOccupes;
		
		public Marche(int pfNbEtals) {
			etals = new Etal[pfNbEtals];
			for (int i=0;i<pfNbEtals;i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if(indiceEtal<etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
			else {
				System.err.println("Indice invalide : hors du tableau.");
			}
		}
		
		public int trouverEtalLibre() {
			for (int i=0;i<etals.length;i++) {
				if (!etals[i].isEtalOccupe())
					return i;
			}
			return -1;
		}
		
		public Etal[] trouverEtals (String produit) {
			Etal[] etalsProduit = new Etal[etals.length];
			int j=0;
			for (int i=0;i<etals.length;i++) {
				if (etals[i] != null && etals[i].contientProduit(produit))
					etalsProduit[j] = etals[i];
					j++;
			}
			return etalsProduit;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0;i<etals.length;i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			String txt="";
			int nbEtalsVides=0;
			
			for(Etal elt:etals) {
				if (elt==null)
					nbEtalsVides++;
				else if(elt.isEtalOccupe())
					txt+=""+elt.afficherEtal();
				else 
					nbEtalsVides++;
			}
			txt+="Il reste "+nbEtalsVides+" étals libres au marché.\n";
			
			return txt;
		}
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
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \""+this.nom+" possède comme étals : \n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des \""+produit+"\" sont : \n");
		Boolean vendeur = false;
		for (Etal elt : marche.trouverEtals(produit)) {
			if (elt!=null) {
				chaine.append(elt.afficherEtal());
				vendeur = true;
			}
		}
		if (!vendeur) 
			chaine.append("Aucun !\n");
		
		return chaine.toString();
	}

	public String installerVendeur(Gaulois gaulois, String produit, int nbProduits) {
		StringBuilder chaine = new StringBuilder();
		int idEtal = marche.trouverEtalLibre();
		if (idEtal ==-1) 
			chaine.append("Il n'y a pas de place pour "+gaulois.getNom()+" dans le marché !\n");
		else {
			marche.utiliserEtal(idEtal, gaulois, produit, nbProduits);
			chaine.append("Le vendeur "+gaulois.getNom()+" vend "+nbProduits+" "+produit+" à l'étal n°"+idEtal+".\n");
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois gaulois) {
		return marche.trouverVendeur(gaulois);
	}

	public String partirVendeur(Gaulois gaulois) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = marche.trouverVendeur(gaulois);
		chaine.append(etal.libererEtal());
		return chaine.toString();
	}
}