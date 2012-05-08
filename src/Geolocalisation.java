
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
                System.out.println("Quel est son nom ?");
                util = FavorisUtilisateur.choixStation();
                
                System.out.println("Vous êtes à la station " + util.getNom());

            } else if (rep.toUpperCase().compareToIgnoreCase("N") == 0) {
                choixOk = true;

                //Recherche de la station la plus proche
                util = rechercheStation();
                
                System.out.println("La station la plus proches est : " + util.getNom());

            } else {
                System.out.println("Merci de respecter le format d'écriture.");
            }
        } while (!choixOk);
        
        plan.setStationUtil(util);
    }

    private static Station rechercheStation() {
                
        Coordonnee coord = Coordonnee.saisieCoord(plan);

        //Lance la recherche
        return getStationProche(coord);
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
