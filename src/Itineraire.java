
import java.util.ArrayList;
import java.util.Objects;

public class Itineraire {

    private ArrayList<Station> trajet;
    private Station depart;
    private Station arrivee;
    private int duree;
    private int nbChangement;

    public Itineraire(Station dep, Station arr) {
        this.trajet = new ArrayList<>();
        //On ajoute la station de départ
        trajet.add(dep);
        depart = dep;
        arrivee = arr;
        duree = 0;
        nbChangement = 0;
    }

    /*
     * Constructeur pour la recherche d'itinéraire par étapes (sans ville
     * d'arrivée)
     */
    public Itineraire(Station depart) {
        this.trajet = new ArrayList<>();
        this.depart = depart;
        duree = 0;
        nbChangement = 0;
    }

    public Itineraire(Station dep, Station arr, int lg, int nb) {
        this.trajet = new ArrayList<>();
        //On ajoute la station de départ
        trajet.add(dep);
        depart = dep;
        arrivee = arr;
        duree = lg;
        nbChangement = nb;
    }

    public boolean addStation(Station s) {
        return trajet.add(s);
    }

    @Override
    public Itineraire clone() {
        Itineraire it = new Itineraire(depart, arrivee);
        it.trajet = (ArrayList<Station>) this.trajet.clone();
        it.duree = this.duree;
        it.nbChangement = this.nbChangement;
        return it;
    }

    //Useless
    public boolean contains(Station s) {
        return trajet.contains(s);
    }

    //Useless
    public boolean rmLastStation() {
        Station last = trajet.get(trajet.size() - 1);
        return trajet.remove(last);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Itineraire other = (Itineraire) obj;
        if (!this.trajet.equals(other.trajet)) {
            return false;
        }

        if (!this.arrivee.equals(other.arrivee)) {
            return false;
        }

        if (!this.depart.equals(other.depart)) {
            return false;
        }

        if (this.duree != other.duree) {
            return false;
        }

        if (this.nbChangement != other.nbChangement) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.trajet);
        hash = 89 * hash + Objects.hashCode(this.depart);
        hash = 89 * hash + Objects.hashCode(this.arrivee);
        return hash;
    }

    public void addDuree(int i) {
        duree += i;
    }

    @Override
    public String toString() {
        String s = "De " + depart + " à " + arrivee + "[ durée : " + duree + " - Nombre de changement : " + nbChangement + "] :";
        s += "\n" + trajet;
        return s;
    }

    public Station getArrivee() {
        return arrivee;
    }

    public void rmDuree(int i) {
        duree -= i;
    }

    public int getDuree() {
        return duree;
    }

    public int getNbChangement() {
        return nbChangement;
    }

    public void incrChangement() {
        nbChangement++;
    }

    public void decrChangement() {
        nbChangement--;
    }

    public void concatItineraire(Itineraire i) {
        this.arrivee = i.arrivee;
        this.duree += i.duree;
        this.nbChangement += i.nbChangement;
        this.trajet.addAll(i.trajet);
    }

    public Station getStation(int i) {
        return trajet.get(i);
    }

    public int getSize() {
        return trajet.size();
    }
}
