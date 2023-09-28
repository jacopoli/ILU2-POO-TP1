package histoire;

import villagegaulois.Etal;
import personnages.Gaulois;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois gaulois = new Gaulois("Obélix", 25);
		etal.libererEtal();
		try {
		etal.acheterProduit(0, gaulois);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test");
		}
}
