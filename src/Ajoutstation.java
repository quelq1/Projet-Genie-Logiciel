
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
        boolean saisieOk = false;
        Coordonnee c;
        do {
            c = Coordonnee.saisieCoord(plan, sc);

            //Vérifie qu'aucune station n'a pas déjà ces coords
            for (Station s : plan.getStations()) {
                if (s.getCoord().equals(c)) {
                    System.out.println("Vous ne pouvez pas ajouter une nouvelle station avec les mêmes coordonnées qu'une déjà existante");
                    saisieOk = false;
                    break;
                } else {
                    saisieOk = true;
                }
            }
        } while (!saisieOk);
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

        Fragment frag = Fragment.creationLiaisonFragment(plan, ligne, aAjoute, sc);
        ligne.addFragment(frag);
        plan.addStation(aAjoute);
        
        System.out.println("La station " + aAjoute.getNom() + " a été ajoutée à ligne " + ligne.getNom() + ".");

        //On écrit dans le fichier
        String txt = plan.formatLigneFichier(aAjoute, frag.getDestination(aAjoute), frag.getTempsDeParcours(), ligne);
        Main.ecriturefichier(txt);
    }

   
}
