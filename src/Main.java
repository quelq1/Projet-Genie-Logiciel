
import com.sun.xml.internal.fastinfoset.util.CharArrayString;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static String fichier = "plan.txt";

    public static void main(String[] args) {
        System.out.println("   *********************");
        System.out.println("     Application metro");
        System.out.println("   *********************");

        Plan plan = new Plan(fichier);
        FavorisUtilisateur.chargerFavoris();
        Geolocalisation.geolocalisation(plan);

        boolean fin = false;

        do {
            System.out.println("\t\t------");
            System.out.println("\t\t MENU");
            System.out.println("\t\t------");
            System.out.println("1 - Actualiser ma position");
            System.out.println("2 - Ajouter une station");
            System.out.println("3 - Gestion des lignes");
            System.out.println("4 - Signaler un incident");
            System.out.println("5 - Itinéraire le plus rapide");
            System.out.println("6 - Itinéraire avec le moins de changements");
            System.out.println("7 - Itinéraire avec différentes etapes");
            System.out.println("8 - Gestion des favoris");
            System.out.println("");
            System.out.println("0 - Quitter");
            int reponse;
            Scanner sc = new Scanner(System.in);
            reponse = saisieInt(sc);

            switch (reponse) {
                case 0:
                    System.out.println("Au revoir...");
                    fin = true;
                    break;
                case 1:
                    Geolocalisation.geolocalisation(plan);
                    break;
                case 2:
                    AjoutStation.menuAjoutStation(plan);
                    break;
                case 3:
                    AjoutLigne.menuGestionLigne(plan);
                    break;
                case 4:
                    AjoutIncident.menuAjoutIncident(plan);
                    break;
                case 5:
                    RechercheItineraire.menuChoixDestination(plan, 1);
                    break;
                case 6:
                    RechercheItineraire.menuChoixDestination(plan, 2);
                    break;
                case 7:
                    RechercheItineraire.menuChoixDestination(plan, 3);
                    break;
                case 8:
                    FavorisUtilisateur.menuGestionFavoris(plan);
                    break;
                default:
                    System.out.println("Choix incorrect...");
                    break;
            }
        } while (!fin);

        //Sauvegarde des favoris
        FavorisUtilisateur.sauvegarderFavoris();
    }

     public static void ecriturefichier(String texte) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(fichier, true);
            writer.write(texte, 0, texte.length());
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
     
     public static void remplaceLigne(String s1, String s2) {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            boolean trouve = false;
            FileInputStream fis = new FileInputStream(fichier);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((line = reader.readLine()) != null && !trouve) {
                if (!line.contains(new CharArrayString(s1)) || !line.contains(new CharArrayString(s2))) {
                    sb.append(line).append("\n");
                }
            }
            sb.replace(sb.lastIndexOf("\n"), sb.length(), "");
            reader.close();
            BufferedWriter out = new BufferedWriter(new FileWriter(fichier));
            out.write(sb.toString());
            out.close();

        } catch (Exception e) {
            System.out.println("Erreur lors du remplacement dans le fichier");
        }
    }

    public static int saisieInt(Scanner sc) {
        String s;
        int n = 0;
        boolean estInt = false;
        while (!estInt) {
            try {
                s = sc.next();
                n = Integer.parseInt(s);

                if (n < 0) {
                    System.out.println("Erreur de saisie.");
                } else {
                    estInt = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur de saisie.");
                //Permet de lire, le reste de la ligne (nextInt ne lit pas le retour chariot)
                sc.nextLine();
            }
        }
        return n;
    }
}
