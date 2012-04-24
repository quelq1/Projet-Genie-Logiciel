
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Mami Sall
 */
public class Plan {

    private Set<Station> stations;
    private Set<Ligne> lignes;
    private Station util;

    public Plan() {
        stations = new HashSet<>();
        lignes = new HashSet<>();
        util = null;
    }

    public Plan(String fichier) {
        stations = new HashSet<>();
        lignes = new HashSet<>();
        util = null;
        chargementPlan(fichier);
    }

    //Lignes
    public Set<Ligne> getLignes() {
        return lignes;
    }

    public boolean addLignes(Ligne l) {
        return lignes.add(l);
    }

    //Stations
    public Set<Station> getStations() {
        return stations;
    }

    public boolean addStation(Station s) {
        return stations.add(s);
    }

    //Station utilisateur
    public Station getStationUtil() {
        return util;
    }

    public void setStationUtil(Station s) {
        util = s;
    }

    private void chargementPlan(String fichier) {
        //lecture du fichier texte	
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);

            String ligne;
            while ((ligne = br.readLine()) != null) {
                traitementLigne(ligne);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void traitementLigne(String chaine) {
        if (chaine != null) {
            String[] ligne = chaine.split("\t");

            //Station de départ
            String[] coord = ligne[1].split(":");
            Coordonnee cd = new Coordonnee(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
            Station sd = new Station(ligne[0], cd);

            //Station d'arrivée
            coord = ligne[3].split(":");
            Coordonnee ca = new Coordonnee(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
            Station sa = new Station(ligne[2], ca);

            if (!sd.equals(sa)) {

                //Ajout des stations de depart et d'arrivée si elle n'existe pas
                stations.add(sd);
                stations.add(sa);

                // creation du fragment 
                Fragment d = new Fragment(sd, sa, Integer.parseInt(ligne[4]));

                //creation d'une ligne 
                Ligne li = new Ligne(ligne[5].toUpperCase());

                //verification de l'existence de la ligne 
                if (lignes.contains(li)) {
                    Iterator<Ligne> it = lignes.iterator();
                    Ligne l;
                    while (it.hasNext()) {
                        l = it.next();

                        if (l.equals(li)) {
                            l.addFragment(d);
                        }
                    }
                } //si la ligne n'existe pas on l'ajoute
                else {
                    li.addFragment(d);
                    lignes.add(li);
                }
            }
        }
    }

    @Override
    public String toString() {
        String s = "* Plan :";
        s += "\n\t- Nombre de stations : " + stations.size();
        s += "\n\t- Nombre de lignes : " + lignes.size();
        s += "\n";
        s += "* Stations :";
        Iterator<Station> is = stations.iterator();
        while (is.hasNext()) {
            s += "\n\t- " + is.next();
        }
        s += "\n";
        s += "* Lignes :";
        Iterator<Ligne> il = lignes.iterator();
        while (il.hasNext()) {
            s += "\n\t- " + il.next();
        }
        return s;
    }

    public Station getStationProche(Coordonnee coord) {
        Station res = null, tmp;
        double min, distance;
        
        Iterator<Station> is = stations.iterator();
        if (is.hasNext()) {
            tmp = is.next(); 
            res = tmp;
            min = coord.distance(tmp.getCoord());
            while (is.hasNext()) {
                tmp = is.next();
                distance = coord.distance(tmp.getCoord());
                if (distance < min) {
                    res = tmp;
                    min = distance;
                }
            }
        }

        return res;
    }
    
    public ArrayList<Fragment> getDirections(Station s) {
        ArrayList<Fragment> res = new ArrayList<>();
        Fragment f;

        //Parcours des lignes
        Iterator<Ligne> ligne = lignes.iterator();
        Iterator<Fragment> frag;
        while (ligne.hasNext()) {
            //Parcours des fragments de la ligne
            frag = ligne.next().getListeFragments().iterator();
            while (frag.hasNext()) {
                f = frag.next();

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
    
    public void rechercheItineraires(Itineraire itineraire, Station s, ArrayList<Itineraire> sol) {
        if (itineraire.getArrivee().equals(s)) {
            //On fait une copie pour éviter les effets de bords
            Itineraire tmp = itineraire.clone();
            //On ne compte pas le temps d'arrêt à la station d'arrivée.
            tmp.rmDuree(s.getTempsArret());
            //ajoute l'itinéraire à la liste des solutions
            sol.add(tmp);
        } else {
            //On récupère les directions possibles
            ArrayList<Fragment> directions = this.getDirections(s);
            Station dest;
            Fragment prec = null;

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
                    
                    if (this.aChangement(fragPossible, prec)) {
                        //incrémente le nombre de changement
                        itineraire.incrChangement();                        
                    }    
                    
                    //appel récursif
                    this.rechercheItineraires(itineraire, dest, sol);
                    
                    //mise à jour des stations possibles
                    directions = this.getDirections(dest);

                    //décrémente le temps de parcours
                    itineraire.rmDuree(fragPossible.getTempsDeParcours() + dest.getTempsArret());
                    //décrément le nombre de changement
                    itineraire.decrChangement();
                    //suppression de la direction
                    itineraire.rmLastStation();
                }
                prec = fragPossible;
            }
        }
    }

    public Itineraire getItinerairePlusRapide(Station dep, Station arr) {
        Itineraire itineraire = new Itineraire(dep, arr);
        ArrayList<Itineraire> solutions = new ArrayList<>();
        this.rechercheItineraires(itineraire, dep, solutions);

        //On parcours les chemins pour connaître le plus court
        Itineraire res = null;
        if (!solutions.isEmpty()) {
            int min = solutions.get(0).getDuree();
            for (Itineraire it : solutions) {
                if (it.getDuree() < min) {
                    res = it;
                }
            }
        }
        return res;
    }
}
