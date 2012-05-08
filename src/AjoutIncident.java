
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Loïc Cimon
 */
public class AjoutIncident {

    private static Scanner sc;

    public static void menuAjoutIncident(Plan plan) {
        sc = new Scanner(System.in);
        Incident inc;
        String reponse;

        boolean choixOk = false;
        do {
            System.out.println("Est-ce que l'incident a lieu sur une station ? (O : oui/N : non) ");
            reponse = sc.next();

//            //Affiche les stations
//            int cpt = 1;
//            for (Station s : plan.getStations()) {
//                System.out.println(cpt + " - " + s.getNom());
//                cpt++;
//            }

            if (reponse.compareToIgnoreCase("o") == 0) {
                choixOk = true;
                //Incident sur une station

                //Saisie de la station
                System.out.println("Quelle est le nom de la station ?");
                Station lieu = FavorisUtilisateur.choixStation(plan, sc);

                //Création de l'incident
                inc = saisieIncident();
                lieu.setIncident(inc);

                System.out.println("Incident signalé à " + lieu.getNom());

            } else if (reponse.compareToIgnoreCase("n") == 0) {
                choixOk = true;
                //Incident sur un fragment

                //Saisie des stations entre lesquelles, il y a l'incident
                System.out.println("Quelles sont les stations ?");
                System.out.println("- Donner la station de départ :");
                Station statdep = FavorisUtilisateur.choixStation(plan, sc);
                System.out.println("- Donner la station d'arrivée :");
                Station statarriv = FavorisUtilisateur.choixStation(plan, sc);

                //On récupère le fragment en question
                Fragment frag = plan.getFragmentByStations((statdep).getNom(), statarriv .getNom());

                if (frag == null) {
                    System.out.println("Il n'existe pas de fragment entre ces deux stations");
                } else {
                    //Création de l'incident
                    inc = saisieIncident();
                    frag.setIncident(inc);

                    System.out.println("Incident signalé entre " + frag.getStationDep().getNom() + " et " + frag.getStationArr().getNom() + ".");
                }
            } else {
                System.out.println("Merci de respecter le format d'écriture.");
            }
        } while (!choixOk);
    }

    public static Incident saisieIncident() {
        System.out.println("Quel est la durée de ce nouvel incident ?");
        int duree = Main.saisieInt(sc);

        System.out.println("Ajoutez un commentaire : ");
        String commentaire = sc.next();

        return new Incident(duree, commentaire);
    }
}
