
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
        Coordonnee c = saisieCoord(plan);
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
        Fragment frag = saisieFragment(aAjoute, extremite);
        ligne.addFragment(frag);
        plan.addStation(aAjoute);

        //On écrit dans le fichier
        ecriturefichier(aAjoute, extremite, frag.getTempsDeParcours(), ligne);
    }
    
    public static Fragment saisieFragment(Station s1, Station s2) {
        System.out.println("Entrer le temps de parcours entre " + s1.getNom() + " et " + s2.getNom() + " :");
        int tmpTrajet = 0;
        boolean saisieOk = false;
        do {
            try {
                tmpTrajet = Integer.parseInt(sc.next());
                saisieOk = true;
            } catch (NumberFormatException e) {
                System.out.println("\nVeuillez saisir un entier.");
            }
        } while (!saisieOk);

        return new Fragment(s1, s2, tmpTrajet);
    }

    public static Coordonnee saisieCoord(Plan plan) {
        //Saisie coord
        double[] coord = new double[2];
        String[] nomCoord = new String[]{"latitude", "longiture"};
        Coordonnee res;

        boolean coordValide = false;
        do {
            boolean saisieOk;
            for (int i = 0; i < nomCoord.length; i++) {
                saisieOk = false;
                do {
                    System.out.println("A quelle " + nomCoord[i] + " se trouve-t-elle ? ");
                    try {
                        coord[i] = Double.parseDouble(sc.next());

                        if (0. <= coord[i] && coord[i] <= 90.) {
                            saisieOk = true;
                        }
                        else {
                            System.out.println("Choix incorrect : la " + nomCoord[i] +  " doit être entre 0 et 90.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Choix incorrect.");
                    }
                } while (!saisieOk);
            }
            
            res = new Coordonnee(coord[0], coord[1]);
            
            //Vérifie qu'aucune station n'a pas déjà ces coords
            for (Station s : plan.getStations()) {
                if (s.getCoord().equals(res)) {
                    System.out.println("Vous ne pouvez pas ajouter une nouvelle station avec les mêmes coordonnées qu'une déjà existante");
                }
                else {
                    coordValide = true;
                }
            }

        } while (!coordValide);


        return res;
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
