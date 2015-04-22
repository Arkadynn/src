package upsidextre.comput.entryPoint;

import upsidextre.comput.hardware.Gant;
import upsidextre.comput.serveur.FingerTestLight;
import upsidextre.comput.serveur.ServeurGants;
import upsidextre.comput.utilities.RotationSliderTest;


public class UpsiDextre {

	private Gant mainDroite;
	private Gant mainGauche;
	
	private FingerTestLight fingerTest;
	
	public UpsiDextre() {
		fingerTest = new FingerTestLight();
	}
	
	public static void main(String[] args) {
		new ServeurGants(new UpsiDextre()).run();
	}

	public Gant getMainDroite() {
		return mainDroite;
	}

	public void setMainDroite(Gant mainDroite) {
		this.mainDroite = mainDroite;
	}

	public Gant getMainGauche() {
		return mainGauche;
	}

	public void setMainGauche(Gant mainGauche) {
		this.mainGauche = mainGauche;
	}

	public void feedFinger (int feed) {
		this.fingerTest.setvalue(feed);
	}
}
