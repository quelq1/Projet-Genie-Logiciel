
import java.util.Scanner;

public class Geolocalisation {

    private static Plan plan;
    private static Scanner sc;

    public static void geolocalisation(Plan p) {
        plan = p;
        sc = new Scanner(System.in);
        Station util = null;

        boolean choixOk = false;
        do {
            System.out.print("Vous trouvez-vous dans une station (O : oui/N : non) ? ");
            String rep = sc.next();

            if (rep.toUpperCase().compareToIgnoreCase("O") == 0) {
                choixOk = true;

                //Choix de la station parmis celles existantes
                util = choixStation();

            } else if (rep.toUpperCase().compareToIgnoreCase("N") == 0) {
                choixOk = true;

                //Recherche de la station la plus proche
                util = rechercheStation();

            } else {
                System.out.println("Merci de respecter le format d'écriture.");
            }
        } while (!choixOk);
        
        plan.setStationUtil(util);
    }

    private static Station choixStation() {
        //On affiche les stations dispo
        int i = 1;
        for (Station s : plan.getStations()) {
            System.out.println(i + " - " + s.getNom());
            i++;
        }

        boolean saisieOk = false;
        int nStation = 0;
        do {
            System.out.print("Quel est son nom (tapez le numero correspondant à votre station) ? ");
            try {
                nStation = Integer.parseInt(sc.next());

                if (nStation < 0 || nStation >= plan.getStations().size()) {
                    throw new NumberFormatException();
                }
                saisieOk = true;
            } catch (NumberFormatException e) {
                System.out.println("\nChoix incorrect.");
            }
        } while (!saisieOk);

        Station util = plan.getStations().get(nStation);

        return util;
    }

    private static Station rechercheStation() {
        double lat = 0, lon = 0;

        //Lecture de la latitude
        boolean saisieOk = false;
        do {
            System.out.println("A quelle latitude vous trouvez-vous ?");
            try {
                lat = Double.parseDouble(sc.next());
                saisieOk = true;
            } catch (NumberFormatException e) {
                System.out.println("\nChoix incorrect.");
            }
        } while (!saisieOk);

        //Lecture de la longitude
        saisieOk = false;
        do {
            System.out.println("A quelle longitude vous trouvez-vous ?");
            try {
                lon = Double.parseDouble(sc.next());
                saisieOk = true;
            } catch (NumberFormatException e) {
                System.out.println("\nChoix incorrect.");
            }
        } while (!saisieOk);

        //Lance la recherche
        return getStationProche(new Coordonnee(lat, lon));
    }

    public static Station getStationProche(Coordonnee coord) {
        Station res = null;
        double min, distance;

        if (!plan.getStations().isEmpty()) {
            res = plan.getStations().get(0);
            min = coord.distance(res.getCoord());
            
            for (Station tmp : plan.getStations()) {
                distance = coord.distance(tmp.getCoord());
                if (distance < min) {
                    res = tmp;
                    min = distance;
                }
            }
        }
        return res;
    }
}
