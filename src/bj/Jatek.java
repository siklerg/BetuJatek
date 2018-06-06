package bj;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Jatek {

    private static Scanner scan = new Scanner(System.in);
    private static Random random = new Random();
    private static BufferedReader beolvaso = null;
	
    private static ArrayList<String> alapSzotar = new ArrayList<>();
    private static ArrayList<String> jatekSzotar = new ArrayList<>();
    private static ArrayList<String> megoldasSzotar = new ArrayList<>();

    //private String path = "C:\\Users\\Sikler.Gabor\\Google Drive\\Code\\Git\\BetuJatek\\src\\bj\\";
    private String path = "E:\\Google Drive\\Code\\Git\\BetuJatek\\src\\bj\\";
    
    private static String valasztottSzotarak = "";
    private static String szoJatekos = "---";
    private static String szoGep = "";
    private static boolean gameOver = false;
    private static String gyoztes = "";
    
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
        szotarBetoltes(path + "alap.txt");

        // kiegészítő szótárak beolvasása
        valasztottKiegeszitoSzotarak("ragozott");
        valasztottKiegeszitoSzotarak("ragok");
        valasztottKiegeszitoSzotarak("nevek");
        valasztottKiegeszitoSzotarak("roviditesek");
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

        while (!szoJatekos.equals("k") && !gameOver) {

            System.out.print("Kérem a következő szót: ");
            szoJatekos = scan.next();

            if (szoJatekos.equals("sz")) {
                printSzabalyok();
                continue;
            } else if (szoJatekos.equals("v")) {
                System.out.println("Eddig felhasznált szavak (sorrendben):");
                printSzotar(jatekSzotar);
                System.out.println();
                continue;
            } else if (szoJatekos.equals("l")) {
                System.out.println("Lehetséges megoldások száma: " + megoldasokSzama(szoGep));
                continue;
            } else if (szoJatekos.equals("s")) {
                helpJatekos();
                System.out.println();
                System.out.println("S E G Í T S É G: " + szoJatekos);
                if (megoldasokSzama(szoJatekos) == 0) {
                    gameOver = true;
                    gyoztes = "Gép. (Az utolsó segítség után nem volt folytatási lehetőség)";
                    continue;
                } else {
                    gepLepes();
                    if (megoldasokSzama(szoGep) == 0) {
                        gameOver = true;
                        gyoztes = "Gép. Nincs megfelelő szó a szótárban.";
                    }
                    continue;
                }
            } else if (!szoCheck().equals("")) {
                System.out.println(szoCheck());
                continue;
            }
            alapSzotar.remove(szoJatekos);
            jatekSzotar.add(szoJatekos);

            if (megoldasokSzama(szoJatekos) == 0) {
                gameOver = true;
                gyoztes = "Játékos";
                continue;
            }

            gepLepes();

            if (megoldasokSzama(szoGep) == 0) {
                gameOver = true;
                gyoztes = "Gép. Nincs megfelelő szó a szótárban.";
            }
        }

        // játék vége
        System.out.println();
        System.out.println("A játék végetért!");
        System.out.println();
        if (gameOver) {
            System.out.println("Győztes: " + gyoztes);
            System.out.println();
        }
        System.out.println("Összesen " + jatekSzotar.size() + " szó került a játékba.");
        System.out.println();
        System.out.println("Ezek a következők voltak: ");
        printSzotar(jatekSzotar);
    }

    private void printSzabalyok() {
        System.out.println();
        System.out.println("T U D N I V A L Ó K");
        System.out.println("Három betűs szavakkal játszunk. A következő szó mindig csak egy betűben térhet el az előzőtől!");
        System.out.println("Az x betű nem játszik: box, fax...");
        System.out.println("A dupla magánhangzós szavak sem: így, úgy... ezek állásfoglalásunk szerint 2 betűsek, noha három karakterből állnak.");
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

    private void valasztottKiegeszitoSzotarak(String szotar) {
        String szotarValasztas;
        System.out.print("Szeretnéd a \"" + szotar + "\" szavakat is betölteni? i/n ");
        szotarValasztas = scan.next();
        if (szotarValasztas.charAt(0) == 'i') {
            szotarBetoltes(path + szotar + ".txt");
            valasztottSzotarak += " - " + szotar;
        }
    }

    private void szotarBetoltes(String pathSzotar) {
        try {
            beolvaso = new BufferedReader(new FileReader(pathSzotar));
            String beolvasottSzo = "";
            while ((beolvasottSzo = beolvaso.readLine()) != null) {
                alapSzotar.add(beolvasottSzo);
            }
        } catch (
                FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (
                Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                beolvaso.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void printSzotar(ArrayList<String> szotar) {
        for (int i = 0; i < szotar.size(); i++) {
            System.out.print(szotar.get(i) + ", ");
            if (i % 10 == 9) {
                System.out.print("\n");
            }
        }
        System.out.println();
    }

    private void gepSzoGeneralas() {
        int x = random.nextInt(alapSzotar.size());
        szoGep = alapSzotar.get(x);
    }

    private void helpJatekos() {
        megoldasokSzotarGeneralas(szoGep);
        int x = random.nextInt(megoldasSzotar.size());
        szoJatekos = megoldasSzotar.get(x);
        alapSzotar.remove(szoJatekos);
        jatekSzotar.add(szoJatekos);
    }

    private int megoldasokSzama(String szo) {
        megoldasokSzotarGeneralas(szo);
        int szamlalo = megoldasSzotar.size();
        //System.out.println("Használható szavak: ");
        //printSzotar(megoldasSzotar);
        megoldasSzotar.clear();
        return szamlalo;
    }

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


    private void gepLepes() {
        megoldasokSzotarGeneralas(szoJatekos);
        int x = random.nextInt(megoldasSzotar.size());
        szoGep = megoldasSzotar.get(x);
        alapSzotar.remove(szoGep);
        jatekSzotar.add(szoGep);
        System.out.println("Az én szavam: " + szoGep);
        System.out.println();
    }

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
