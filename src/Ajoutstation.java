
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Elodie
 */
public class AjoutStation {

    private static Scanner sc;

    public static void menuAjoutStation(Plan plan) {
        sc = new Scanner(System.in);
        //Saisie nom
        System.out.println("Quel est le nom de la station que vous souhaitez ajouter ? ");
        String nomStation = sc.next();
        Station aAjoute = new Station(nomStation);

        if (plan.getStations().contains(aAjoute)) {
            System.out.println("La station que vous souhaitez ajouter existe déjà.");
            return;
        }

        //Coordonnée
        Coordonnee c = Coordonnee.saisieCoord(plan, sc);
        aAjoute.setCoord(c);

        //Saisie ligne
        System.out.println("Sur quelle ligne se trouve-t-elle ? (tapez son nom)");
        String nomLigne = sc.next();
        Ligne ligne = new Ligne(nomLigne);


        if (!plan.getLignes().contains(ligne)) {
            System.out.println("La ligne n'existe pas.");
            return;
        }

        //Récupère la ligne
        for (Ligne l : plan.getLignes()) {
            if (l.equals(ligne)) {
                ligne = l;
                break;
            }
        }

        //Crée le fragment
        List<Station> stationterminus = plan.getStationExtremite(ligne);
        System.out.println("Vous avez la possibilite de lier votre station à :");
        System.out.println("\t1 - " + stationterminus.get(0).getNom());
        System.out.println("\t2 - " + stationterminus.get(1).getNom());
        System.out.println("Quel est votre choix ?");
        int choix = 0;
        boolean saisieOk = false;
        do {
            try {
                choix = Integer.parseInt(sc.next());

                if (0 < choix && choix < 3) {
                    saisieOk = true;
                }
                else {
                    System.out.println("\nChoix incorrect.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nChoix incorrect.");
            }
        } while (!saisieOk);
        Station extremite = stationterminus.get(choix - 1);

        //Création du fragment
        Fragment frag = Fragment.saisieFragment(aAjoute, extremite);
        ligne.addFragment(frag);
        plan.addStation(aAjoute);

        //On écrit dans le fichier
        ecriturefichier(aAjoute, extremite, frag.getTempsDeParcours(), ligne);
    }

    public static void ecriturefichier(Station s1, Station s2, int temps, Ligne ligne) {
        String texte = "\n" + s1.getNom() + "\t" + s1.getCoord().getLatitude() + ":" + s1.getCoord().getLongitude();
        texte += "\t" + s2.getNom() + "\t" + s2.getCoord().getLatitude() + ":" + s2.getCoord().getLongitude();
        texte += "\t" + temps + "\t" + ligne.getNom();

        FileWriter writer = null;
        try {
            writer = new FileWriter(Main.getFichierPlan(), true);
            writer.write(texte, 0, texte.length());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
