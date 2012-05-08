
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

        System.out.println("Est-ce que l'incident a lieu sur une station ? (O : oui/N : non) ");
        String reponse = sc.next();

        //Affiche les stations
        int cpt = 1;
        for (Station s : plan.getStations()) {
            System.out.println(cpt + " - " + s.getNom());
            cpt++;
        }

        if (reponse.compareToIgnoreCase("O") == 0) {
            //Incident sur une station

            int numstation;
            System.out.println("Quelle station ?");
            numstation = sc.nextInt();

            inc = saisieIncident();
            Station lieu = plan.getStations().get(numstation - 1);
            lieu.setIncident(inc);

            System.out.println("Incident signalé à " + lieu.getNom());

        } else {
            //Incident sur un fragment

            int statdep;
            int statarriv;
            System.out.println("Quelles sont les stations?");
            System.out.println("Donner la station de départ puis la station d'arrivée");
            statdep = sc.nextInt();
            statarriv = sc.nextInt();

            System.out.println(statdep + " " + statarriv);
            System.out.println(plan.getStations().get(statdep - 1) + " " + plan.getStations().get(statarriv - 1));

            boolean ok = false;

            Fragment frag = plan.getFragmentByStations(plan.getStations().get(statdep - 1).getNom(), plan.getStations().get(statarriv - 1).getNom());

            if (frag == null) {
                System.out.println("Il n'existe pas de fragment entre ces deux stations");
            } else {
                inc = saisieIncident();
                frag.setIncident(inc);

                System.out.println("Incident signalé entre " + frag.getStationDep().getNom() + " et " + frag.getStationArr().getNom() + ".");
            }
        }
    }

    public static Incident saisieIncident() {
        System.out.println("Quel est la durée de ce nouvel incident ?\n");
        int duree;
        duree = sc.nextInt();

        System.out.println("Ajoutez un commentaire : \n");
        String commentaire;
        commentaire = sc.next();

        return new Incident(duree, commentaire);
    }
}
