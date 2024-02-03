import java.util.Timer;
import java.util.TimerTask;

import model.compteur.Compteur;

public class TimerApp {

	public static void main(String[] args) {
		Timer timer = new Timer();
		Compteur compteur = new Compteur();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				compteur.tick();
				System.out.println(compteur.getValeur());
			}
		}, 1000, 1000);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
	}
}
