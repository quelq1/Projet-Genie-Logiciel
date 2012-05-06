
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Loïc Cimon
 */
public class RechercheItineraire {

    private static Plan plan;

    /*
     * Utile juste pour les tests JUnits
     */
    public static void initPlan(Plan p) {
        plan = p;
    }

    public static void menuChoixDestination(Plan p, int type) {
        plan = p;
        Scanner sc = new Scanner(System.in);

        boolean choixOk = false;
        Station dest;
        do {
            System.out.println("Entrez la station de destination : ");
            dest = new Station(sc.next());

            if (plan.getStations().contains(dest)) {
                dest = plan.getStations().get(plan.getStations().indexOf(dest));
                choixOk = true;
            } else {
                System.out.println("Erreur : la station saisie n'existe pas.");
            }
        } while (!choixOk);

        //On lance la recherche d'itinéraire
        //1 : itinéraire rapide
        //2 : itinéraire moins de changement
        //3 : itinéraire avec étapes
        Itineraire itineraire = null;
        System.out.println("Recherche en cours...");
        switch (type) {
            case 1:
                itineraire = getItinerairePlusRapide(plan.getStationUtil(), dest);
                break;
            case 2:
                itineraire = getItineraireMoinsChangement(plan.getStationUtil(), dest);
                break;
            case 3:
                ArrayList<Station> etapes = menuChoixEtapes(dest);
                System.out.println("Etapes : " + etapes);
                itineraire = getItineraireParEtapes(etapes);
                break;
        }

        affichageItineraire(itineraire);
    }

    private static ArrayList<Station> menuChoixEtapes(Station dest) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Station> lEtapes = new ArrayList<>();
        lEtapes.add(plan.getStationUtil());

        System.out.println("Saissisez les étapes [Finir par : f] : ");
        Station etape = null;
        String saisie;
        boolean fin = false;
        do {
            saisie = sc.next();
            etape = new Station(saisie);

            if (saisie.compareToIgnoreCase("f") == 0) {
                fin = true;
            } else if (plan.getStations().contains(etape)) {
                etape = plan.getStations().get(plan.getStations().indexOf(etape));
                lEtapes.add(etape);
            } else {
                System.out.println("Erreur : la station saisie n'existe pas.");
            }
        } while (!fin);

        if (!lEtapes.contains(dest)) {
            lEtapes.add(dest);
        }

        return lEtapes;
    }

    public static ArrayList<Fragment> getDirections(Station s) {
        ArrayList<Fragment> res = new ArrayList<>();

        for (Ligne l : plan.getLignes()) {

            //Parcours des fragments de la ligne
            for (Fragment f : l.getListeFragments()) {

                //Si le fragment contient la station et qu'elle n'est pas déjà dans la liste
                //On ajoute si :
                // - le fragment contient la station de départ
                // - le fragment n'est pas déjà contenu
                // - le fragment n'a pas d'incident
                // - la station d'arrivée n'a pas d'incident

                if (f.contientStation(s)
                        && !res.contains(f)
                        && f.getIncident() == null
                        && f.getDestination(s).getIncident() == null) {
                    res.add(f);
                }
            }
        }
        return res;
    }

    public static void rechercheItineraires(Itineraire itineraire, Station s, Fragment fragPrec, ArrayList<Itineraire> sol) {
        if (itineraire.getArrivee().equals(s)) {
            //On fait une copie pour éviter les effets de bords
            Itineraire tmp = itineraire.clone();
            //On ne compte pas le temps d'arrêt à la station d'arrivée.
            tmp.rmDuree(s.getTempsArret());
            //ajoute l'itinéraire à la liste des solutions
            sol.add(tmp);
        } else {
            //On récupère les directions possibles
            ArrayList<Fragment> directions = getDirections(s);
            Station dest;

            //On boucle sur les stations possibles
            for (Fragment fragPossible : directions) {
                //On récupère la destination du fragment
                dest = fragPossible.getDestination(s);

                //Vérifie que la station n'a jamais été emprunté
                if (!itineraire.contains(dest)) {
                    //enregistrement de la station
                    itineraire.addStation(dest);
                    //ajout du temps de trajet
                    itineraire.addDuree(fragPossible.getTempsDeParcours() + dest.getTempsArret());

                    if (plan.aChangement(fragPossible, fragPrec)) {
                        //incrémente le nombre de changement
                        itineraire.incrChangement();
                    }
                    //appel récursif
                    rechercheItineraires(itineraire, dest, fragPossible, sol);

                    //décrémente le temps de parcours
                    itineraire.rmDuree(fragPossible.getTempsDeParcours() + dest.getTempsArret());
                    if (plan.aChangement(fragPossible, fragPrec)) {
                        //décrément le nombre de changement
                        itineraire.decrChangement();
                    }
                    //suppression de la direction et le fragement précédent
                    itineraire.rmLastStation();
                }
            }
        }
    }

    public static Itineraire getItinerairePlusRapide(Station dep, Station arr) {
        Itineraire itineraire = new Itineraire(dep, arr);
        ArrayList<Itineraire> solutions = new ArrayList<>();
        rechercheItineraires(itineraire, dep, null, solutions);

        //On parcours les chemins pour connaître le plus court
        Itineraire res = null;
        if (!solutions.isEmpty()) {
            int min = solutions.get(0).getDuree();
            for (Itineraire it : solutions) {
                if (it.getDuree() <= min) {
                    res = it;
                    min = it.getDuree();
                }
            }
        }
        return res;
    }

    public static Itineraire getItineraireMoinsChangement(Station dep, Station arr) {
        Itineraire itineraire = new Itineraire(dep, arr);
        ArrayList<Itineraire> solutions = new ArrayList<>();
        rechercheItineraires(itineraire, dep, null, solutions);

        //On parcours les chemins pour connaître le plus court
        Itineraire res = null;
        if (!solutions.isEmpty()) {
            int min = solutions.get(0).getNbChangement();
            for (Itineraire it : solutions) {
                if (it.getNbChangement() <= min) {
                    res = it;
                    min = it.getNbChangement();
                }
            }
        }
        return res;
    }

    /*
     * La station de départ doit être la 1ère La station d'arrivée la dernière
     */
    public static Itineraire getItineraireParEtapes(ArrayList<Station> etapes) {
        Itineraire res = null, tmp;
        if (etapes.size() >= 2) {
            res = getItinerairePlusRapide(etapes.get(0), etapes.get(1));
            //On calcule l'itinéraire le plus rapide entre chaque étape
            for (int i = 2; i < etapes.size(); i++) {
                tmp = getItinerairePlusRapide(etapes.get(i - 1), etapes.get(i));
                res.concatItineraire(tmp, plan);
            }
        }
        return res;
    }

    public static void affichageItineraire(Itineraire itineraire) {
        System.out.println("* Itinéraire trouvé en " + itineraire.getDuree() + "m et " + itineraire.getNbChangement() + " changement(s) : ");
        System.out.print("\t - ");
        for (Station station : itineraire.getTrajet()) {
            System.out.print(station.getNom() + ", ");
        }
        System.out.println("");
    }
}
