
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Loïc Cimon
 */
public class AjoutLigne {

    private static Scanner sc;

    public static void menuAjoutLigne(Plan plan) {
        sc = new Scanner(System.in);

        System.out.println("Entrez le nom de la ligne à creer : ");
        Ligne l = new Ligne(sc.next());
        if (plan.getLignes().contains(l)) {
            System.out.println("La ligne existe déjà!!");
            return;
        }

        System.out.println("Entrer le nombre de stations à ajouter : ");
        int nbreStation;

        boolean saisieOk = false;
        do {
            nbreStation = sc.nextInt();
            if (nbreStation >= 2) {
                saisieOk = true;
            } else {
                System.out.println("Il faut au moins deux stations pour creer la ligne !");
            }
        } while (!saisieOk);

        ArrayList<Station> listStationTmp = new ArrayList();

        //TODO Affiche les stations et favoris

        while (nbreStation != 0) {
            System.out.println("Entrer le nom de la station :");
            String s = sc.next();
            Station stationTmp = new Station(s);

            //Vérifie si la station n'a pas déjà été saisie
            if (!listStationTmp.contains(stationTmp)) {

                //Vérifie si la station n'existe pas déjà dans le plan
                if (!plan.getStations().contains(stationTmp)) {
                    System.out.println("La station n'existe pas, voulez vous la créer ? [O/N]");
                    String choix = sc.next();
                    if (choix.compareToIgnoreCase("o") == 0) {
                        Coordonnee coord = Coordonnee.saisieCoord(plan, sc);
                        stationTmp.setCoord(coord);
                    } else {
                        //Passe à l'itération suivante
                        continue;
                    }
                } else {
                    //On récupère la station existante dans le plan
                    stationTmp = plan.getStations().get(plan.getStations().indexOf(stationTmp));
                }

                listStationTmp.add(stationTmp);
                nbreStation--;
            } else {
                System.out.println("Vous avez déja saisi cette station!");
            }
        }

        Fragment f;
        for (int i = 0; i <= listStationTmp.size() - 2; i++) {
            //Vérifie que le fragement n'existe pas déjà
            f = plan.getFragmentByStations(listStationTmp.get(i).getNom(), listStationTmp.get(i + 1).getNom());
            if (f == null) {
                //Si null, on le crée
                f = Fragment.saisieFragment(listStationTmp.get(i), listStationTmp.get(i + 1), sc);
            }
            l.addFragment(f);
        }

        plan.getLignes().add(l);
        System.out.println("La ligne a été ajoutée !");
    }
}
