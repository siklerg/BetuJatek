package betuJatek;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

	private Scanner scan = new Scanner(System.in);
	private Random random = new Random();

	private ArrayList<String> basicDictionary = new ArrayList<>(); //játszható szavak szótára
	private ArrayList<String> gameDictionary = new ArrayList<>(); //megjátszott szavak szótára
	private ArrayList<String> solutionDictionary = new ArrayList<>(); //lehetséges szavak szótára

	private String chosenDictionaries = "";
	private String playerWord = "---";
	private String computerWord = "";
	private boolean gameOver = false;
	private String winner = "";

	public void play() {

		System.out.println("Köszöntünk játékunkban!");
		printRules();
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
		basicDictionary = Dictionary.getBasicDictionary();

		// kiegészítő szótárak beolvasása
		chosenExtraDictioneries("ragozott", Dictionary.getSuffixedWordsDictionary());
		chosenExtraDictioneries("ragok", Dictionary.getSuffixsDictionary());
		chosenExtraDictioneries("nevek", Dictionary.getNamesDictionary());
		chosenExtraDictioneries("roviditesek", Dictionary.getShortsDictionary());
		System.out.println();

		if (chosenDictionaries.equals("")) {
			System.out.println("Nincs kiválasztott kiegészítő szótár.");
		} else {
			System.out.println("Kiválasztott plusz szótárak: " + chosenDictionaries);
		}
		System.out.println("Szavak száma a szótárban: " + basicDictionary.size());
		System.out.println();

		// játék

		System.out.println("Kezdődjön a játék!");
		System.out.println();
		generateComputerWord();
		System.out.println("Az első szó: " + computerWord);
		basicDictionary.remove(computerWord);
		gameDictionary.add(computerWord);

		//fut amíg nem a 'k' a válasz (Kilépés) vagy nincs még győztes
		while (!playerWord.equals("k") && !gameOver) {

			System.out.print("Kérem a következő szót: ");
			playerWord = scan.next();
			// elemezzük a játékos válaszát
			if (playerWord.equals("sz")) { // szabály kérés
				printRules();
				continue;
			} else if (playerWord.equals("v")) { // felhasznált szavak kiirítása
				System.out.println("Eddig felhasznált szavak (sorrendben):");
				printDictionary(gameDictionary);
				System.out.println();
				continue;
			} else if (playerWord.equals("l")) { // lehetséges szavak száma
				System.out.println("Lehetséges megoldások száma: " + numberOfSolutions(computerWord));
				continue;
			} else if (playerWord.equals("s")) { // segítség kérés
				help();
				System.out.println();
				System.out.println("S E G Í T S É G: " + playerWord);
				if (numberOfSolutions(playerWord) == 0) { // nincs több lehetséges válasz
					gameOver = true;
					winner = "Gép. (Az utolsó segítség után nem volt folytatási lehetőség)";
					continue;
				} else { // volt lehetséges szó, a gép lép
					computerTurn();
					if (numberOfSolutions(computerWord) == 0) { // ha nincs több lehetséges válasz
						gameOver = true;
						winner = "Gép. Nincs megfelelő szó a szótárban.";
					}
					continue;
				}
				// ellenőrizzük a kapott szót, ha van hiba üzenet, akkor kiírjuk
			} else if (!checkWord().equals("")) {
				System.out.println(checkWord());
				continue;
			}
			// ha idáig elérünk, akkor a kapott szó megfelelő
			basicDictionary.remove(playerWord);
			gameDictionary.add(playerWord);
			//ha nincs több megfelelő szó, azaz a megoldás szótár üresként generálódik le, akkor győzött a játékos
			if (numberOfSolutions(playerWord) == 0) {
				gameOver = true;
				winner = "Játékos";
				continue;
			}

			computerTurn();

			//ha nincs több megfelelő szó, azaz a megoldás szótár üresként generálódik le, akkor győzött a gép
			if (numberOfSolutions(computerWord) == 0) {
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
		System.out.println("Összesen " + gameDictionary.size() + " szó került a játékba.");
		System.out.println();
		System.out.println("Ezek a következők voltak: ");
		printDictionary(gameDictionary);
	}

	/** Szabályok kiiratása. */
	private void printRules() {
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
	private void chosenExtraDictioneries(String chosenDictionary, ArrayList<String> list) {
		String choose;
		System.out.print("Szeretnéd a \"" + chosenDictionary + "\" szavakat is betölteni? i/n ");
		choose = scan.next();
		if (choose.charAt(0) == 'i') {
			basicDictionary.addAll(list);
			chosenDictionaries += " - " + chosenDictionary;
		}
	}

	/** Szótár kiiratás. */
	private void printDictionary(ArrayList<String> dictionary) {
		for (int i = 0; i < dictionary.size(); i++) {
			System.out.print(dictionary.get(i) + ", ");
			if (i % 10 == 9) {
				System.out.print("\n");
			}
		}
		System.out.println();
	}

	/** Kezdő szó generálása. */
	private void generateComputerWord() {
		int x = random.nextInt(basicDictionary.size());
		computerWord = basicDictionary.get(x);
	}

	/**
	 * Segítség a játékosnak. Legenerálódik a lehetséges szavak szótára, és
	 * véletlenszerűen választunk belőle.
	 */
	private void help() {
		generateSolutionDictionary(computerWord);
		int x = random.nextInt(solutionDictionary.size());
		playerWord = solutionDictionary.get(x);
		basicDictionary.remove(playerWord);
		gameDictionary.add(playerWord);
	}

	/** Meghatározza a lehetséges megoldások számát, ehhez legenerálja a megoldás szótárt. */
	private int numberOfSolutions(String word) {
		generateSolutionDictionary(word);
		int szamlalo = solutionDictionary.size();
		// System.out.println("Használható szavak: ");
		// printDictionary(solutionDictionary);
		solutionDictionary.clear();
		return szamlalo;
	}

	/** Legenerálja a MEGOLDÁS SZÓTÁRT a megadott szó alapján */
	private void generateSolutionDictionary(String word) {
		solutionDictionary.clear();
		for (int i = 0; i < basicDictionary.size(); i++) {
			if (validatingWord(basicDictionary.get(i), word) == 2) {
				solutionDictionary.add(basicDictionary.get(i));
			}
		}
	}

	/**
	 * Lekéri a MEGOLDÁS SZÓTÁRT, és a gép véletlenszerűen kiválaszt egy szót,
	 * majd megjeleníti.
	 */
	private void computerTurn() {
		generateSolutionDictionary(playerWord);
		int x = random.nextInt(solutionDictionary.size());
		computerWord = solutionDictionary.get(x);
		basicDictionary.remove(computerWord);
		gameDictionary.add(computerWord);
		System.out.println("Az én szavam: " + computerWord);
		System.out.println();
	}

	/**
	 * A megadott szó ellenőrzése a szabályoknak megfelelően, ha van hiba,
	 * visszadaja üzenetben.
	 */
	private String checkWord() {
		String message = "";
		if (playerWord.equals("k")) {
			message = "K I L É P É S";
		} else if (gameDictionary.indexOf(playerWord) != -1) {
			message = "Ez a szó már volt!";
		} else if (playerWord.length() != 3) {
			message = "Három betűs szavakkal játszunk!";
		} else if (basicDictionary.indexOf(playerWord) == -1) {
			message = "Ez a szó nincs benne a beállított szótárban!";
		} else if (validatingWord(playerWord, computerWord) != 2) {
			message = "Pontosan egy betűnek kell változnia!";
		}
		return message;
	}

	/** Ellenőrzi, hogy a megadott két szó csak egy betűben tér-e el egymástól. */
	private int validatingWord(String word1, String word2) {
		int match = 0;
		if (word1.charAt(0) == word2.charAt(0)) {
			match++;
		}
		if (word1.charAt(1) == word2.charAt(1)) {
			match++;
		}
		if (word1.charAt(2) == word2.charAt(2)) {
			match++;
		}
		return match;
	}

}
