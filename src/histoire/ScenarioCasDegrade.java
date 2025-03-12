package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		try { 
			etal.libererEtal();
		}catch(IllegalStateException e) {
			System.err.println("Erreur : "+e.getMessage());
		}
		
		Gaulois acheteur = new Gaulois("Asterix", 5);
		
		try { 
			etal.acheterProduit(5, acheteur);
		}catch(IllegalStateException e) {
			System.err.println("Erreur : "+e.getMessage());
		}
		
		
		Gaulois vendeur = new Gaulois("Bonemine",2);
		etal.occuperEtal(vendeur, "fleurs", 12);
		
		try { 
			etal.acheterProduit(-8, acheteur);
		}catch(IllegalArgumentException e) {
			System.err.println("Erreur : "+e.getMessage());
		}
		
		
		acheteur = null;
		etal.acheterProduit(5, acheteur);
		
		
		System.out.println("Fin du test");
	}
}
