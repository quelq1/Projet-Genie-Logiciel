
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Loïc Cimon
 */
public class RechercheItineraire {

    private static Plan plan;
    private static Calendar heureDep;

    /*
     * Utile juste pour les tests JUnits
     */
    public static void initPlan(Plan p, Calendar c) {
        plan = p;
        heureDep = c;
    }

    public static void menuChoixDestination(Plan p, int type) {
        plan = p;
        heureDep = Calendar.getInstance();

        Station dest;
        System.out.println("Entrez la station de destination : ");

        dest = FavorisUtilisateur.choixStation(plan, new Scanner(System.in));

        //On lance la recherche d'itinéraire
        //1 : itinéraire rapide
        //2 : itinéraire moins de changement
        //3 : itinéraire avec étapes
        Itineraire itineraire = null;
        
        System.out.println("Recherche en cours...");
        switch (type) {
            case 1:
                itineraire = getItinerairePlusRapide(plan.getStationUtil(), dest, heureDep);
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
            s.setTempsAttenteStation(0);
            //ajoute l'itinéraire à la liste des solutions
            sol.add(tmp);
        } else {
            //Ajout le temps d'attente de la prochaine rame
            //On considère qu'il faut au moins 2min pour changer
            int tmpAttente = s.getTempsAttenteStation(itineraire.getDateArrivee());
            itineraire.addDuree(tmpAttente);

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
                    int tmpParcours = fragPossible.getTempsDeParcours();
                    itineraire.addDuree(tmpParcours);

                    if (plan.aChangement(fragPossible, fragPrec)) {
                        //incrémente le nombre de changement
                        itineraire.incrChangement();
                    }
                    //appel récursif
                    rechercheItineraires(itineraire, dest, fragPossible, sol);

                    //décrémente le temps de parcours
                    itineraire.addDuree(-(fragPossible.getTempsDeParcours() + dest.getTmpArret()));
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

    public static Itineraire getItinerairePlusRapide(Station dep, Station arr, Calendar heure) {
        Itineraire itineraire = new Itineraire(dep, arr, heure);
        ArrayList<Itineraire> solutions = new ArrayList<>();
        rechercheItineraires(itineraire, dep, null, solutions);

        //On parcours les chemins pour connaître le plus court
        Itineraire res = null;
        if (!solutions.isEmpty()) {
            Calendar min = solutions.get(0).getDateArrivee();
            res = solutions.get(0);
            for (Itineraire it : solutions) {
                if (it.getDateArrivee().before(min)) {
                    res = it;
                    min = it.getDateArrivee();
                }
            }
        }
        return res;
    }

    public static Itineraire getItineraireMoinsChangement(Station dep, Station arr) {
        Itineraire itineraire = new Itineraire(dep, arr, heureDep);
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
            res = getItinerairePlusRapide(etapes.get(0), etapes.get(1), heureDep);
            //On calcule l'itinéraire le plus rapide entre chaque étape
            for (int i = 2; i < etapes.size(); i++) {
                tmp = getItinerairePlusRapide(etapes.get(i - 1), etapes.get(i), (Calendar) res.getDateArrivee().clone());
                res.concatItineraire(tmp, plan);
            }
        }
        return res;
    }

    public static void affichageItineraire(Itineraire itineraire) {
        System.out.println("Données : " + itineraire);
        System.out.println("* Itinéraire :");

        Station sDeb;
        Station sSuiv = null;
        Fragment fDeb;
        List<Ligne> lignesDeb;

        if (itineraire.getSize() <= 2) {
            sDeb = itineraire.getStation(0);
            sSuiv = itineraire.getStation(1);
            fDeb = plan.getFragmentByStations(sDeb.getNom(), sSuiv.getNom());
            lignesDeb = plan.getLigneByFragment(fDeb);
            System.out.println("\t- A " + sDeb.getNom() + ", prendre la ligne " + lignesDeb.get(0).getNom() + " jusqu'à " + sSuiv.getNom());
        } else {

            Station sFin;
            Fragment fSuiv = null;
            Ligne ligne = null;

            int i;
            for (i = 1; i <= itineraire.getSize() - 1; i++) {
                //On récupère les stations
                sDeb = itineraire.getStation(i - 1);
                sSuiv = itineraire.getStation(i);
                sFin = itineraire.getStation(i + 1);

                //On récupère les fragments
                fDeb = plan.getFragmentByStations(sDeb.getNom(), sSuiv.getNom());
                lignesDeb = plan.getLigneByFragment(fDeb);

                if (sFin != null) {
                    fSuiv = plan.getFragmentByStations(sSuiv.getNom(), sFin.getNom());
                }
                ligne = plan.getLigneCommune(fDeb, fSuiv);
                if (ligne == null) {
                    ligne = lignesDeb.get(lignesDeb.size() - 1);
                } else {
                    do {
                        sSuiv = itineraire.getStation(i);
                        i++;
                    } while (ligne.contientStation(itineraire.getStation(i)));
                    i--;
                }

                System.out.println("\t- A " + sDeb.getNom() + ", prendre la ligne " + ligne.getNom() + " jusqu'à " + sSuiv.getNom() + ".");
            }
        }

        System.out.println("\t- Vous êtes arrivé à " + sSuiv.getNom() + ".");
    }
}
