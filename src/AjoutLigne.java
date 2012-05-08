
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Loïc Cimon
 */
public class AjoutLigne {

    private static Scanner sc;

    public static void menuAjoutLigne(Plan plan) {
        sc = new Scanner(System.in);

        System.out.println("Entrez le nom de la ligne à creer:");
        Ligne l = new Ligne(sc.next());
        if (plan.getLignes().contains(l)) {
            System.out.println("la ligne existe déjà!!");
            return;
        }

        System.out.println("Entrer le nombre de stations à ajouter:");
        int nbreStation;

        boolean saisieOk = false;
        do {
            nbreStation = sc.nextInt();
            if (nbreStation >= 2) {
                saisieOk = true;
            } else {
                System.out.println("Il faut au moins deux stations pour creer la ligne!");
            }
        } while (!saisieOk);

        ArrayList<Station> listStationTmp = new ArrayList();

        //TODO Affiche les stations et favoris

        while (nbreStation != 0) {
            System.out.println("Entrer le nom de la station :");
            String s = sc.next();
            Station stationTmp = new Station(s);

            if (!listStationTmp.contains(stationTmp)) {
                if (!plan.getStations().contains(stationTmp)) {
                    System.out.println("La station n'existe pas, voulez vous la créer ? [O/Y]");
                    String choix = sc.next();
                    if (choix.compareToIgnoreCase("o") == 0) {
                        Coordonnee coord = AjoutStation.saisieCoord(plan);
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

        for (int i = 0; i <= listStationTmp.size() - 2; i++) {
            System.out.println("Entrer le temps de parcours entre " + listStationTmp.get(i) + "et " + listStationTmp.get(i + 1) + ":");
            int tempsTmp = sc.nextInt();
            Fragment f = new Fragment(listStationTmp.get(i), listStationTmp.get(i + 1), tempsTmp);
            l.addFragment(f);
        }

        plan.getLignes().add(l);
        System.out.println("La ligne a été ajoutée!");
    }
}
