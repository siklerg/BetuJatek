package betuJatek;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Jatek {

	private Scanner scan = new Scanner(System.in);
	private Random random = new Random();

	private ArrayList<String> alapSzotar = new ArrayList<>(); //játszható szavak szótára
	private ArrayList<String> jatekSzotar = new ArrayList<>(); //megjátszott szavak szótára
	private ArrayList<String> megoldasSzotar = new ArrayList<>(); //lehetséges szavak szótára

	private String valasztottSzotarak = "";
	private String szoJatekos = "---";
	private String szoGep = "";
	private boolean gameOver = false;
	private String winner = "";

	public void play() {

		System.out.println("Köszöntünk játékunkban!");
		printSzabalyok();
		System.out.println();
		System.out.println("Betölthető szótárak:");
		System.out.println();
		System.out.println("ALAP szótár: szótövek ragozás nélkül - automatikusan betöltődik");
		System.out.println("1 - RAGOZOTT szavak: éri, fás, írd...");
		System.out.println("2 - RAGOK: ból, ből...");
		System.out.println("3 - NEVEK: személy, település, földrajzi... kisbetűvel írva");
		System.out.println("4 - RÖVIDÍTÉSEK: zrt, dkg...");
		System.out.println();

		// alap szótár betöltése
		alapSzotar = Szotar.getAlapSzotar();

		// kiegészítő szótárak beolvasása
		valasztottKiegeszitoSzotarak("ragozott", Szotar.getRagozottSzotar());
		valasztottKiegeszitoSzotarak("ragok", Szotar.getRagokSzotar());
		valasztottKiegeszitoSzotarak("nevek", Szotar.getNevekSzotar());
		valasztottKiegeszitoSzotarak("roviditesek", Szotar.getRoviditesekSzotar());
		System.out.println();

		if (valasztottSzotarak.equals("")) {
			System.out.println("Nincs kiválasztott kiegészítő szótár.");
		} else {
			System.out.println("Kiválasztott plusz szótárak: " + valasztottSzotarak);
		}
		System.out.println("Szavak száma a szótárban: " + alapSzotar.size());
		System.out.println();

		// játék

		System.out.println("Kezdődjön a játék!");
		System.out.println();
		gepSzoGeneralas();
		System.out.println("Az első szó: " + szoGep);
		alapSzotar.remove(szoGep);
		jatekSzotar.add(szoGep);

		//fut amíg nem a 'k' a válasz (Kilépés) vagy nincs még győztes
		while (!szoJatekos.equals("k") && !gameOver) {

			System.out.print("Kérem a következő szót: ");
			szoJatekos = scan.next();
			// elemezzük a játékos válaszát
			if (szoJatekos.equals("sz")) { // szabály kérés
				printSzabalyok();
				continue;
			} else if (szoJatekos.equals("v")) { // felhasznált szavak kiirítása
				System.out.println("Eddig felhasznált szavak (sorrendben):");
				printSzotar(jatekSzotar);
				System.out.println();
				continue;
			} else if (szoJatekos.equals("l")) { // lehetséges szavak száma
				System.out.println("Lehetséges megoldások száma: " + megoldasokSzama(szoGep));
				continue;
			} else if (szoJatekos.equals("s")) { // segítség kérés
				helpJatekos();
				System.out.println();
				System.out.println("S E G Í T S É G: " + szoJatekos);
				if (megoldasokSzama(szoJatekos) == 0) { // nincs több lehetséges válasz
					gameOver = true;
					winner = "Gép. (Az utolsó segítség után nem volt folytatási lehetőség)";
					continue;
				} else { // volt lehetséges szó, a gép lép
					gepLepes();
					if (megoldasokSzama(szoGep) == 0) { // ha nincs több lehetséges válasz
						gameOver = true;
						winner = "Gép. Nincs megfelelő szó a szótárban.";
					}
					continue;
				}
				// ellenőrizzük a kapott szót, ha van hiba üzenet, akkor kiírjuk
			} else if (!szoCheck().equals("")) {
				System.out.println(szoCheck());
				continue;
			}
			// ha idáig elérünk, akkor a kapott szó megfelelő
			alapSzotar.remove(szoJatekos);
			jatekSzotar.add(szoJatekos);
			//ha nincs több megfelelő szó, azaz a megoldás szótár üresként generálódik le, akkor győzött a játékos
			if (megoldasokSzama(szoJatekos) == 0) {
				gameOver = true;
				winner = "Játékos";
				continue;
			}

			gepLepes();

			//ha nincs több megfelelő szó, azaz a megoldás szótár üresként generálódik le, akkor győzött a gép
			if (megoldasokSzama(szoGep) == 0) {
				gameOver = true;
				winner = "Gép. Nincs több megfelelő szó a szótárban.";
			}
		}

		// játék vége
		System.out.println();
		System.out.println("A játék végetért!");
		System.out.println();
		if (gameOver) {
			System.out.println("Győztes: " + winner);
			System.out.println();
		}
		System.out.println("Összesen " + jatekSzotar.size() + " szó került a játékba.");
		System.out.println();
		System.out.println("Ezek a következők voltak: ");
		printSzotar(jatekSzotar);
	}

	/** Szabályok kiiratása. */
	private void printSzabalyok() {
		System.out.println();
		System.out.println("T U D N I V A L Ó K");
		System.out.println(
				"Három betűs szavakkal játszunk. A következő szó mindig csak egy betűben térhet el az előzőtől!");
		System.out.println("Az x betű nem játszik: box, fax...");
		System.out.println(
				"A dupla magánhangzós szavak sem: így, úgy... ezek állásfoglalásunk szerint 2 betűsek, noha három karakterből állnak.");
		System.out.println("Egy szótárral nagyobb a kihívás, több szótárral nagyobb a szabadság!");
		System.out.println();
		System.out.println("S E G Í T S É G E K");
		System.out.println(" sz: szabályok");
		System.out.println(" s:  segítég");
		System.out.println(" v:  már felhasznált szavak");
		System.out.println(" l: lehetséges válaszok száma");
		System.out.println(" k:  kilépés");
		System.out.println();
	}

	/** Kiegészító szótárak bekérése. */
	private void valasztottKiegeszitoSzotarak(String szotar, ArrayList<String> list) {
		String szotarValasztas;
		System.out.print("Szeretnéd a \"" + szotar + "\" szavakat is betölteni? i/n ");
		szotarValasztas = scan.next();
		if (szotarValasztas.charAt(0) == 'i') {
			alapSzotar.addAll(list);
			valasztottSzotarak += " - " + szotar;
		}
	}

	/** Szótár kiiratás. */
	private void printSzotar(ArrayList<String> szotar) {
		for (int i = 0; i < szotar.size(); i++) {
			System.out.print(szotar.get(i) + ", ");
			if (i % 10 == 9) {
				System.out.print("\n");
			}
		}
		System.out.println();
	}

	/** Kezdő szó generálása. */
	private void gepSzoGeneralas() {
		int x = random.nextInt(alapSzotar.size());
		szoGep = alapSzotar.get(x);
	}

	/**
	 * Segítség a játékosnak. Legenerálódik a lehetséges szavak szótára, és
	 * véletlenszerűen választunk belőle.
	 */
	private void helpJatekos() {
		megoldasokSzotarGeneralas(szoGep);
		int x = random.nextInt(megoldasSzotar.size());
		szoJatekos = megoldasSzotar.get(x);
		alapSzotar.remove(szoJatekos);
		jatekSzotar.add(szoJatekos);
	}

	/** Meghatározza a lehetséges megoldások számát, ehhez legenerálja a megoldás szótárt. */
	private int megoldasokSzama(String szo) {
		megoldasokSzotarGeneralas(szo);
		int szamlalo = megoldasSzotar.size();
		// System.out.println("Használható szavak: ");
		// printSzotar(megoldasSzotar);
		megoldasSzotar.clear();
		return szamlalo;
	}

	/** Legenerálja a megoldás szótárt a megadott szó alapján */
	private void megoldasokSzotarGeneralas(String szo) {
		megoldasSzotar.clear();
		String szoHelp;
		for (int i = 0; i < alapSzotar.size(); i++) {
			szoHelp = alapSzotar.get(i);
			if (szoValidChange(szoHelp, szo) == 2) {
				megoldasSzotar.add(szoHelp);
			}
		}
	}

	/**
	 * Legenerálódik a megoldás szótár, és a gép véletlenszerűen kiválaszt egy szót,
	 * majd megjeleníti.
	 */
	private void gepLepes() {
		megoldasokSzotarGeneralas(szoJatekos);
		int x = random.nextInt(megoldasSzotar.size());
		szoGep = megoldasSzotar.get(x);
		alapSzotar.remove(szoGep);
		jatekSzotar.add(szoGep);
		System.out.println("Az én szavam: " + szoGep);
		System.out.println();
	}

	/**
	 * A megadott szó ellenőrzése a szabályoknak megfelelően, ha van hiba,
	 * visszadaja üzenetben.
	 */
	private String szoCheck() {
		String uzenet = "";
		if (szoJatekos.equals("k")) {
			uzenet = "K I L É P É S";
		} else if (jatekSzotar.indexOf(szoJatekos) != -1) {
			uzenet = "Ez a szó már volt!";
		} else if (szoJatekos.length() != 3) {
			uzenet = "Három betűs szavakkal játszunk!";
		} else if (alapSzotar.indexOf(szoJatekos) == -1) {
			uzenet = "Ez a szó nincs benne a beállított szótárban!";
		} else if (szoValidChange(szoJatekos, szoGep) != 2) {
			uzenet = "Pontosan egy betűnek kell változnia!";
		}
		return uzenet;
	}

	/** Ellenőrzi, hogy a megadott két szó csak egy betűben tér-e el egymástól. */
	private int szoValidChange(String szo1, String szo2) {
		int egyezes = 0;
		if (szo1.charAt(0) == szo2.charAt(0)) {
			egyezes++;
		}
		if (szo1.charAt(1) == szo2.charAt(1)) {
			egyezes++;
		}
		if (szo1.charAt(2) == szo2.charAt(2)) {
			egyezes++;
		}
		return egyezes;
	}

}
